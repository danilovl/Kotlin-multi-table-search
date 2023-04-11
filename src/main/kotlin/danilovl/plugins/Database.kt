package danilovl.plugins

import danilovl.service.Database
import io.ktor.server.application.*

fun Application.configureDatabase() {
    Database.getConnection()
}
