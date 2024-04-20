package com.example.e_commerceapp.helper

import android.content.Context
import com.example.e_commerceapp.R
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.example.e_commerceapp.util.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FireBaseDataManager {

    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userId = auth.currentUser?.uid
    private val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")

    fun addToCart(context: Context, data: ProductResponseDataItem) {
        val productId = data.id

        val productRef = userRef.child(productId.toString())
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showMessage(context, context.getString(R.string.product_already_in_cart))
                } else {
                    productRef.setValue(data)
                        .addOnSuccessListener {
                            BaseShared.removeKey(context,"count_${data.id}")
                            showMessage(context, context.getString(R.string.product_added_to_cart))
                        }
                        .addOnFailureListener {
                            showMessage(context, context.getString(R.string.unexpected_error))
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }


    fun removeFromCart(context: Context, productId: String) {
        val productRef = userRef.child(productId)

        productRef.removeValue()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
            }
    }

    fun removeAllFromCart(context: Context) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children) {
                    productSnapshot.ref.removeValue()
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener {
                            showMessage(context, context.getString(R.string.unexpected_error))
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage(context, context.getString(R.string.unexpected_error))
            }
        })
    }
}
