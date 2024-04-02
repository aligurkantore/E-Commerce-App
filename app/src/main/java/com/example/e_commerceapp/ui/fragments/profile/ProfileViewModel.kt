package com.example.e_commerceapp.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.models.datamodels.profile.Currency

class ProfileViewModel : BaseViewModel(){

    private val _selectedCurrency = MutableLiveData<Currency>()
    val selectedCurrency: LiveData<Currency> = _selectedCurrency

    fun setSelectedCurrency(currency: Currency){
        _selectedCurrency.postValue(currency)
    }
}