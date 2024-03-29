package com.example.e_commerceapp.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentLoginBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.util.Constants.EMAIL
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.observeNonNull
import com.example.e_commerceapp.util.setUpBottomSheetDialog
import com.example.e_commerceapp.util.togglePasswordVisibility
import com.google.android.material.snackbar.Snackbar


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModelClass: Class<out LoginViewModel>
        get() = LoginViewModel::class.java

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPasswordVisibilityToggle()
        clickForSignUp()
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                viewModel.loginUser(email, password)
                BaseShared.saveString(mContext, EMAIL, email)
            }

            textViewContinueWithRegister.setOnClickListener {
                navigateSafe(R.id.action_loginFragment_to_registerFragment)
            }

            textViewForgotPassword.setOnClickListener {
                setUpBottomSheetDialog { email ->
                    viewModel.resetPassword(email)
                }
            }
        }
    }

    override fun setUpObservers() {
        viewModel.apply {
            loginResult.observeNonNull(viewLifecycleOwner) {
                if (it) {
                    navigateSafe(R.id.action_loginFragment_to_profileFragment)
                } else {
                    Toast.makeText(mContext, getString(R.string.login_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            resetPassword.observeNonNull(viewLifecycleOwner) { result ->
                if (result.isNotEmpty()) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.send_mail),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.empyt_mail),
                        Snackbar.LENGTH_LONG
                    ).show()
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

    private fun clickForSignUp() {
        binding?.textViewContinueWithRegister?.setOnClickListener {
            navigateSafe(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setUpAppBar() {
        AppUtils.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.login))
    }
}