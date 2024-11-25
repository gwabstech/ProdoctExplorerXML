package com.gwabs.prodoctexplorerxml.data.api

import com.gwabs.prodoctexplorerxml.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}


