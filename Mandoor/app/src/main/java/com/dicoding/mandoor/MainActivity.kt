package com.dicoding.mandoor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.mandoor.databinding.ActivityMainBinding
import com.dicoding.mandoor.ui.Login.LoginActivity
import com.dicoding.mandoor.ui.onboarding.OnBoardingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ambil token JWT dari SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            // Token tidak ditemukan, arahkan ke LoginActivity
            Toast.makeText(this, "Token is missing. Please login again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Token ditemukan, lanjutkan aplikasi
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_activity_main)

            // Konfigurasi navigasi dengan BottomNavigationView
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_bangun, R.id.navigation_account
                )
            )
            navView.setupWithNavController(navController)
        }
    }

    override fun onBackPressed() {
        // Handle tombol kembali untuk memastikan navigasi tidak keluar aplikasi langsung
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        if (navController.currentDestination?.id != R.id.navigation_home) {
            navController.navigate(R.id.navigation_home)
        } else {
            super.onBackPressed() // Keluar aplikasi jika sudah di HomeFragment
        }
    }
}



