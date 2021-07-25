package com.gmail.assetkikbayev.locations.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.assetkikbayev.locations.di.ViewModelKey
import com.gmail.assetkikbayev.locations.viewmodel.AuthViewModel
import com.gmail.assetkikbayev.locations.viewmodel.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun bindUserViewModel(viewModel: MapViewModel): ViewModel
}