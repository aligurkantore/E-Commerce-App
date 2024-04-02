package com.example.e_commerceapp.models.datamodels.product

import java.io.Serializable

data class ProductResponseDataItem(
    val id: Int? = null,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: Double? = null,
    val rating: Rating? = null,
    val title: String? = null,
) : Serializable {
    var count : Int = 1
}