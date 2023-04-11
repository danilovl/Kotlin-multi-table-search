package danilovl.setting

enum class ErrorTypeMessage(val message: String) {
    MISSING_AUTH_TOKEN("Missing auth token"),
    AUTH_TOKEN_INVALID("Auth token is invalid"),
    PARAM_ERROR("Bad parameters"),
    INTERNAL_ERROR("Server error")
}
