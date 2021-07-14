package com.gmail.assetkikbayev.locations.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.assetkikbayev.locations.databinding.FragmentLoginBinding
import com.gmail.assetkikbayev.locations.utils.Resource
import com.gmail.assetkikbayev.locations.viewmodel.AuthViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.signButton.setOnClickListener {
            val email = fragmentBinding.loginEditText.text.toString().trim()
            val password = fragmentBinding.passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginByEmail(email, password)
            }
        }
        observeRegisterResult()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    private fun observeRegisterResult() {
        viewModel.getAuthLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                    val action = LoginFragmentDirections.actionLoginFragmentToMapsFragment()
                    navController.navigate(action)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context,
                        "Login failed: ${state.throwable?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}