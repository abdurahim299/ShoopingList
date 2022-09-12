package com.timesoft.shoopinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}