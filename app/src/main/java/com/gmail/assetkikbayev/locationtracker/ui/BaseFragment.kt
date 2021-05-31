package com.gmail.assetkikbayev.locationtracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gmail.assetkikbayev.locationtracker.di.modules.ViewModelFactory
import com.gmail.assetkikbayev.locationtracker.viewmodel.AuthViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelFactory
    protected lateinit var authViewModel: AuthViewModel
    var fragmentBinding: T? = null
    private val binding get() = fragmentBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        authViewModel = ViewModelProvider(this, providerFactory)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = getFragmentBinding(inflater, container)
        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}