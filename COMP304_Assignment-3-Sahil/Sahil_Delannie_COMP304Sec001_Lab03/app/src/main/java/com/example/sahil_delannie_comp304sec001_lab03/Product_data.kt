package com.example.sahil_delannie_comp304sec001_lab03
//This class is responsible for creating attributes of producst that user gonna create, update etc
data class Product_data (
    val id: Int,
    val name: String,
    val price: Double,
    val deliveryDate: String,
    val category: String,
    val isFavorite: Boolean
)