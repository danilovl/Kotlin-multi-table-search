package danilovl.plugins

import danilovl.model.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.requestValidation() {
    install(RequestValidation) {
        validate<Array<SearchParam>> { params ->
            for (param in params) {
                if (param.identifier.isEmpty()) {
                    return@validate ValidationResult.Invalid("Identifier must be not empty")
                }

                if (param.tableName.isEmpty()) {
                    return@validate ValidationResult.Invalid("TableName must be not empty")
                }

                if (param.selectColumns.isEmpty()) {
                    return@validate ValidationResult.Invalid("SelectColumns must be not empty")
                }
            }

            return@validate ValidationResult.Valid
        }
    }
}
