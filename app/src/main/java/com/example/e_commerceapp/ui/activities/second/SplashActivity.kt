package com.example.e_commerceapp.ui.activities.second

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.databinding.ActivitySplashBinding
import com.example.e_commerceapp.ui.activities.main.MainActivity
import com.example.e_commerceapp.util.Constants.EN
import com.example.e_commerceapp.util.Constants.LANGUAGE
import com.example.e_commerceapp.util.changeLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpAnimation()
        setLanguage()
    }

    private fun setUpBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpAnimation() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        binding.imageSlash.startAnimation(slideAnimation)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setLanguage() {
        val language = BaseShared.getString(this, LANGUAGE, EN)
        if (language != null) {
            changeLanguage(language)
        }
    }
}
