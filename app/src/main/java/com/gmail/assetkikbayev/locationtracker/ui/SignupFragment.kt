package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentSignupBinding
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.utils.Resource.*

class SignupFragment : BaseFragment<FragmentSignupBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding?.signupButton?.setOnClickListener {
            val email = fragmentBinding?.signupEditText?.text.toString().trim()
            val password = fragmentBinding?.passwordEditText?.text.toString().trim()
            authViewModel.registerByEmail(email, password)
        }
        observeRegisterResult()
    }

    private fun observeRegisterResult() {
        authViewModel.getAuthData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is Success -> {
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                    moveToUserFragment()
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

    private fun moveToUserFragment() {
        val userFragment = UserFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, userFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(inflater, container, false)
    }
}