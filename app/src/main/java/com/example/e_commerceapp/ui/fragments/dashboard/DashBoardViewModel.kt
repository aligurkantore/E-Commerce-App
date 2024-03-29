package com.example.e_commerceapp.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.models.datamodels.product.Category
import com.example.e_commerceapp.models.datamodels.product.ProductResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val repository: BaseRepository) :
    BaseViewModel() {

    private val _productLiveData = MutableLiveData<ProductResponseData>()
    val productLiveData: LiveData<ProductResponseData> = _productLiveData

    private val _categoryLiveData = MutableLiveData<Category>()
    val categoryLiveData: LiveData<Category> = _categoryLiveData


    /*

        private fun getProducts(limit: Int){
            viewModelScope.launch {
                _productLiveData.value = Resource.Loading()
                try {
                    val result = repository.getProducts(limit)
                    _productLiveData.postValue(result)
                    Log.d("agt", "getProducts:  ${productLiveData.value}")
                    Log.d("agt1", "getProducts:  ${_productLiveData.value}")
                }catch (e: Exception){
                    _productLiveData.value = e.message?.let { Resource.Error(it) }
                }
            }
        }

     */


    fun getProducts() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productLiveData.postValue(it)
                    }
                }
            }
        }
    }

    fun getCategories() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCategories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _categoryLiveData.postValue(it)
                    }
                }
            }
        }
    }
    /*

    fun fetchProducts(
        onSucess : (Any) -> Unit,
        onError : (Any) -> Unit
    ){
        fetchData(QueryType.Products,onSucess,onError)
    }

     */


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

