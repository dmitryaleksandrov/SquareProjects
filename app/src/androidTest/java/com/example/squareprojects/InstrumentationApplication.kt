package com.example.squareprojects

class InstrumentationApplication : Application() {

    companion object {
        private const val BASE_URL = "http://localhost:${GitHubService.PORT}"
    }

    override val baseUrl: String get() = BASE_URL
}