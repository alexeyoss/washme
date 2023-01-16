package com.example.washme.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.washme.ApplicationViewModelImpl
import com.example.washme.R
import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.UserLocation.Companion.toYandexPoint
import com.example.washme.data.entities.WashMePoint
import com.example.washme.data.entities.WashMePoint.Companion.toYandexPoint
import com.example.washme.databinding.ActivityMainBinding
import com.example.washme.presentation.RestoreState.Companion.toUserLocation
import com.example.washme.utils.ConstHolder
import com.example.washme.utils.lazyUnsafe
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map.CameraCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val applicationViewModel: ApplicationViewModelImpl by viewModels()
    private val viewModel: MainActivityViewModelImpl by viewModels()

    private val binding: ActivityMainBinding by lazyUnsafe {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var currentState: RestoreState? = null

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(), ::onPermissionsResult
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mapView.map.isRotateGesturesEnabled = false

        launchLocationPermissionsLauncher()

        savedInstanceState?.let {
            extractStateFromArgs(savedInstanceState)
            initListeners()
            launchMap(
                currentState?.toUserLocation()
                    ?: ConstHolder.DEFAULT_POINT_COORDINATION
            )
        }

    }

    private fun extractStateFromArgs(savedInstanceState: Bundle) {
        currentState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getParcelable(RESTORE_STATE_KEY, RestoreState::class.java)
        } else {
            savedInstanceState.getParcelable(RESTORE_STATE_KEY)
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    private fun provideCameraCallback(userLocation: UserLocation = ConstHolder.DEFAULT_POINT_COORDINATION): CameraCallback {
        return CameraCallback {
            viewModel.getStartRandomPoints(
                ConstHolder.DEFAULT_AMOUNT_OF_POINTS, userLocation
            )
        }
    }

    private fun initListeners() {
        // TODO test observeOnce functionality
        applicationViewModel.locationLiveData.observe(this) { location ->
            launchMap(location, false)

            applicationViewModel.saveLocationIntoDB()

//            setLocationIcon()
        }

        with(binding) {
            lifecycleScope.launchWhenCreated {
                viewModel.pointsStateFlow.collect { commonState ->
                    when (commonState) {
                        is CommonStates.Success<*> -> {
                            // TODO get rid of DOWNCASTING
                            (commonState.data as List<WashMePoint>).forEach { washMePoint ->
                                mapView.map.mapObjects.addPlacemark(washMePoint.toYandexPoint())
                            }
                        }
                        is CommonStates.Loading -> Unit
                        is CommonStates.Empty -> Unit
                        is CommonStates.Error<*> -> Unit
                    }
                }
            }


//            bottomNavigationView.setOnItemSelectedListener {item ->
//
//            }

        }
    }


    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { it.value }) {
            applicationViewModel.startLocationUpdates()
            initListeners()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                launchLocationPermissionsLauncher()
            } else {
                launchMap(ConstHolder.DEFAULT_POINT_COORDINATION, useZoom = true)
            }
        }
    }

    private fun launchMap(userLocation: UserLocation, useZoom: Boolean = false) {
        with(binding) {
            mapView.map.move(
                PreconfiguredCameraPositionAnimation(userLocation.toYandexPoint()),
                Animation(Animation.Type.SMOOTH, 3f),
                provideCameraCallback(userLocation)
            )
        }
    }


    private fun launchLocationPermissionsLauncher() {
        locationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationPermissionsLauncher.unregister()
    }


    companion object {

        @JvmStatic
        val RESTORE_STATE_KEY = "RESTORE_STATE_KEY"

        internal class PreconfiguredCameraPositionAnimation(
            target: Point, zoom: Float = 9.0f, azimuth: Float = 0.0f, tilt: Float = 0.0f
        ) : CameraPosition(target, zoom, azimuth, tilt)
    }
}

@Parcelize
internal data class RestoreState(
    val zoom: Float,
    val latitude: Double,
    val longitude: Double,
    val updateAt: OffsetDateTime? = null
) : Parcelable {
    companion object {
        fun RestoreState.toYandexPoint(): Point = Point(this.latitude, this.longitude)
        fun RestoreState.toUserLocation(): UserLocation =
            UserLocation(latitude = this.latitude, longitude = this.longitude)
    }
}

