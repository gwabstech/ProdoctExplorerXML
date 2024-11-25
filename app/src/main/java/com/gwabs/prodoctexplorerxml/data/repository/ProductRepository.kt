package com.gwabs.prodoctexplorerxml.data.repository

import com.gwabs.prodoctexplorerxml.data.api.ProductApi
import com.gwabs.prodoctexplorerxml.data.database.ProductDao
import com.gwabs.prodoctexplorerxml.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao
) {

    suspend fun getProductsFromApi() {
        val response = productApi.getProducts()
        if (response.isSuccessful) {
            response.body()?.let {
                productDao.insertAll(it)
            }
        }
    }


    fun getProductsFromDb(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    fun getProductById(id: Int): Flow<Product> {
        return productDao.getProductById(id)
    }
}
