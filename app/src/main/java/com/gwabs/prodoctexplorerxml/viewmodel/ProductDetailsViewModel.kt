
package com.gwabs.prodoctexplorerxml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwabs.prodoctexplorerxml.data.model.Product
import com.gwabs.prodoctexplorerxml.data.repository.ProductRepository
import com.gwabs.prodoctexplorerxml.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository // Injected dependency
) : ViewModel() {

    private val _product = MutableLiveData<Resource<Product>>()
    val product: LiveData<Resource<Product>> get() = _product

    fun getProductById(id: Int) {
        viewModelScope.launch {
            _product.postValue(Resource.Loading())  // Set loading state
            try {
                val product = productRepository.getProductById(id).first() // Fetch product from DB
                _product.postValue(Resource.Success(product))  // Set success state
            } catch (e: Exception) {
                _product.postValue(Resource.Error("Error fetching product details"))  // Set error state
            }
        }
    }
}

