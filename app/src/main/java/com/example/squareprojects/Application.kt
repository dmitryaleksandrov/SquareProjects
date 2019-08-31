package com.example.squareprojects

import com.example.squareprojects.api.IGitHub
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class Application : android.app.Application(), HasAndroidInjector {

    companion object {
        const val BASE_URL = "baseUrl"
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    protected open val baseUrl: String get() = IGitHub.BASE_URL

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().setBaseUrl(baseUrl)
                .build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

}