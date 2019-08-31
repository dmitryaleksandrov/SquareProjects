package com.example.squareprojects

import com.example.squareprojects.core.CoreModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        CoreModule::class
    )
)
interface ApplicationComponent {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun setBaseUrl(@Named(Application.BASE_URL) baseUrl: String): Builder

    }

}