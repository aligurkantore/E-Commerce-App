package com.example.e_commerceapp.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.adapters.cart.CartAdapter
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.helper.FireBaseDataManager
import com.example.e_commerceapp.util.Constants
import com.example.e_commerceapp.util.Constants.DETAIL
import com.example.e_commerceapp.util.convertAndFormatCurrency
import com.example.e_commerceapp.util.getCurrencySymbols
import com.example.e_commerceapp.util.gone
import com.example.e_commerceapp.util.goneIf
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.navigateSafeWithArgs
import com.example.e_commerceapp.util.visibleIf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private lateinit var cartAdapter: CartAdapter
    private val cartItems: MutableList<ProductResponseDataItem> = mutableListOf()
    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }


    override val viewModelClass: Class<out CartViewModel>
        get() = CartViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isLoggedIn()) {
            checkUserLoginStatus(true)
            getProducts()
            setUpAdapter()
            if (cartItems.isEmpty()) binding?.cardViewAreaBottom?.gone()
        } else {
            checkUserLoginStatus(false)
            animationBasket()
        }
        setUpAppBar()
    }


    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_loginFragment)
            }

            buttonBuy.setOnClickListener {
                FireBaseDataManager.removeAllFromCart(mContext)
                navigateSafe(R.id.action_cartFragment_to_orderFragment)
            }
        }
    }

    override fun setUpObservers() {}

    private fun setUpAdapter() {
        cartAdapter = CartAdapter(
            mContext,
            cartItems,
            ::onClickAdapterItem,
            object : CartAdapter.TotalPriceListener {
                override fun onTotalPriceUpdated(totalPrice: String, count: Int) {
                    binding?.textViewTotalPrice?.text = totalPrice
                }
            })
        binding?.recyclerViewCarts?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun getProducts() {
        val userId = Firebase.auth.currentUser?.uid
        val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")

        progressBarUtil.showProgressBar()

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<ProductResponseDataItem> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ProductResponseDataItem::class.java)
                    if (item != null) {
                        tempList.add(item)
                    }
                }
                cartItems.clear()
                cartItems.addAll(tempList)
                updateTotalPrice()
                checkItemsInAdapter()
                progressBarUtil.hideProgressBar()
                cartAdapter.notifyDataSetChanged()

            }



            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

    private fun updateTotalPrice() {
        val currency = BaseShared.getString(mContext, Constants.CURRENCY, Constants.USD)
        val currencySymbols = mContext.getCurrencySymbols()
        val totalPrice = cartItems.sumByDouble {
            val count = BaseShared.getInt(mContext, "count_${it.id}", 1)
            (it.price)?.times(count) ?: 0.0
        }
        val newTotalPrice = totalPrice.convertAndFormatCurrency(Constants.USD, currency ?: Constants.USD, currencySymbols)
        binding?.textViewTotalPrice?.text = newTotalPrice
    }

    private fun checkUserLoginStatus(isLogin: Boolean) {
        binding?.apply {
            imageBasket goneIf isLogin
            textViewCart goneIf isLogin
            textViewCartDescription goneIf isLogin
            buttonLogin goneIf isLogin
            recyclerViewCarts visibleIf isLogin
            cardViewAreaBottom visibleIf isLogin
        }
    }

    private fun onClickAdapterItem(data: ProductResponseDataItem) {
        val bundle = Bundle().apply {
            putSerializable(DETAIL, data.id)
        }
        navigateSafeWithArgs(R.id.action_cartFragment_to_detailFragment, bundle)
    }

    private fun checkItemsInAdapter(){
        val isCartEmpty = cartItems.isEmpty()
        binding?.apply {
            cardViewAreaBottom.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            textViewEmptyMessage.visibility = if (isCartEmpty) View.VISIBLE else View.GONE
            buttonNavigateToDashboard.visibility =
                if (isCartEmpty) View.VISIBLE else View.GONE
            buttonNavigateToDashboard.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_dashBoardFragment)
            }
        }
    }

    private fun animationBasket() {
        progressBarUtil.hideProgressBar()
        val slideAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in)
        binding?.imageBasket?.startAnimation(slideAnimation)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
        }
    }

    private fun setUpAppBar() {
        AppUtils.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.my_cart))
    }

}