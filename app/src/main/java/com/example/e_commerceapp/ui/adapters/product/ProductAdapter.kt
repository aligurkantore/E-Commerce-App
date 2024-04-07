package com.example.e_commerceapp.ui.adapters.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.ItemProductBinding
import com.example.e_commerceapp.util.Constants.CURRENCY
import com.example.e_commerceapp.util.Constants.USD
import com.example.e_commerceapp.util.convertAndFormatCurrency
import com.example.e_commerceapp.util.getCurrencySymbols
import com.example.e_commerceapp.util.loadImage

class ProductAdapter(
    private var context: Context,
    private var productList: List<ProductResponseDataItem>,
    private val navigateToDetail: (ProductResponseDataItem) -> Unit,
    private val addToCart: ItemClickListener,
) : RecyclerView.Adapter<ProductAdapter.ProductVH>() {


    inner class ProductVH(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        with(holder.binding) {
            val productData = productList[position]
            productData.image?.let { imageProduct.loadImage(it) }
            textTitle.text = productData.title

            val currency = BaseShared.getString(context, CURRENCY, USD)
            val currencySymbols = context.getCurrencySymbols()
            textPrice.text =
                productData.price?.convertAndFormatCurrency(USD, currency ?: USD, currencySymbols)

            linearLayoutAddToCart.setOnClickListener { addToCart.onAddToCartClicked(productData) }
            constraintProduct.setOnClickListener { navigateToDetail.invoke(productData) }
        }
    }

    override fun getItemCount(): Int = productList.size

    interface ItemClickListener {
        fun onAddToCartClicked(data: ProductResponseDataItem)
    }
}