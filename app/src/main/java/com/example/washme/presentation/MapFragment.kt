package com.example.washme.presentation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washme.R
import com.example.washme.data.entities.WashMePoint
import com.example.washme.databinding.FragmentMapBinding
import com.example.washme.utils.ConstHolder
import com.example.washme.utils.ConstHolder.REQUEST_CODE_LOCATION_PERMISSION
import com.example.washme.utils.Locations
import com.example.washme.utils.TrackingUtility
import com.example.washme.utils.collectOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map.CameraCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), EasyPermissions.PermissionCallbacks {

    private var binding: FragmentMapBinding? = null
    private val viewModel: MapFragmentViewModelImpl by viewModels()

    private val startCameraCallback by lazy(LazyThreadSafetyMode.NONE) {
        CameraCallback { viewModel.getStartRandomPoints(ConstHolder.DEFAULT_AMOUNT_OF_POINTS) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            restoreCameraState()
        } else {
            initMapSettings()
        }

        initListeners()
        checkPermissionsAndRenderLocationButton()
    }

    private fun initMapSettings() {
        val binding = checkNotNull(binding)
        with(binding) {
            mapView.map.move(
                PreconfiguredCameraPosition(
                    Locations.DefaultCoordination.getPoint(),
                ), Animation(Animation.Type.SMOOTH, 3f), startCameraCallback
            )

            mapView.map.mapObjects
        }
    }

    private fun restoreCameraState() {
        val binding = checkNotNull(binding)
        with(binding) {
            mapView.map.move(
                PreconfiguredCameraPosition(
                    Locations.UserCoordination(
                        Point(
                            0.0, 0.0
                        )
                    ).getPoint(),
                )
            )
        }
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)

        with(binding) {
            viewModel.pointsStateFlow.collectOnLifecycle(this@MapFragment) { commonState ->
                when (commonState) {
                    is CommonStates.Success<*> -> {
                        (commonState.data as List<WashMePoint>).forEach { washMePoint ->
                            mapView.map.mapObjects.addPlacemark(
                                Point(
                                    washMePoint.latitude,
                                    washMePoint.longitude
                                )
                            )
                        }
                    }
                    is CommonStates.Loading -> Unit
                    is CommonStates.Empty -> Unit
                    is CommonStates.Error<*> -> Unit
                }
            }

            locationCursor.setOnClickListener {
                requestPermissions()
            }
        }
    }

    private fun checkPermissionsAndRenderLocationButton() {
        val binding = checkNotNull(binding)
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            binding.locationCursor.isSelected = true
        }
    }

    private fun requestPermissions() {
        val binding = checkNotNull(binding)
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            // TODO Bug with rendering location button
            binding.locationCursor.isSelected = true
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Permissions provided",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.perm_rational_text),
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

        }
    }

    override fun onStart() {
        super.onStart()
        val binding = checkNotNull(binding)
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()

    }

    override fun onStop() {
        val binding = checkNotNull(binding)
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = checkNotNull(binding)
        binding.locationCursor.setOnClickListener(null)
        this.binding = null
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        val binding = checkNotNull(binding)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
            binding.locationCursor.isSelected = true
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private class PreconfiguredCameraPosition(
        target: Point, zoom: Float = 9.0f, azimuth: Float = 0.0f, tilt: Float = 0.0f
    ) : CameraPosition(target, zoom, azimuth, tilt)

    private data class RestoreData(
        val lastLocation: Point,
        val lastMapZoom: Float,
        val mapPointId: Int?
    )
}


fun com.yandex.mapkit.map.Map.addPlaceMark() {

}