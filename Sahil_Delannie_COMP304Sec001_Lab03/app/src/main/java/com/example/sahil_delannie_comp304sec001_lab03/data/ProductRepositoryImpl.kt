package com.example.sahil_delannie_comp304sec001_lab03.data

import kotlinx.coroutines.flow.Flow


class ProductRepositoryImpl(private val productDao: ProductDao)  {
    suspend fun insertProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getProductById(productId: Int): Flow<ProductEntity?> {
        return productDao.getProductById(productId)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

}

