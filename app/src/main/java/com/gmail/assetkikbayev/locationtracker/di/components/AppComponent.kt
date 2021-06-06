package com.gmail.assetkikbayev.locationtracker.di.components

import com.gmail.assetkikbayev.locationtracker.di.App
import com.gmail.assetkikbayev.locationtracker.di.modules.FragmentsModule
import com.gmail.assetkikbayev.locationtracker.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }
}