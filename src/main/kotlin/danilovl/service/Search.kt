package danilovl.service

import danilovl.factory.QueryBuilder
import danilovl.model.SearchInTableResult
import danilovl.model.SearchParam
import kotlinx.coroutines.*

class Search {
    companion object {
        suspend fun search(searchParams: Array<SearchParam>): List<MutableMap<String, SearchInTableResult>> {
            val result = runBlocking {
                val deferreds: List<Deferred<MutableMap<String, SearchInTableResult>>> = searchParams.map {
                    it
                    async {
                        searchInTable(it)
                    }
                }

                deferreds.awaitAll()
            }

            return result
        }

        private fun searchInTable(searchParam: SearchParam): MutableMap<String, SearchInTableResult> {
            val query = QueryBuilder.getQuery(searchParam)

            val statement = Database.getConnection().prepareStatement(query)
            val queryResult = statement.executeQuery()

            val result = mutableMapOf<String, SearchInTableResult>()
            val resultRows = mutableListOf<MutableMap<String, String?>>()
            val columnsNumber: Int = queryResult.metaData.columnCount

            while (queryResult.next()) {
                val rowResult = mutableMapOf<String, String?>()

                for (i in 1..columnsNumber) {
                    val columnName = queryResult.metaData.getColumnName(i)
                    var columnValue: String? = null

                    try {
                        columnValue = queryResult.getString(i)
                    } catch (e: NullPointerException) {}

                    rowResult[columnName] = columnValue
                }

                resultRows.add(rowResult)
            }

            result[searchParam.identifier] = SearchInTableResult(resultRows.size, resultRows)

            return result
        }
    }
}
