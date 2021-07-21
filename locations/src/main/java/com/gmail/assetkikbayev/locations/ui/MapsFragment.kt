package com.gmail.assetkikbayev.locations.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import com.gmail.assetkikbayev.locations.R
import com.gmail.assetkikbayev.locations.databinding.FragmentMapsBinding
import com.gmail.assetkikbayev.locations.utils.Resource
import com.gmail.assetkikbayev.locations.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class MapsFragment : BaseFragment<FragmentMapsBinding, MapViewModel>(),
    DatePickerDialog.OnDateSetListener,
    OnMapReadyCallback {

    private lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.onBackPressedDispatcher?.addCallback {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        observeLogoutResult()
        observeUserCoordinates()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                viewModel.logout()
                return true
            }
            R.id.calendar -> {
                showCalendarDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapsBinding {
        return FragmentMapsBinding.inflate(inflater, container, false)
    }

    override fun getViewModel(): Class<MapViewModel> {
        return MapViewModel::class.java
    }

    private fun observeUserCoordinates() {
        viewModel.getMapLiveData.observe(viewLifecycleOwner, { data ->
            val coordinates = LatLng(
                data["LATITUDE"].toString().toDouble(),
                data["LONGITUDE"].toString().toDouble()
            )
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 3f))
            gMap.addMarker(MarkerOptions().position(coordinates).title("${data["TIMESTAMP"]}"))
        })
    }

    private fun observeLogoutResult() {
        viewModel.getMapSingleEvent.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> {
                    val action = MapsFragmentDirections.actionMapsFragmentToLoginFragment()
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showCalendarDialog() {
        DatePickerDialog(
            requireContext(),
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        var date: String = dateFormat.format(calendar.time)
        viewModel.getLocations(date)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
    }
}