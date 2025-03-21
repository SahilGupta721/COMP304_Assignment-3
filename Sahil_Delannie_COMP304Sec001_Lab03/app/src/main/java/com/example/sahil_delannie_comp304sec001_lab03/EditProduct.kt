package com.example.sahil_delannie_comp304sec001_lab03

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditProduct(
    navController: NavController,
    id: Int,
    onUpdateProduct: (Product_data) -> Unit,
    productList: List<Product_data>
) {
    // Find the product based on the ID
    val product = productList.find { it.id == id }

    if (product == null) {
        // Handle the case where the product isn't found in the list
        Text("Product not found!")
        return
    }

    // State variables for the name and price
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Edit Product", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = name.isEmpty()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Product Price") },
            modifier = Modifier.fillMaxWidth(),
            isError = price.isEmpty() || price.toFloatOrNull() == null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && price.isNotEmpty() && price.toFloatOrNull() != null) {
                    val updatedProduct = product.copy(
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0
                    )
                    onUpdateProduct(updatedProduct)
                    navController.popBackStack()
                    Toast.makeText(navController.context, "Product updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(navController.context, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        {
            Text("Save Changes")
        }
    }
}
