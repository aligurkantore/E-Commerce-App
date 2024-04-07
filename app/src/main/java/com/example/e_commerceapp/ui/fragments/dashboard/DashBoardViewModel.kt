package com.example.e_commerceapp.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.models.datamodels.product.Category
import com.example.e_commerceapp.models.datamodels.product.ProductResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val repository: BaseRepository) :
    BaseViewModel() {

    private val _productLiveData = MutableLiveData<ProductResponseData>()
    val productLiveData: LiveData<ProductResponseData> = _productLiveData

    private val _categoryLiveData = MutableLiveData<Category>()
    val categoryLiveData: LiveData<Category> = _categoryLiveData


    fun getProducts() {
        performRequest(_productLiveData) {
            repository.getProducts()
        }
    }

    fun getCategories() {
        performRequest(_categoryLiveData) {
            repository.getCategories()
        }
    }
}

