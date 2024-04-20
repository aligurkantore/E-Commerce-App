package com.example.e_commerceapp.ui.fragments.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentCategoryDetailBinding
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.util.Constants.CATEGORY
import com.example.e_commerceapp.util.goneIf
import com.example.e_commerceapp.util.navigateSafe

class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding, CategoryDetailViewModel>() {

    override val viewModelClass: Class<out CategoryDetailViewModel>
        get() = CategoryDetailViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCategoryDetailBinding {
        return FragmentCategoryDetailBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserLoginStatus(viewModel.isLoggedIn())
    }


    override fun setUpListeners() {
        val nameOfCategory = BaseShared.getString(mContext, CATEGORY, "") ?: ""
        binding?.apply {
            textViewCategoryName.text = nameOfCategory
            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_categoryDetailFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {}

    private fun checkUserLoginStatus(isLogin: Boolean) {
        binding?.apply {
            textViewDescription goneIf isLogin
            buttonLogin goneIf isLogin
        }
    }
}