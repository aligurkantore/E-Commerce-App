package com.example.e_commerceapp.ui.fragments.order

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentOrderBinding
import com.example.e_commerceapp.util.navigateSafe


class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>() {

    override val viewModelClass: Class<out OrderViewModel>
        get() = OrderViewModel::class.java

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        binding?.buttonNavigateToDashboard?.setOnClickListener {
            navigateSafe(R.id.action_orderFragment_to_dashBoardFragment)
        }
    }

    override fun setUpObservers() {}


}