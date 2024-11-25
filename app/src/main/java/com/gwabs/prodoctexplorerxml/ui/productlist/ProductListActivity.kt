package com.gwabs.prodoctexplorerxml.ui.productlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gwabs.prodoctexplorerxml.R
import com.gwabs.prodoctexplorerxml.ui.productdetails.ProductDetailsActivity
import com.gwabs.prodoctexplorerxml.utils.Resource
import com.gwabs.prodoctexplorerxml.viewmodel.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var loadingIndicator: ProgressBar // Reference to the ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        toolbar.setBackgroundColor(getColor(R.color.white)) // Optional for runtime clarity
        toolbar.setTitleTextColor(getColor(R.color.black))

        toolbar.title = getString(R.string.product_list)
        // Set the back arrow icon
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Add back button if needed

        // Initialize Views
        loadingIndicator = findViewById(R.id.progressBar)
        productRecyclerView = findViewById(R.id.productListRecyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Adapter
        productAdapter = ProductAdapter(emptyList()) { product ->
            val intent = Intent(this, ProductDetailsActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            startActivity(intent)
        }
        productRecyclerView.adapter = productAdapter

        // Observe Product Data
        viewModel.products.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> showLoading(true) // Show ProgressBar
                is Resource.Success -> {
                    showLoading(false) // Hide ProgressBar
                    resource.data?.let { productAdapter.updateData(it) }
                }
                is Resource.Error -> {
                    showLoading(false) // Hide ProgressBar
                    showError(resource.message ?: "An unknown error occurred.")
                }
            }
        }

        // Fetch Products
        viewModel.getProducts()
    }

    private fun showLoading(isLoading: Boolean) {
        // Toggle visibility of the ProgressBar
        loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        // Show error message using Snackbar
        Snackbar.make(findViewById(R.id.productListRecyclerView), message, Snackbar.LENGTH_LONG).show()
    }
}
