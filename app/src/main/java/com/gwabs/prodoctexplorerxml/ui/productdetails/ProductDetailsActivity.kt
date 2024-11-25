package com.gwabs.prodoctexplorerxml.ui.productdetails

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gwabs.prodoctexplorerxml.R
import com.gwabs.prodoctexplorerxml.viewmodel.ProductDetailsViewModel
import com.gwabs.prodoctexplorerxml.utils.Resource
import com.gwabs.prodoctexplorerxml.data.model.Product
import com.google.android.material.snackbar.Snackbar
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar) // Use Toolbar as ActionBar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Enable the back button
            setHomeAsUpIndicator(R.drawable.ic_back_arrow) // Optional custom back arrow icon
            title = getString(R.string.product_details) // Set the title
        }

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        toolbar.setTitleTextColor(getColor(R.color.black)) // Ensure title is visible

        // Handle the back arrow functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Go back to the previous activity
        }

        val productId = intent.getIntExtra("PRODUCT_ID", -1)

        // Observe product details from the ViewModel
        viewModel.product.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE // Show progress bar
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE // Hide progress bar
                    resource.data?.let {
                        product = it
                        displayProductDetails()
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE // Hide progress bar
                    showError(resource.message ?: "Error fetching product details")
                }
            }
        }

        // Fetch the product details using the ViewModel
        if (productId != -1) {
            viewModel.getProductById(productId)
        }
    }

    private fun displayProductDetails() {
        // Display product details in the UI
        val productTitle: TextView = findViewById(R.id.productTitle)
        val productPrice: TextView = findViewById(R.id.productPrice)
        val productCategory: TextView = findViewById(R.id.productCategory)
        val productDescription: TextView = findViewById(R.id.productDescription)
        val productImage: ImageView = findViewById(R.id.productImage)

        productTitle.text = product.title
        productPrice.text = "₦${product.price}"
        productCategory.text = product.category
        productDescription.text = product.description

        // Load the product image using Glide
        Glide.with(this)
            .load(product.image)
            .into(productImage)
    }

    private fun showError(message: String) {
        // Show error message using Snackbar
       // Snackbar.make(findViewById(R.id.rootLayout), message, Snackbar.LENGTH_LONG).show()
    }
}
