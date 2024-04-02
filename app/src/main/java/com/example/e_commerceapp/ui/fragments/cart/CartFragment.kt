package com.example.e_commerceapp.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapter.cart.CartAdapter
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.util.goneIf
import com.example.e_commerceapp.util.navigateSafe
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
        setUpAdapter()
        getProducts()
        if (viewModel.isLoggedIn()) {
            progressBarUtil.hideProgressBar()
            checkUserLoginStatus(true)
        } else {
            checkUserLoginStatus(false)
            animationBasket()
        }
        setUpAppBar()
    }


    override fun setUpListeners() {
        binding?.buttonLogin?.setOnClickListener {
            navigateSafe(R.id.action_cartFragment_to_loginFragment)
        }
    }

    override fun setUpObservers() {}

    private fun setUpAdapter() {
        cartAdapter = CartAdapter(mContext, cartItems)
        binding?.recyclerViewCarts?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
        cartAdapter.notifyDataSetChanged()
    }

    private fun getProducts() {
        val userId = Firebase.auth.currentUser?.uid
        val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")

        progressBarUtil.showProgressBar()

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ProductResponseDataItem::class.java)
                    if (item != null) {
                        cartItems.add(item)
                    }
                }
                cartAdapter.notifyDataSetChanged()

                val totalPrice = cartAdapter.calculateTotalPrice()
                binding?.textViewTotalPrice?.text = String.format("$%.2f", totalPrice)

                progressBarUtil.hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

    private fun checkUserLoginStatus(isLogin: Boolean) {
        binding?.apply {
            imageBasket goneIf isLogin
            textViewCart goneIf isLogin
            textViewCartDescription goneIf isLogin
            buttonLogin goneIf isLogin
            buttonBuy visibleIf isLogin
            recyclerViewCarts visibleIf isLogin
            line visibleIf isLogin
            textViewTotal visibleIf isLogin
            textViewTotalPrice visibleIf isLogin
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