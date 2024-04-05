package com.example.e_commerceapp.ui.fragments.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerceapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : BaseViewModel(){

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String>
        get() = _totalPrice

    fun setTotalPrice(price: String) {
        _totalPrice.value = price
    }
}

