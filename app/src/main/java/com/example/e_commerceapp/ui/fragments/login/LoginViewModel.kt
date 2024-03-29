package com.example.e_commerceapp.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.util.containsTurkishCharacters
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    //private val sharedPreferences = application.getSharedPreferences("user_session",Context.MODE_PRIVATE)

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _resetPassword = MutableLiveData<String>()
    val resetPassword: LiveData<String> = _resetPassword


    fun loginUser(email: String, password: String) {
        if ((email.isNotEmpty() && password.isNotEmpty()) && !email.containsTurkishCharacters()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _loginResult.value = task.isSuccessful
                }
        } else {
            _loginResult.value = false
        }
    }

    fun resetPassword(email: String) {
        if (email.isNotEmpty()) {
            viewModelScope.launch {
                auth.sendPasswordResetEmail(email)
                    .addOnFailureListener { exception ->
                        _resetPassword.value =
                            exception.message ?: "An error occurred while sending reset link."
                    }
            }
        } else {
            _resetPassword.value = "Please provide an email address."
        }
    }
    /*
        private fun saveUser(email: String, password: String){
            val editor = sharedPreferences.edit()
            editor.putString("email",email)
            editor.putString("password",password)
            editor.apply()
        }

     */
}