package com.bennyjon.searchi.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.bennyjon.searchi.databinding.ActivityMainBinding

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.androidViewsButton.setOnClickListener {
            startActivity(Intent(this, AndroidViewsActivity::class.java))
        }
    }
}
