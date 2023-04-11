package danilovl.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val error: ApiErrorType?,
    val result: List<MutableMap<String, SearchInTableResult>>? = null
)
