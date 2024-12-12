package com.dicoding.mandoor.splashscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.R
import com.dicoding.mandoor.databinding.ActivitySplashScreenBinding
import com.dicoding.mandoor.ui.onboarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logo = findViewById<ImageView>(R.id.splash_mandoor)

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair.create(logo, "image")
        )
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
            startActivity(intent, options.toBundle())
            finish()
        }, 3000)
    }
}