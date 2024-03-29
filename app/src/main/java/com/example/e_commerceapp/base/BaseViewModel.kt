package com.example.e_commerceapp.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    protected var job: Job? = null


    fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }


    /*

    private val _productLiveData = MutableLiveData<ProductResponseData>()
    val productLiveData: LiveData<ProductResponseData> = _productLiveData

    private val _categoryLiveData = MutableLiveData<Categories>()
    val categoryLiveData: LiveData<Categories> = _categoryLiveData



    enum class QueryType{
        Products,
        Categories
    }

    fun fetchData(
        queryType: QueryType,
        onSuccess: (Any) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
            val response = when(queryType){
                QueryType.Products -> repository.getProducts()
                QueryType.Categories -> repository.getCategories()
            }
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        onSuccess(it)
                        when(queryType){
                            QueryType.Products -> _productLiveData.postValue(it as ProductResponseData)
                            QueryType.Categories -> _categoryLiveData.postValue(it as Categories)
                        }
                    } ?: onError("${queryType.name} response body is null")
                } else {
                    onError("Failed to get ${queryType.name.toLowerCase()}: ${response.message()}")
                }
            }
        }
    }

     */
}