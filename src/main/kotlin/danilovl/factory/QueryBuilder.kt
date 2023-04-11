package danilovl.factory

import danilovl.model.SearchParam

class QueryBuilder {
    companion object {
        fun getQuery(searchParam: SearchParam): String {
            var query = "SELECT "
            val lenSelectColumns: Int = searchParam.selectColumns.size

            searchParam.selectColumns.forEachIndexed { index, selectColumn ->
                var suffix = " "
                if (lenSelectColumns > 1 && index < lenSelectColumns - 1) {
                    suffix = ", "
                }

                query += selectColumn + suffix
            }

            query += "FROM ${searchParam.tableName}"

            searchParam.whereColumns?.forEachIndexed { index, tableColumn ->
                var orWhere = " "
                if (index > 1) {
                    orWhere = "OR"
                }

                query += "$orWhere WHERE $tableColumn LIKE '%${searchParam.search}%'"
            }

            query += " ORDER BY id"
            query += " LIMIT ${searchParam.limit}"
            query += " OFFSET ${searchParam.offset};"

            return query
        }
    }
}
