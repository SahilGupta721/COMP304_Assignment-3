package com.example.sahil_delannie_comp304sec001_lab03.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductEntity
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepositoryImpl) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductEntity>>(emptyList())
    val productList: StateFlow<List<ProductEntity>> = _productList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllProducts().collect {
                _productList.value = it
            }
        }
    }

    // Función para insertar un producto
    fun insertProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    // Función para actualizar un producto
    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    // Obtener producto por ID
    // Dentro de ProductViewModel
    fun getProductById(id: Int): Flow<ProductEntity?> {
        return repository.getProductById(id)
    }

    fun deleteProduct(product: ProductEntity) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

}
