package metropolis.xtracted.controller.lazyloading

import java.util.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.*
import metropolis.xtracted.controller.ControllerBase
import metropolis.xtracted.controller.LRUCache
import metropolis.xtracted.controller.Scheduler
import metropolis.xtracted.data.Filter
import metropolis.xtracted.data.SortDirection
import metropolis.xtracted.data.SortDirective
import metropolis.xtracted.data.UNORDERED
import metropolis.xtracted.model.TableColumn
import metropolis.xtracted.model.TableState
import metropolis.xtracted.repository.Identifiable
import metropolis.xtracted.repository.CRUDLazyRepository


class LazyTableController<T : Identifiable>(
    title: String,
    private val repository: CRUDLazyRepository<T>,
    columns: List<TableColumn<T, *>>,
    private val defaultItem: T
) :
    ControllerBase<TableState<T>, LazyTableAction>(
        initialState = TableState(
            title = title,
            triggerRecompose = false,
            allIds = repository.readFilteredIds(emptyList(), SortDirective(null), ""),
            selectedId = null,
            lazyListState = LazyListState(),
            focusRequester = FocusRequester(),
            currentFilters = emptyList(),
            currentSort = UNORDERED,
            filteredCount = repository.filteredCount(emptyList()),
            totalCount = repository.totalCount(),
            columns = columns
        )
    ) {
    lateinit var uiScope: CoroutineScope

    private val filterScheduler = Scheduler(200)

    private val maxNumberOfEntriesInCache = 100
    private val cache = Collections.synchronizedMap(LRUCache<Int, T>(maxNumberOfEntriesInCache))

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getData(id: Int): T =
        cache.computeIfAbsent(id) {
            val deferred = ioScope.async {
                delay(60)
                repository.read(id)!!
            }
            deferred.invokeOnCompletion {
                val loadedItem = deferred.getCompleted()
                cache[id] = loadedItem
                if (isVisible(id)) {
                    recompose()
                }
            }

            defaultItem
        }

    override fun executeAction(action: LazyTableAction): TableState<T> =
        when (action) {
            is LazyTableAction.Select -> changeSelection(action.id)
            is LazyTableAction.SelectNext -> selectNext()
            is LazyTableAction.SelectPrevious -> selectPrevious()
            is LazyTableAction.SetFilter<*> -> setFilter(
                action.column as TableColumn<T, *>,
                action.filter,
                action.nameOrder
            )

            is LazyTableAction.ToggleSortOrder<*> -> toggleSortOrder(action.column as TableColumn<T, *>)
            is LazyTableAction.Create -> create()
            is LazyTableAction.Reload -> reloadTable(action.reloadId)
        }

    private fun create() =
        state.copy(selectedId = null)

    private fun reloadTable(reloadId: Int?): TableState<T> {
        if (reloadId != null) {
            cache.remove(key = reloadId)
        }
        return setFilter(state.columns[0], filter = state.columns[0].filterAsText, "")
    }

    private fun changeSelection(id: Int) =
        state.copy(selectedId = id)

    private fun selectNext() =
        with(state) {
            focusRequester.requestFocus()
            val nextIdx = (allIds.indexOf(selectedId ?: -1) + 1).coerceAtMost(filteredCount - 1)
            if (nextIdx >= lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size - 2) {
                scrollToIdx(lazyListState.firstVisibleItemIndex + 1)
            }
            changeSelection(allIds[nextIdx])
        }

    private fun selectPrevious() =
        with(state) {
            focusRequester.requestFocus()
            val nextIdx = (allIds.indexOf(selectedId ?: 1) - 1).coerceAtLeast(0)
            if (nextIdx < lazyListState.firstVisibleItemIndex) {
                scrollToIdx(lazyListState.firstVisibleItemIndex - 1)
            }
            changeSelection(allIds[nextIdx])
        }

    private fun setFilter(column: TableColumn<T, *>, filter: String, nameOrder: String): TableState<T> {
        column.filterAsText = filter
        filterScheduler.scheduleTask {
            if (column.validFilterDescription()) {
                val allIds = repository.readFilteredIds(
                    filters = createFilterList(),
                    sortDirective =
                    state.currentSort,
                    nameOrder
                )
                scrollToIdx(0)
                state = state.copy(
                    allIds = allIds,
                    filteredCount = allIds.size
                )
            }
        }
        return state
    }

    private fun toggleSortOrder(column: TableColumn<T, *>): TableState<T> {
        val currentSortDirective = state.currentSort
        val nextSortDirective =
            when {
                null == column.dbColumn -> UNORDERED

                currentSortDirective.column == column.dbColumn -> if (currentSortDirective.direction == SortDirection.ASC) {
                    SortDirective(currentSortDirective.column, SortDirection.DESC)
                } else {
                    UNORDERED
                }

                UNORDERED == currentSortDirective ||
                        currentSortDirective.column != column.dbColumn -> SortDirective(
                    column.dbColumn,
                    SortDirection.ASC
                )

                else -> UNORDERED
            }

        scrollToIdx(0)

        return state.copy(
            currentSort = nextSortDirective,
            allIds = repository.readFilteredIds(createFilterList(), nextSortDirective, "")
        )

    }

    private fun createFilterList(): List<Filter<*>> =
        buildList {
            state.columns.forEach {
                if (it.dbColumn != null && it.filterAsText.isNotBlank() && it.validFilterDescription()) {
                    add(it.createFilter())
                }
            }
        }

    private fun scrollToIdx(idx: Int) =
        uiScope.launch {
            state.lazyListState.scrollToItem(idx, 0)
        }

    private fun isVisible(id: Int): Boolean =
        with(state) {
            val idx = allIds.indexOf(id)
            idx >= lazyListState.firstVisibleItemIndex &&
                    idx < lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size
        }

    private fun recompose() {
        state = state.copy(triggerRecompose = !state.triggerRecompose)
    }
}

