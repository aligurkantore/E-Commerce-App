package com.example.e_commerceapp.base

import com.example.e_commerceapp.api.ApiService
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts() = apiService.getProducts()

    suspend fun getCategories() = apiService.getCategories()

    suspend fun getSingleProduct(productId: String) = apiService.getSingleProduct(productId)

}