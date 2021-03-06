package com.gmail.assetkikbayev.locationtracker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.gmail.assetkikbayev.locationtracker.databinding.FragmentUserBinding
import com.gmail.assetkikbayev.locationtracker.model.services.LocationService
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.viewmodel.UserViewModel

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback {}
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.logoutButton.setOnClickListener {
            viewModel.stopLocationUpdates()
            activity?.stopService(Intent(context, LocationService::class.java))
        }
        observeLogoutResult()
        observeLocationResult()
        observeStopLocationUpdatesResult()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStart() {
        super.onStart()
        startLocationService()
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
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE
            ),
            Constants.REQUEST_CODE_BACKGROUND
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startLocationService() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.FOREGROUND_SERVICE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getPermissions()
        } else {
            val intent = Intent(context, LocationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity?.startForegroundService(intent)
            } else {
                activity?.startService(intent)
            }
        }
    }

    private fun observeLogoutResult() {
        viewModel.getUserSingleEvent.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                        val action = UserFragmentDirections.actionUserFragmentToLoginFragment2()
                        navController.navigate(action)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context, "User couldn't logout. Try again later...", Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(context, "Logging out...", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private fun observeStopLocationUpdatesResult() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    viewModel.logout()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Location service isn't responding", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun observeLocationResult() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    Toast.makeText(context, "Location is saved", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    if ((state.throwable?.message == Constants.LOCATION_PERMISSION_ERROR)) {
                        getPermissions()
                    }
                }
            }
        })
    }

}