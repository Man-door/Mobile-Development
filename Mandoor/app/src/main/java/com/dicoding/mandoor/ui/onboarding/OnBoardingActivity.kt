package com.dicoding.mandoor.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.databinding.ActivityOnBoardingBinding
import com.dicoding.mandoor.ui.Login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    private val sharedPrefs by lazy {
        getSharedPreferences("AppPreferences", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isOnboardingShown()) {
            navigateToLogin()
        } else {
            markOnboardingAsShown()

            binding.buttonuser.setOnClickListener {
                navigateToLogin()
            }

            binding.buttonmandor.setOnClickListener {
                navigateToLogin()
            }
        }
    }

    private fun isOnboardingShown(): Boolean {
        return sharedPrefs.getBoolean("isOnboardingShown", false)
    }

    private fun markOnboardingAsShown() {
        sharedPrefs.edit().putBoolean("isOnboardingShown", true).apply()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
