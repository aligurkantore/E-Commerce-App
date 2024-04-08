package com.example.e_commerceapp.base

import com.example.e_commerceapp.network.ApiService
import com.example.e_commerceapp.models.datamodels.product.Category
import com.example.e_commerceapp.models.datamodels.product.ProductResponseData
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import retrofit2.Response
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts(): Response<ProductResponseData>{
        return apiService.getProducts()
    }

    suspend fun getCategories(): Response<Category>{
        return apiService.getCategories()
    }

    suspend fun getSingleProduct(productId: String): Response<ProductResponseDataItem>{
        return apiService.getSingleProduct(productId)
    }

}