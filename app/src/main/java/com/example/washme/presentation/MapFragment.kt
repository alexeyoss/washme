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
import com.example.washme.utils.ConstHolder.REQUEST_CODE_LOCATION_PERMISSION
import com.example.washme.utils.TrackingUtility
import com.example.washme.utils.collectOnLifecycle
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), EasyPermissions.PermissionCallbacks {

    private var binding: FragmentMapBinding? = null
    private val viewModel: MapFragmentViewModel by viewModels()

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
        initListeners()
        initMapSettings()
        requestPermissions()
    }

    private fun initMapSettings() {
        val binding = checkNotNull(binding)

        with(binding) {
//            mapView. TODO change the point image
            mapView.map.move(
                CameraPosition(
                    Point(59.945933, 30.320045), 14.0f, 0.0f, 0.0f
                ), Animation(Animation.Type.SMOOTH, 5f), null
            )
        }
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)

        viewModel.points.collectOnLifecycle(this@MapFragment) { commonState ->
            when (commonState) {
                is CommonStates.Success<*> -> {
                    (commonState.data as HashSet<Point>).forEach { point ->
                        with(binding) {
                            mapView.map.mapObjects.addPlacemark(point)
                        }
                    }
                }

                is CommonStates.Loading -> Unit
                is CommonStates.Empty -> Unit
                is CommonStates.Error<*> -> Unit
            }
        }
    }

    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app",
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

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
