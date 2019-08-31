package com.example.squareprojects.core

import com.example.squareprojects.api.model.Repository

interface IGitHubManager {

    suspend fun getRepositoriesByUser(user: String, limit: Int): List<Repository>

}