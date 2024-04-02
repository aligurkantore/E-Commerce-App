package com.example.e_commerceapp.util

import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.e_commerceapp.R

class ProgressBarUtil(private val context: Context, private val layout: ViewGroup) {

    private var progressBar: ProgressBar? = null

    init {
        initProgressBar()
    }

    private fun initProgressBar() {
        progressBar = ProgressBar(context)
        progressBar?.indeterminateTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        }

        progressBar?.layoutParams = params
        layout.addView(progressBar)
        hideProgressBar()
    }

    fun showProgressBar() {
        progressBar?.visible()
    }

    fun hideProgressBar() {
        progressBar?.gone()
    }
}
