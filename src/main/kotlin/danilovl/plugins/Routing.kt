package danilovl.plugins

import danilovl.model.*
import danilovl.service.Search
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        get("/ping") {
            call.respond(SimpleResponse())
        }

        post("/multi-table-search") {
            val searchParams = call.receive<Array<SearchParam>>()
            val result: List<MutableMap<String, SearchInTableResult>> = Search.search(searchParams)

            val apiResponse = ApiResponse(null, result)

            call.respond(apiResponse)
        }
    }
}
