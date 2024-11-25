package com.gwabs.prodoctexplorerxml.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwabs.prodoctexplorerxml.R
import com.gwabs.prodoctexplorerxml.data.model.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productTitle: TextView = itemView.findViewById(R.id.productTitle)
       // private val productCategory: TextView = itemView.findViewById(R.id.productCategory)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)

        fun bind(product: Product) {
            productTitle.text = product.title
           // productCategory.text = product.category
            productPrice.text = "₦${product.price}"
            // Use Glide or Picasso to load the image
            Glide.with(itemView.context)
                .load(product.image)
                .into(productImage)

            itemView.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    // Function to update the data in the adapter
    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
