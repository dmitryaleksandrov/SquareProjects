package com.example.squareprojects

import com.example.squareprojects.ui.RepositoryListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ApplicationModule {

    @ContributesAndroidInjector
    fun contributeRepositoryListActivity(): RepositoryListActivity

}