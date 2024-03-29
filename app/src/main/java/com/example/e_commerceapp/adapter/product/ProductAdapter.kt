package com.example.e_commerceapp.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.ItemProductBinding
import com.example.e_commerceapp.util.loadImage

class ProductAdapter(
    private val productList: List<ProductResponseDataItem>,
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
            val product = productList[position]
            product.image?.let { imageProduct.loadImage(it) }
            textTitle.text = product.title
            textPrice.text = String.format("%.2f$", product.price)


            linearLayoutAddToCart.setOnClickListener { addToCart.onAddToCartClicked(product) }
            constraintProduct.setOnClickListener { navigateToDetail.invoke(product) }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface ItemClickListener {
        fun onAddToCartClicked(data: ProductResponseDataItem)
    }
}