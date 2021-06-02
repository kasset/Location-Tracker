package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentSignupBinding
import com.gmail.assetkikbayev.locationtracker.utils.Resource.*
import com.gmail.assetkikbayev.locationtracker.viewmodel.AuthViewModel

class SignupFragment : BaseFragment<FragmentSignupBinding, AuthViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding?.signupButton?.setOnClickListener {
            val email = fragmentBinding?.signupEditText?.text.toString().trim()
            val password = fragmentBinding?.passwordEditText?.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.registerByEmail(email, password)
            }
        }
        observeRegisterResult()
    }

    private fun observeRegisterResult() {
        viewModel.getAuthData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is Success -> {
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.action_signupFragment_to_userFragment)
                }
                is Failure -> {
                    Toast.makeText(context, "Registration is failed", Toast.LENGTH_LONG).show()
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