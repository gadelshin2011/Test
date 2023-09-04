package com.example.myapplication

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var running = false
    var offset: Long = 0
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        binding.stopwatch.base = SystemClock.elapsedRealtime()


    }

    private fun setListener() {
        binding.startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }

        }

        binding.pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }

        }

        binding.resetButton.setOnClickListener {
            offset = 0
            binding.stopwatch.stop()
            setBaseTime()
        }
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime()- binding.stopwatch.base
    }

    private fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY,binding.stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        offset = savedInstanceState.getLong(OFFSET_KEY)
        running = savedInstanceState.getBoolean(RUNNING_KEY)
        if (running){
            binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
            binding.stopwatch.start()
        } else setBaseTime()
    }
}