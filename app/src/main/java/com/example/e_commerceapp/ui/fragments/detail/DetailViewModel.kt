package com.example.e_commerceapp.ui.fragments.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BaseRepository) : BaseViewModel() {

    private val _productDetailLiveData = MutableLiveData<ProductResponseDataItem>()
    val productDetailLiveData: LiveData<ProductResponseDataItem> = _productDetailLiveData


    fun getSingleProduct(productId: String){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repository.getSingleProduct(productId)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        _productDetailLiveData.postValue(it)
                        Log.d("agt1", "getSingleProduct:${response.body()} ")
                        Log.d("agt1", "getSingleProduct:${_productDetailLiveData.postValue(it)} ")
                    }
                }
            }
        }
    }

}