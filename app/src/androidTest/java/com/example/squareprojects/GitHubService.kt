package com.example.squareprojects

import com.example.squareprojects.api.GetRepositoriesByUserResponse
import com.example.squareprojects.api.model.Repository
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.util.concurrent.TimeUnit

class GitHubService {

    companion object {
        const val PORT = 6000
    }

    private val repositories = mutableListOf<Repository>()

    fun addRepository(repository: Repository) {
        repositories.add(repository)
    }

    var failRepositoryListFetch: Boolean = false

    private val server = embeddedServer(Netty, PORT) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        routing {
            get("/search/repositories") {
                if (failRepositoryListFetch) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(HttpStatusCode.OK, GetRepositoriesByUserResponse(repositories))
                }
            }
        }
    }


    fun startService() {
        server.start(wait = false)
    }

    fun stopService() {
        server.stop(3000, 3000, TimeUnit.MILLISECONDS)
    }

}