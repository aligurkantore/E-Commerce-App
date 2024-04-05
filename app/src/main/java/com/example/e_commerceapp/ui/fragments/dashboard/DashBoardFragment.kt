package com.example.e_commerceapp.ui.fragments.dashboard

import com.example.e_commerceapp.ui.adapters.category.CategoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.adapters.product.ProductAdapter
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.FragmentDashBoardBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.util.Constants.DETAIL
import com.example.e_commerceapp.helper.FireBaseDataManager
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.navigateSafeWithArgs
import com.example.e_commerceapp.util.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding, DashBoardViewModel>() {

    private lateinit var adapterCategory: CategoryAdapter
    private var adapterProduct: ProductAdapter? = null

    override val viewModelClass: Class<out DashBoardViewModel>
        get() = DashBoardViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDashBoardBinding {
        return FragmentDashBoardBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarUtil.showProgressBar()
        viewModel.apply {
            getProducts()
            getCategories()
        }
        setUpAppBar()
    }

    override fun setUpListeners() {}

    override fun setUpObservers() {
        viewModel.apply {
            productLiveData.observeNonNull(viewLifecycleOwner) { product ->
                setUpProductAdapter(product)
                progressBarUtil.hideProgressBar()
            }

            categoryLiveData.observeNonNull(viewLifecycleOwner) { categoryNameList ->
                val categoryName = getString(R.string.all_products)
                val updatedCategoryList = mutableListOf(categoryName)
                updatedCategoryList.addAll(categoryNameList)
                setUpCategoryAdapter(updatedCategoryList)
            }
        }
    }


    private fun setUpProductAdapter(data: List<ProductResponseDataItem>) {
        adapterProduct =
            ProductAdapter(
                mContext,
                data,
                ::onClickAdapterItem,
                object : ProductAdapter.ItemClickListener {
                    override fun onAddToCartClicked(data: ProductResponseDataItem) {
                        checkUserLoginStatus(data)
                    }
                })
        binding?.recyclerViewProducts?.apply {
            adapter = adapterProduct
            layoutManager = GridLayoutManager(mContext, 2)
        }
    }

    private fun setUpCategoryAdapter(data: List<String>) {
        adapterCategory = CategoryAdapter(data, object : CategoryAdapter.ItemClickListener {
            override fun onCategoryClicked(position: Int) {
                val category = data[position]
                val filteredProducts = getProductsByCategory(category)
                if (filteredProducts != null) {
                    if (category == getString(R.string.all_products)) {
                        setUpProductAdapter(viewModel.productLiveData.value ?: emptyList())
                    } else {
                        setUpProductAdapter(filteredProducts)
                    }
                    adapterCategory.notifyDataSetChanged()
                }
            }
        })
        binding?.recyclerViewCategories?.apply {
            adapter = adapterCategory
            layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getProductsByCategory(category: String): List<ProductResponseDataItem>? {
        return viewModel.productLiveData.value?.filter { it.category == category }
    }

    private fun checkUserLoginStatus(data: ProductResponseDataItem) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToCart(mContext, data)
        else navigateSafe(R.id.action_dashBoardFragment_to_loginFragment)
    }

    private fun onClickAdapterItem(data: ProductResponseDataItem) {
        val bundle = Bundle().apply {
            putSerializable(DETAIL, data.id)
        }
        navigateSafeWithArgs(R.id.action_dashBoardFragment_to_detailFragment, bundle)
    }

    private fun setUpAppBar() {
        AppUtils.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.home))
    }
}