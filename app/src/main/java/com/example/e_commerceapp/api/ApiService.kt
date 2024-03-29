package com.example.e_commerceapp.api

import com.example.e_commerceapp.models.datamodels.product.Category
import com.example.e_commerceapp.models.datamodels.product.ProductResponseData
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 0
    ) : Response<ProductResponseData>

    @GET("products/{id}")
    suspend fun getSingleProduct(
        @Path("id") id: String
    ): Response<ProductResponseDataItem>

    @GET("products/categories")
    suspend fun getCategories(
    ): Response<Category>

}