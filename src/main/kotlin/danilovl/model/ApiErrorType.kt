package danilovl.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorType(
    val type: String,
    val message: Array<String>
)
