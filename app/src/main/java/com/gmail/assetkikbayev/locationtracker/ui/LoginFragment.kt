package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentLoginBinding
import com.gmail.assetkikbayev.locationtracker.utils.Resource

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(authViewModel)
        fragmentBinding?.signupButton?.setOnClickListener {
            moveToSignupFragment()
        }
        fragmentBinding?.loginButton?.setOnClickListener {
            val email = fragmentBinding?.loginEditText?.text.toString().trim()
            val password = fragmentBinding?.passwordEditText?.text.toString().trim()
            authViewModel.loginByEmail(email, password)
        }
        observeRegisterResult()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    private fun observeRegisterResult() {
        authViewModel.getAuthData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                    moveToUserFragment()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun moveToUserFragment() {
        val userFragment = UserFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, userFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun moveToSignupFragment() {
        val signupFragment = SignupFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, signupFragment)
            ?.addToBackStack(SignupFragment::class.simpleName)
            ?.commit()
    }
}