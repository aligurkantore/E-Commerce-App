package com.example.e_commerceapp.ui.fragments.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.FragmentDetailBinding
import com.example.e_commerceapp.util.Constants.DETAIL
import com.example.e_commerceapp.util.gone
import com.example.e_commerceapp.util.loadImage
import com.example.e_commerceapp.util.observeNonNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private var productId: String? = null

    override val viewModelClass: Class<out DetailViewModel>
        get() = DetailViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgs()
    }

    override fun setUpListeners() {
     //   TODO("Not yet implemented")
    }

    override fun setUpObservers() {
        viewModel.productDetailLiveData.observeNonNull(viewLifecycleOwner) {
            setUi(it)
        }
    }

    private fun setArgs() {
        productId = arguments?.getString(DETAIL)
        productId?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getSingleProduct(it)
                Log.d("agt", "setArgs: ${viewModel.getSingleProduct(it)} ")
            }
        }
    }

    private fun setUi(data: ProductResponseDataItem) {
        binding?.apply {
            data.image?.let { imageViewProduct.loadImage(it) }
            textViewTitle.text = data.title
            textViewDescription.text = data.description
            textViewPrice.text = String.format("%.2f$", data.price)
            data.rating?.rate?.toFloat()?.let { rating ->
                ratingBar.rating = rating
            } ?: run {
                ratingBar.gone()
            }
        }
    }
}