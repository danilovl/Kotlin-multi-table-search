package danilovl.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchParam(
    var identifier: String,
    var tableName: String,
    var search: String? = null,
    var searchCondition: String? = null,
    val selectColumns: Array<String>,
    val whereColumns: Array<String>? = null,
    var limit: Int = 10,
    val offset: Int = 0
) {
    init {
        identifier = identifier.trim()
        tableName = tableName.trim()
        search = search?.trim()
        searchCondition = searchCondition?.trim()

        if (limit > 500) {
            limit = 500
        }
    }
}
