package com.example.e_commerceapp.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BaseRepository) : BaseViewModel() {

    private val _productDetailLiveData = MutableLiveData<ProductResponseDataItem>()
    val productDetailLiveData: LiveData<ProductResponseDataItem> = _productDetailLiveData


    fun getSingleProduct(productId: String){
        performRequest(_productDetailLiveData){
            repository.getSingleProduct(productId)
        }
    }
}