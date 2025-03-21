package com.example.sahil_delannie_comp304sec001_lab03

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProductList(
    navController: NavController,
    productList: List<Product_data>,
    onDeleteProduct: (Int) -> Unit,
    onUpdateProduct: (Product_data) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (productList.isEmpty()) {
            Text(
                text = "No products available.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            productList.forEach { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = product.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Price: \$${"%.2f".format(product.price)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = "Category: \$${product.category}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        Row {
                            // Edit Button
                            IconButton(onClick = {
                                navController.navigate("editProduct/${product.id}") // Navigate to Edit screen
                            }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Product")
                            }
                            // Delete Button
                            IconButton(onClick = {
                                onDeleteProduct(product.id)
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Product")
                            }
                        }
                    }
                }
            }
        }

        // Add Product Button
        Button(
            onClick = { navController.navigate("addProduct") },//once clicked it will creat product epage
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Add Product", fontSize = 18.sp)
        }
    }
}
