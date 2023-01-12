package com.example.washme.presentation

import android.Manifest
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.washme.ApplicationViewModel
import com.example.washme.R
import com.example.washme.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val applicationViewModel: ApplicationViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(),
        ::onPermissionsResult
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        launchLocationPermissionsLauncher()
    }

    private fun initListeners() {
        applicationViewModel.locationLiveData.observe(this) { location ->
            checkNotNull(location)
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.dataContainer,
                    MapFragment.newInstance(
                        CustomPoint(location.longitude, location.latitude)
                    )
                ).commit()
            // TODO put args via navigation component
        }
    }

    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { it.value }) {
            applicationViewModel.startLocationUpdates()

            initListeners()
        } else {
            launchLocationPermissionsLauncher()
        }
    }

    private fun launchLocationPermissionsLauncher() {
        locationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        locationPermissionsLauncher.unregister()
    }

    @Parcelize
    data class CustomPoint(
        val longitude: Double,
        val latitude: Double,
    ) : Parcelable


}