package danilovl.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(val result: String = "ok")
