package danilovl.service

import danilovl.factory.QueryBuilder
import danilovl.model.SearchInTableResult
import danilovl.model.SearchParam
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.system.measureTimeMillis

object Search {
    suspend fun search(searchParams: Array<SearchParam>): List<MutableMap<String, SearchInTableResult>> {
        val channel = Channel<MutableMap<String, SearchInTableResult>>()
        val result: MutableList<MutableMap<String, SearchInTableResult>> = mutableListOf()

        coroutineScope {
            searchParams.map {
                launch {
                    val startTime = System.currentTimeMillis()
                    val executionTime = measureTimeMillis {
                        searchInTable(it, channel)
                    }
                    val endTime = System.currentTimeMillis()

                    println("Search in table for '${it.identifier}' was completed within ${executionTime} ms. Start: ${startTime} End: ${endTime}")
                }
            }

            repeat(searchParams.size) {
                result.add(channel.receive())
            }
        }

        return result
    }

    private suspend fun searchInTable(searchParam: SearchParam, channel: Channel<MutableMap<String, SearchInTableResult>>) {
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
                } catch (e: NullPointerException) {
                    println("Query result for '$columnName' is error")
                }

                rowResult[columnName] = columnValue
            }

            resultRows.add(rowResult)
        }

        result[searchParam.identifier] = SearchInTableResult(resultRows.size, resultRows)

        channel.send(result)
    }
}
