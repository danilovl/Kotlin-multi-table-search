package danilovl.plugins

import danilovl.model.ApiErrorType
import danilovl.model.ApiResponse
import danilovl.setting.EnvType
import danilovl.setting.ErrorType
import danilovl.setting.ErrorTypeMessage
import danilovl.setting.RequestHeaderType
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

val AuthTokenPlugin = createApplicationPlugin("AuthTokenPlugin") {
    onCall { call ->
        val token: String? = call.request.headers.get(RequestHeaderType.AUTH_TOKEN.value)
        var message: String? = null

        if (token === null) {
            message = ErrorTypeMessage.MISSING_AUTH_TOKEN.message
        }

        val dotenv = dotenv()
        if (token !== null && !token.equals(dotenv[EnvType.X_AUTH_TOKEN.value])) {
            message = ErrorTypeMessage.AUTH_TOKEN_INVALID.message
        }

        if (message === null) {
            return@onCall
        }

        val apiErrorType = ApiErrorType(ErrorType.AUTH_TOKEN.value, arrayOf(message))
        val apiResponse = ApiResponse(apiErrorType)

        call.respond(HttpStatusCode.Forbidden, apiResponse)
    }
}
