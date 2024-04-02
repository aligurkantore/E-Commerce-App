package com.example.e_commerceapp.models.uimodels.product

import com.example.e_commerceapp.models.datamodels.product.Rating
import java.io.Serializable

class ProductResponseUiItem(
    val id: String? = null,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: Double? = null,
    val rating: Rating? = null,
    val title: String? = null
) : Serializable