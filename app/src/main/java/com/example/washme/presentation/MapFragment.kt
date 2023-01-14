package com.example.washme.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washme.R
import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.WashMePoint
import com.example.washme.data.entities.WashMePoint.Companion.toYandexPoint
import com.example.washme.databinding.FragmentMapBinding
import com.example.washme.utils.ConstHolder
import com.example.washme.utils.Locations
import com.example.washme.utils.collectOnLifecycle
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map.CameraCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

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
//        if (savedInstanceState != null) {
//            restoreCameraState()
//        } else {
//            initMapSettings()
//        }
        val result = requireArguments().getParcelable<UserLocation>(ARG_KEY)!!
        initMapSettings(Point(result.latitude, result.longitude))

        initListeners()
//        checkPermissionsAndRenderLocationButton()
    }

    private fun initMapSettings(point: Point) {
        val binding = checkNotNull(binding)

        with(binding) {

            mapView.map.isRotateGesturesEnabled = false
            mapView.map.move(
                PreconfiguredCameraPositionAnimation(point),
                Animation(Animation.Type.SMOOTH, 3f),
                startCameraCallback
            )


        }
    }

    private fun restoreCameraState() {
        val binding = checkNotNull(binding)
        with(binding) {
            mapView.map.move(
                PreconfiguredCameraPositionAnimation(
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
                        // TODO get rid of DOWNCASTING
                        (commonState.data as List<WashMePoint>).forEach { washMePoint ->
                            mapView.map.mapObjects.addPlacemark(
                                washMePoint.toYandexPoint()
                            )
                        }
                    }
                    is CommonStates.Loading -> Unit
                    is CommonStates.Empty -> Unit
                    is CommonStates.Error<*> -> Unit
                }
            }

            fab.setOnClickListener {
                ProfileBottomSheetDialogFragment().show(parentFragmentManager, "")
            }
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
        binding.fab.setOnClickListener(null)
        this.binding = null
    }

    internal class PreconfiguredCameraPositionAnimation(
        target: Point, zoom: Float = 9.0f, azimuth: Float = 0.0f, tilt: Float = 0.0f
    ) : CameraPosition(target, zoom, azimuth, tilt)

    // TODO handling savedInstanceState
    private data class RestoreData(
        val lastLocation: Point, val lastMapZoom: Float, val mapPointId: Int?
    )


    companion object {
        @JvmStatic
        val ARG_KEY = "ARG_KEY"

        fun newInstance(location: UserLocation): MapFragment {
            return MapFragment().apply {
                arguments = bundleOf(Pair(ARG_KEY, location))
            }
        }
    }
}
