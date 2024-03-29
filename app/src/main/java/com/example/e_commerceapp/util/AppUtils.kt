package com.example.e_commerceapp.util

import androidx.appcompat.app.AppCompatActivity

object AppUtils {

    fun updateAppBarTitle(activity: AppCompatActivity, title: String) {
        activity.supportActionBar?.title = title
    }
}