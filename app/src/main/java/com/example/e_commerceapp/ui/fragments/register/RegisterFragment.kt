package com.example.e_commerceapp.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentRegisterBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.util.Constants.EMAIL
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.observeNonNull
import com.example.e_commerceapp.util.togglePasswordVisibility

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModelClass: Class<out RegisterViewModel>
        get() = RegisterViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPasswordVisibilityToggle()
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonRegister.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                viewModel.registerUser(name, email, password)
                BaseShared.saveString(mContext, EMAIL, email)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.apply {
            registrationResult.observeNonNull(viewLifecycleOwner) {
                if (it) {
                    navigateSafe(R.id.action_registerFragment_to_profileFragment)
                } else {
                    Toast.makeText(mContext, getString(R.string.register_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding?.editTextPassword?.apply {
            setOnClickListener {
                togglePasswordVisibility()
            }
        }
    }

    private fun setUpAppBar(){
        AppUtils.updateAppBarTitle(mContext as AppCompatActivity,getString(R.string.register))
    }
}