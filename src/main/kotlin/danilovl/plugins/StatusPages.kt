package danilovl.plugins

import danilovl.model.*
import danilovl.setting.ErrorType
import danilovl.setting.ErrorTypeMessage
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*

fun Application.statusPages() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when(throwable) {
                is RequestValidationException -> {
                    val apiErrorType = ApiErrorType(ErrorType.PARAM_ERROR.value, arrayOf(throwable.reasons.joinToString()))
                    val apiResponse = ApiResponse(apiErrorType)

                    call.respond(HttpStatusCode.BadRequest, apiResponse)
                }
                else -> {
                    val apiErrorType = ApiErrorType(ErrorType.INTERNAL_ERROR.value, arrayOf(ErrorTypeMessage.INTERNAL_ERROR.message))
                    val apiResponse = ApiResponse(apiErrorType)

                    call.respond(HttpStatusCode.InternalServerError, apiResponse)
                }
            }
        }
    }
}
