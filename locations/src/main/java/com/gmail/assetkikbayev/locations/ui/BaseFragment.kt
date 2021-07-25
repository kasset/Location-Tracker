package com.gmail.assetkikbayev.locations.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.gmail.assetkikbayev.locations.di.modules.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding, E : ViewModel> : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelFactory
    lateinit var navController: NavController
    protected lateinit var viewModel: E
    lateinit var fragmentBinding: T
    private val binding get() = fragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, providerFactory)[getViewModel()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): T

    abstract fun getViewModel(): Class<E>

}