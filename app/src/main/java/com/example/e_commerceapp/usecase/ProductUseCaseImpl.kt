package com.example.e_commerceapp.usecase

/*
class ProductUseCaseImpl(private val repo: BaseRepository) : ProductUseCase {
    override suspend fun getProducts(): ProductResponseUiData {
        val productList = repo.getProducts()
        return productList.body()?.map { productData ->
            ProductResponseUiData(

            )
        } ?: emptyList()
    }
}


/*
id = productData.id.toString(),
                title = productData.title,
                price = productData.price,
                description = productData.description,
                image = productData.image,
                ratingCount = productData.rating?.count.toString(),
                rating = productData.rating?.rate?.toFloat()
 */