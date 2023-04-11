package danilovl.setting

enum class ErrorType(val value: String) {
    AUTH_TOKEN("AuthToken"),
    PARAM_ERROR("ParamError"),
    INTERNAL_ERROR("InternalError")
}
