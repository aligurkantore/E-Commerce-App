package com.example.e_commerceapp.usecase

import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.models.datamodels.product.ProductResponseData
import com.example.e_commerceapp.models.datamodels.product.ProductResponseDataItem
import javax.inject.Inject
/*
class ProductUseCase @Inject constructor(private val repo: BaseRepository) {
    suspend fun getProducts(): ProductResponseData {
        val response = repo.getProducts().body()
        return response?.map {
            ProductResponseData(
                id = it.id.toString(),
                category = it.category,
                description = it.description,
                image = it.image,
                title = it.title,
                rating = it.rating
            )
        }
    }
}

 */


