package danilovl.factory

import danilovl.model.SearchParam

object QueryBuilder {
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
        if (searchParam.whereColumns != null) {
            query += " WHERE"
        }

        if (searchParam.search != null) {
            searchParam.whereColumns?.forEachIndexed { index, tableColumn ->
                var orWhere = " "
                if (index >= 1) {
                    orWhere = "OR"
                }

                var whereOperator = "LIKE"
                var whereSearch = searchParam.search

                if (searchParam.searchCondition != null) {
                    whereOperator = searchParam.searchCondition!!
                }

                if (whereOperator.equals("LIKE") && !searchParam.search!!.contains("%")) {
                    whereSearch = "%${searchParam.search}%"
                }

                query += " $orWhere $tableColumn $whereOperator '$whereSearch'"
            }
        }

        query += " ORDER BY id"
        query += " LIMIT ${searchParam.limit}"
        query += " OFFSET ${searchParam.offset};"

        return query.replace("\\s+".toRegex(), " ")
    }
}
