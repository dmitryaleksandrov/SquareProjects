package com.example.squareprojects.core

import com.example.squareprojects.Application
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideGitHubManager(@Named(Application.BASE_URL) baseUrl: String): IGitHubManager =
        GitHubManager(baseUrl)

}