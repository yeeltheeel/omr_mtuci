package com.example.mobileproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlin.jvm.java

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            when {
                !hasInternetConnection(this) -> {
                    startActivity(Intent(this, NoInternetActivity::class.java))
                }
                !getSharedPreferences("app", MODE_PRIVATE).getBoolean("onboarding_done", false) -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
        }, 2500)
    }
}
