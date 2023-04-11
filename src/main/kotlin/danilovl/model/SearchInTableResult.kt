package danilovl.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchInTableResult(
    val count: Int,
    val result: List<MutableMap<String, String?>>? = null
)
