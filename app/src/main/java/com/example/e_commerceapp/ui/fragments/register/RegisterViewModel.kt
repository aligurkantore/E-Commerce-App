package com.example.e_commerceapp.ui.fragments.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerceapp.base.BaseViewModel
import com.example.e_commerceapp.util.containsTurkishCharacters

class RegisterViewModel : BaseViewModel() {

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult

    fun registerUser(name: String, email: String, password: String) {
        if ((email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty())
            && !email.containsTurkishCharacters()
        ) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _registrationResult.value = task.isSuccessful
                }
        } else {
            _registrationResult.value = false
        }
    }
}