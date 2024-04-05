package com.example.e_commerceapp.ui.fragments.categorydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentCategoryDetailBinding
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.util.Constants.CATEGORY

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


    override fun setUpListeners() {
        val nameOfCategory = BaseShared.getString(mContext, CATEGORY, "") ?: ""
        binding?.textViewCategoryName?.text = nameOfCategory
    }

    override fun setUpObservers() {}

    override fun onPause() {
        super.onPause()
        BaseShared.removeKey(mContext, CATEGORY)
    }
}