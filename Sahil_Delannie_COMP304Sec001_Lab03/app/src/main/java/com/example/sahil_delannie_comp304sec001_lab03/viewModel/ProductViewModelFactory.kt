package com.example.sahil_delannie_comp304sec001_lab03.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepositoryImpl
import java.lang.IllegalArgumentException

class ProductViewModelFactory(private val productRepository: ProductRepositoryImpl):ViewModelProvider.Factory {
    override fun <T: ViewModel>create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Unknown")
    }
}