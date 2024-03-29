package com.example.e_commerceapp.usecase

import com.example.e_commerceapp.base.BaseRepository
import com.example.e_commerceapp.models.uimodels.product.ProductResponseUiData
import com.example.e_commerceapp.models.uimodels.product.ProductResponseUiModel
import javax.inject.Inject

/*class ProductUseCase @Inject constructor(private val repo: BaseRepository) {
   suspend fun getProducts() :  {
        return repo.getProducts().body()?.map {
            ProductResponseUiModel(
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
