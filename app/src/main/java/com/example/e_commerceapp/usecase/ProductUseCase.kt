package com.example.e_commerceapp.usecase

import com.example.e_commerceapp.models.uimodels.product.ProductResponseUiData

interface ProductUseCase {
    suspend fun getProducts(): ProductResponseUiData
}