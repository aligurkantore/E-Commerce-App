package com.example.e_commerceapp.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: BaseRepository) : BaseViewModel() {

    private val _productLiveData = MutableLiveData<String>()
    val productLiveData: LiveData<String> = _productLiveData


    fun getSingleProduct(productId: String){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repository.getSingleProduct(productId)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        _productLiveData.postValue(it.id.toString())
                        Log.d("agt1", "getSingleProduct:${response.body()} ")
                    }
                }
            }
        }
    }

}