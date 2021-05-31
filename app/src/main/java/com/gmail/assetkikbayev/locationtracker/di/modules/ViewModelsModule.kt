package com.gmail.assetkikbayev.locationtracker.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.assetkikbayev.locationtracker.di.ViewModelKey
import com.gmail.assetkikbayev.locationtracker.viewmodel.AuthViewModel
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
}