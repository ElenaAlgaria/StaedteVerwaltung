package metropolis.xtracted.repository

import java.sql.ResultSet
import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.data.Filter
import metropolis.xtracted.data.SortDirective

class LazyRepository<T>(private val url        : String,
                        private val table      : String,
                        private val dataColumns: Map<DbColumn, (T) -> String?>,
                        private val idColumn   : DbColumn,
                        private val mapper     : ResultSet.() -> T) {

    fun readFilteredIds(filters: List<Filter<*>>, sortDirective: SortDirective): List<Int> =
        readIds(url           = url,
                table         = table,
                idColumn      = idColumn,
                filters       = filters,
                sortDirective = sortDirective)

    fun read(id: Int) =
        readFirst(url     = url,
                  table   = table,
                  columns = dataColumns.keys.joinToString(),
                  where   = "$idColumn = $id",
                  map     = { mapper() })

    fun totalCount() =
        count(url      = url,
              table    = table,
              idColumn = idColumn)

    fun filteredCount(filters: List<Filter<*>>) =
        count(url      = url,
              table    = table,
              idColumn = idColumn,
              filters  = filters)


}
