package com.example.e_commerceapp.util

import android.content.Context
import com.example.e_commerceapp.R
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FireBaseDataManager {

    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userId = auth.currentUser?.uid
    private val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")

    fun addToCart(context: Context, data: ProductResponseDataItem) {
        val productId = generateUniqueId()

        val productRef = userRef.child(productId)
        productRef.setValue(data)
            .addOnSuccessListener {
                showMessage(context, context.getString(R.string.product_added_to_cart))
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.product_already_in_cart))
            }
    }

    private fun generateUniqueId(): String {
        return userRef.push().key ?: ""
    }

    fun removeFromCart(context: Context,data: MutableList<ProductResponseDataItem>) {
        val deneme = data.first().id
        val productRef = userRef.child(deneme.toString())

        productRef.removeValue()
            .addOnSuccessListener {
                showMessage(context, "Silme başarılı")
            }
            .addOnFailureListener {
                showMessage(context, "silme başarısız")
            }
    }
}
