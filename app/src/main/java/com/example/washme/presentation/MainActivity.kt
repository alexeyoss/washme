package com.example.washme.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.washme.R
import com.example.washme.databinding.ActivityMainBinding
import com.example.washme.utils.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.dataContainer, MapFragment())
            .commit()
    }
}