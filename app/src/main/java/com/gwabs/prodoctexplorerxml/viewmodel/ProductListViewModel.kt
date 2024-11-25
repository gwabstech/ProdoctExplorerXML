package com.gwabs.prodoctexplorerxml.viewmodel

import kotlinx.coroutines.flow.first


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwabs.prodoctexplorerxml.data.model.Product
import com.gwabs.prodoctexplorerxml.data.repository.ProductRepository
import com.gwabs.prodoctexplorerxml.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository // Injected dependency
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> get() = _products

    fun getProducts() {
        viewModelScope.launch {
            _products.postValue(Resource.Loading())  // Loading state
            try {
                productRepository.getProductsFromApi() // Fetch products from the API
                val products = productRepository.getProductsFromDb().first() // Load from database
                _products.postValue(Resource.Success(products))  // Success state
            } catch (e: Exception) {
                _products.postValue(Resource.Error("Error fetching products")) // Error state
                val products = productRepository.getProductsFromDb().first() // Load from database
                _products.postValue(Resource.Success(products))
            }
        }
    }
}

