package metropolis.xtracted.repository

import metropolis.metropolis.data.City
import java.sql.ResultSet
import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.data.Filter
import metropolis.xtracted.data.SortDirective

class CRUDLazyRepository<T: Identifiable>(private val url        : String,
                                          private val table      : String,
                                          private val idColumn   : DbColumn,
                                          private val dataColumns: Map<DbColumn, (T) -> String?>,
                                          private val mapper     : ResultSet.() -> T) {
//    fun createKey(data: T) : Int {
//        val (fields, values) = dataColumns.entries
//            .filter { it.key != idColumn }
//            .map { entry -> entry.key to entry.value(data) }
//            .unzip()
//
//        val fieldsString = fields.joinToString(", ")
//        val valuesString = values.joinToString(", ")
//
//        val insertStmt = """INSERT INTO $table ($fieldsString) VALUES ($valuesString) RETURNING $idColumn""".trimMargin()
//
//        return insertAndCreateKey(url = url, insertStmt = insertStmt)
//    }
 fun createKey(id: Int) =
    insertAndCreateKey(url        = url,
    insertStmt = """INSERT INTO $table ($idColumn) VALUES ($id) RETURNING $idColumn """.trimMargin())



    fun readFilteredIds(filters: List<Filter<*>>, sortDirective: SortDirective, nameOrder: String): List<Int> =
        readIds(url           = url,
                table         = table,
                idColumn      = idColumn,
                filters       = filters,
                sortDirective = sortDirective,
                 nameOrder = nameOrder)

    fun read(id: Int) : T?  =
        readFirst(url     = url,
            table   = table,
            columns = "$idColumn, " + dataColumns.keys.joinToString(),
            where   = "$idColumn = $id",
            map     = { mapper() })



    fun update(data: T){
        val valueUpdates = StringBuilder()
        dataColumns.entries.forEachIndexed { index, entry ->
            valueUpdates.append("${entry.key}")
            valueUpdates.append(" = ")
            valueUpdates.append(entry.value(data))
            if(index < dataColumns.size - 1){
                valueUpdates.append(", ")
            }

        }
        update(url          = url,
            table        = table,
            id           = data.id,
            idColumn     = idColumn,
            setStatement = """SET $valueUpdates """)

    }

    fun delete(id: Int) =
        delete(url   = url,
            table = table,
            id    = id)


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
