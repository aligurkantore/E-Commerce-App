package com.example.e_commerceapp.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.e_commerceapp.R
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FireBaseDataManager {

    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun addToCart(context: Context, data: ProductResponseDataItem){
        val userId = auth.currentUser?.uid
        val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")


        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val existingList = snapshot.value as? ArrayList<String> ?: arrayListOf()
                if (existingList.any { it == data.id.toString() }) {
                    Toast.makeText(context, R.string.product_already_in_cart, Toast.LENGTH_SHORT).show()
                } else {
                    existingList.add(data.id.toString())
                    userRef.setValue(existingList)
                    Toast.makeText(context, R.string.product_added_to_cart, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

}