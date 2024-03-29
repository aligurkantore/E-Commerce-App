package com.example.e_commerceapp.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.e_commerceapp.databinding.MyCustomDialogBinding

class CustomDialog(
    context: Context,
    private val title: String,
    private val message: String,
    private val positiveButtonText: String? = null,
    private val negativeButtonText: String? = null,
    private val showPositiveButton: Boolean = true,
    private val showNegativeButton: Boolean = true,
    private val positiveButtonClickListener: (() -> Unit)? = null,
    private val negativeButtonClickListener: (() -> Unit)? = null,
) : Dialog(context) {

    private lateinit var binding: MyCustomDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setUpBinding()
        setListeners()
    }

    private fun setUpBinding(){
        binding = MyCustomDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    private fun setListeners() {
        binding.apply {
            textViewTitle.text = title
            textViewMessage.text = message
            buttonPositive.text = positiveButtonText
            buttonNegative.text = negativeButtonText

            buttonPositive.visibility = if (showPositiveButton) View.VISIBLE else View.GONE
            buttonPositive.setOnClickListener {
                positiveButtonClickListener?.invoke()
                dismiss()
            }

            buttonNegative.visibility = if (showNegativeButton) View.VISIBLE else View.GONE
            buttonNegative.setOnClickListener {
                negativeButtonClickListener?.invoke()
                dismiss()
            }
        }
    }
}
