package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentUserBinding

class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding?.logoutButton?.setOnClickListener { logout() }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater, container, false)
    }

    private fun logout() {
        authViewModel.logout()
        val loginFragment = LoginFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, loginFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}