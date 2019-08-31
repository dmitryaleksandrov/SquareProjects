package com.example.squareprojects.api

import com.example.squareprojects.api.model.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface IGitHub {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("/search/repositories")
    suspend fun getRepositories(@Query("q") query: String, @Query("per_page") limit: Int): GetRepositoriesByUserResponse

}

data class GetRepositoriesByUserResponse(
    val items: List<Repository>
)