package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentUserBinding
import com.gmail.assetkikbayev.locationtracker.viewmodel.UserViewModel

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveLocation()
        fragmentBinding.logoutButton.setOnClickListener {
            viewModel.logout()
            navController.navigate(R.id.loginFragment)
    //            navController.navigate(R.id.action_userFragment_to_loginFragment2)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater, container, false)
    }

    override fun getViewModel(): Class<UserViewModel> {
        return UserViewModel::class.java
    }

}