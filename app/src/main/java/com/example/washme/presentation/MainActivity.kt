package com.example.washme.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.washme.ApplicationViewModelImpl
import com.example.washme.R
import com.example.washme.data.entities.UserLocation
import com.example.washme.databinding.ActivityMainBinding
import com.example.washme.utils.Locations
import com.example.washme.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val applicationViewModel: ApplicationViewModelImpl by viewModels()
    private var lastUserLocation: UserLocation? = null

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(), ::onPermissionsResult
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
        // TODO test observeOnce functionality
        applicationViewModel.locationLiveData.observeOnce(this) { location ->
            lastUserLocation = location
            launchScreen()

            applicationViewModel.saveLocationIntoDB()
        }

    }

    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { it.value }) {
            initListeners()
            applicationViewModel.startLocationUpdates()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                launchLocationPermissionsLauncher()
            } else {
                launchScreen()
            }
        }
    }


    private fun launchLocationPermissionsLauncher() {
        locationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun launchScreen() {
        // TODO put args via navigation component
        supportFragmentManager.beginTransaction().replace(
            R.id.dataContainer, MapFragment.newInstance(
                lastUserLocation ?: Locations.DEFAULT_POINT_COORDINATION
            )
        ).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationPermissionsLauncher.unregister()
    }
}