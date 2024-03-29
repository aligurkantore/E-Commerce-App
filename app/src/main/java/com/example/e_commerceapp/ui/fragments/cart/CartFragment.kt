package com.example.e_commerceapp.ui.fragments.cart

import android.os.Bundle
import android.util.Log
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
import com.example.e_commerceapp.util.observeNonNull
import com.example.e_commerceapp.util.visibleIf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private lateinit var cartAdapter: CartAdapter
    private val cartItems: MutableList<ProductResponseDataItem> = mutableListOf()

    override val viewModelClass: Class<out CartViewModel>
        get() = CartViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getProducts()
        setUpAppBar()
        animationBasket()
    }


    override fun setUpListeners() {
        checkUserLoginStatus(viewModel.isLoggedIn())

        binding?.buttonLogin?.setOnClickListener {
            navigateSafe(R.id.action_cartFragment_to_loginFragment)
        }
    }

    override fun setUpObservers() {
        viewModel.productLiveData.observeNonNull(viewLifecycleOwner) {
            //
        }
    }

    private fun setUpAdapter() {
        cartAdapter = CartAdapter(cartItems)
        binding?.recyclerViewCarts?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun getProducts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("users/$userId/cart")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val productId = itemSnapshot.getValue(String::class.java)
                    Log.d("agt", "onDataChange: $productId")
                    Log.d("agt", "onDataChange: ${productId == "1"}")

                }
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