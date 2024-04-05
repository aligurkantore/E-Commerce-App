package com.example.e_commerceapp.ui.adapters.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.ItemCartsBinding
import com.example.e_commerceapp.util.Constants
import com.example.e_commerceapp.util.Constants.CURRENCY
import com.example.e_commerceapp.helper.FireBaseDataManager
import com.example.e_commerceapp.util.convertAndFormatCurrency
import com.example.e_commerceapp.util.getCurrencySymbols
import com.example.e_commerceapp.util.loadImage

class CartAdapter(
    private var context: Context,
    private var cartList: MutableList<ProductResponseDataItem>,
    private var navigateToDetail: (ProductResponseDataItem) -> Unit,
    private var listener: TotalPriceListener
) : RecyclerView.Adapter<CartAdapter.CartVH>() {

    val currency = BaseShared.getString(context, CURRENCY, Constants.USD)
    private val currencySymbols = context.getCurrencySymbols()

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
            textPrice.text = cartData.price.toString()

            val convertedPrice = cartData.price?.convertAndFormatCurrency(
                Constants.USD,
                currency ?: Constants.USD,
                currencySymbols
            )
            textPrice.text = convertedPrice

            imagePlus.setOnClickListener {
                cartData.count++
                textCount.text = cartData.count.toString()
                listener.onTotalPriceUpdated(calculateTotalPrice())
            }
            imageMinus.setOnClickListener {
                if (cartData.count > 0) {
                    cartData.count--
                    textCount.text = cartData.count.toString()
                    if (cartData.count == 0) {
                        val productId = cartData.id
                        FireBaseDataManager.removeFromCart(context, productId.toString())
                        cartList.remove(cartData)
                        notifyItemRemoved(position)
                    }
                }
                listener.onTotalPriceUpdated(calculateTotalPrice())
            }
            constraintCart.setOnClickListener { navigateToDetail.invoke(cartData) }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    fun calculateTotalPrice(): String {
        val totalPrice = cartList.sumByDouble { (it.price ?: 0.0) * it.count }
        Log.d("agt", "calculateTotalPrice: $totalPrice")
        Log.d("agt", "calculateTotalCount: ${cartList.sumByDouble { it.count.toDouble() }}")
        return totalPrice.convertAndFormatCurrency(
            Constants.USD,
            currency ?: Constants.USD,
            currencySymbols
        )
    }

    interface TotalPriceListener {
        fun onTotalPriceUpdated(totalPrice: String)
    }
}