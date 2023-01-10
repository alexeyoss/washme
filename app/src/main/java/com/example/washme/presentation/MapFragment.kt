package com.example.washme.presentation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washme.R
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
        initMapSettings()
        initListeners()
        renderLocationButton()
    }

    private fun initMapSettings() {
        val binding = checkNotNull(binding)
        with(binding) {
            mapView.map.move(
                CameraPosition(
                    Locations.DefaultCoordination.getPoint(), 9.0f, 0.0f, 0.0f
                ), Animation(Animation.Type.SMOOTH, 3f), startCameraCallback
            )
        }
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)

        with(binding) {
            viewModel.pointsStateFlow.collectOnLifecycle(this@MapFragment) { commonState ->
                when (commonState) {
                    is CommonStates.Success<*> -> {
                        (commonState.data as HashSet<Point>).forEach { point ->
                            mapView.map.mapObjects.addPlacemark(point)
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

    private fun renderLocationButton() {
        val binding = checkNotNull(binding)
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            binding.locationCursor.isSelected = true
        }
    }

    private fun requestPermissions() {
        val binding = checkNotNull(binding)
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
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
        MapKitFactory.getInstance().onStart()
        binding!!.mapView.onStart()

    }

    override fun onStop() {
        binding!!.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
            renderLocationButton() // TODO not working render
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
