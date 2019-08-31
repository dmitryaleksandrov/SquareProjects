package com.example.squareprojects.core

import com.example.squareprojects.api.IGitHub
import com.example.squareprojects.api.model.Repository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubManager(baseUrl: String) : IGitHubManager {

    private val gitHub = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IGitHub::class.java)

    override suspend fun getRepositoriesByUser(user: String, limit: Int): List<Repository> =
            withContext(IO) {
                gitHub.getRepositories("user:$user", limit).items
            }

}