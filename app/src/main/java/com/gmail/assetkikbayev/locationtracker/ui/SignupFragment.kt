package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentSignupBinding
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.gmail.assetkikbayev.locationtracker.utils.Resource.*
import com.gmail.assetkikbayev.locationtracker.viewmodel.AuthViewModel

class SignupFragment : BaseFragment<FragmentSignupBinding, AuthViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.signupButton.setOnClickListener {
            val email = fragmentBinding.signupEditText.text.toString().trim()
            val password = fragmentBinding.passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.registerByEmail(email, password)
            }
        }
        observeRegisterResult()
    }

    private fun observeRegisterResult() {
        viewModel.getAuthLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Success -> {
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                    val action = SignupFragmentDirections.actionSignupFragmentToUserFragment()
                    navController.navigate(action)
                }
                is Failure -> {
                    if (state.throwable?.message == Constants.USER_EXIST) {
                        Toast.makeText(context, "Email already exist", Toast.LENGTH_LONG).show()
                        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                        navController.navigate(action)
                    } else {
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
                    }
                }
                is Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(inflater, container, false)
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }
}