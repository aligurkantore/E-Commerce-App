package com.example.e_commerceapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private var job: Job? = null

    private var isLoading = MutableLiveData<Boolean>()

    private var error = MutableLiveData<String>()


    protected fun <T> performRequest(
        liveData: MutableLiveData<T>,
        request: suspend () -> Response<T>,
    ) {
        isLoading.value = true
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveData.postValue(it)
                        }
                    } else {
                        error.postValue(response.message())
                    }
                }
            } catch (e: Exception) {
                error.postValue(e.message ?: "Unknown error occurred.")
            } finally {
                isLoading.postValue(false)
            }
        }
    }


    fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}