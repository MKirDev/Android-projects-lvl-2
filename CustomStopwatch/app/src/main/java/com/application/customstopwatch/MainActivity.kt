package com.application.customstopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.customstopwatch.databinding.ActivityMainBinding
import com.application.customstopwatch.view.TimeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timeView.addUpdateListener(binding.customClock::startTime)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.timeView.removeUpdateListener(binding.customClock::startTime)
    }
}