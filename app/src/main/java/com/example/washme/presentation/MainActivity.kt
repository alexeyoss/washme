package com.example.washme.presentation

import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.washme.R
import com.example.washme.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

//        locationManager.requestLocationUpdates(
//            LocationManager.NETWORK_PROVIDER,
//            1000 * 10,
//            10f,
//
//        )

        supportFragmentManager.beginTransaction().replace(R.id.dataContainer, MapFragment())
            .commit()
    }
}