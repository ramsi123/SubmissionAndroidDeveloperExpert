package com.example.submissionandroiddeveloperexpert.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.submissionandroiddeveloperexpert.R
import com.example.submissionandroiddeveloperexpert.databinding.ActivitySplashScreenBinding
import com.example.submissionandroiddeveloperexpert.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setting up view model
        val splashScreenViewModel: SplashScreenViewModel by viewModels()

        // observing theme preferences
        splashScreenViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                binding.imgLogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.imgLogo.context,
                        R.drawable.ic_logo_dark
                    )
                )
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.imgLogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.imgLogo.context,
                        R.drawable.ic_logo_light
                    )
                )
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler().postDelayed({
            val moveToMainActivityIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveToMainActivityIntent)
            finish()
        }, 3000)
    }

}