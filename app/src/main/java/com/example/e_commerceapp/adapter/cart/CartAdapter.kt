package com.example.e_commerceapp.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.ItemCartsBinding
import com.example.e_commerceapp.util.loadImage

class CartAdapter(
    private var cartList: List<ProductResponseDataItem>,
) : RecyclerView.Adapter<CartAdapter.CartVH>() {

    inner class CartVH(val binding: ItemCartsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = ItemCartsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartVH(view)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        with(holder.binding) {
            val cartData = cartList[position]
            cartData.image?.let { imageProduct.loadImage(it) }
            textTitle.text = cartData.title
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}