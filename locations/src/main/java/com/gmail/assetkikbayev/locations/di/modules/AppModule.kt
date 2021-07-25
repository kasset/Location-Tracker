package com.gmail.assetkikbayev.locations.di.modules

import dagger.Module

@Module(
    includes = [
        ActivityModule::class,
        FragmentsModule::class,
        FirebaseModule::class,
        ViewModelsModule::class,
        RepositoriesModule::class,
    ]
)
interface AppModule