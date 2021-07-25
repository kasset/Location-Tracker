package com.gmail.assetkikbayev.locations.di.components

import android.app.Application
import com.gmail.assetkikbayev.locations.di.AppB
import com.gmail.assetkikbayev.locations.di.modules.AppModule
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
interface AppBComponent: AndroidInjector<AppB> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppBComponent
    }
}