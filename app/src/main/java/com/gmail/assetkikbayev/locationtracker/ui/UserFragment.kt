package com.gmail.assetkikbayev.locationtracker.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.gmail.assetkikbayev.locationtracker.R
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentUserBinding
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.viewmodel.UserViewModel

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.logoutButton.setOnClickListener {
            viewModel.logout()
            navController.navigate(R.id.loginFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()
        getPermissions()
        viewModel.saveLocation()
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


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            REQUEST_CODE_BACKGROUND
        )
    }

    /*
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun observeRegisterResult() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    Toast.makeText(context, "Location is saved", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    if (
                        (state.throwable?.message == "GPS_PERMISSION") ||
                        (state.throwable?.message == "NETWORK_PERMISSION")
                    ) {
                        getPermissions()
                    }
                }
            }
        })
    }*/

    companion object {
        const val REQUEST_CODE_BACKGROUND = 1
    }
}