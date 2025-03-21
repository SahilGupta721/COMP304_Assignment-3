package com.example.sahil_delannie_comp304sec001_lab03

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(
    navController: NavController,
    id: Int,
    onUpdateProduct: (Product_data) -> Unit,
    productList: List<Product_data>
) {
    val context = LocalContext.current

    val product = productList.find { it.id == id }

    if (product == null) {
        Text("Product not found!")
        return
    }

    // Get current date in "yyyy-MM-dd" format
    val currentDate = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    // State variables for editable fields
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var category by remember { mutableStateOf(product.category) }
    var isFavorite by remember { mutableStateOf(product.isFavorite) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp) // Added top margin
    ) {
        item {
            Text(
                text = "Edit Product",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        item {
            // Non-editable ID field
            OutlinedTextField(
                value = product.id.toString(),
                onValueChange = {},
                label = { Text("Product ID (Read-Only)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
        }

        item {
            // Editable Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isEmpty()
            )
        }

        item {
            // Editable Price
            OutlinedTextField(
                value = price,
                onValueChange = {
                    if (it.toDoubleOrNull() != null && it.toDouble() > 0) price = it
                },
                label = { Text("Product Price") },
                modifier = Modifier.fillMaxWidth(),
                isError = price.isEmpty() || price.toDoubleOrNull() == null
            )
        }

        item {
            // Non-editable Delivery Date (always set to current date)
            OutlinedTextField(
                value = currentDate,
                onValueChange = {},
                label = { Text("Delivery Date (Read-Only)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
        }

        item {
            // Editable Category
            var expanded by remember { mutableStateOf(false) }
            val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                category = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            // Editable Favorite toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Favorite", modifier = Modifier.padding(end = 8.dp))
                Switch(checked = isFavorite, onCheckedChange = { isFavorite = it })
            }
        }

        item {
            // Save Changes Button
            Button(
                onClick = {
                    if (name.isNotEmpty() && price.isNotEmpty() && price.toDoubleOrNull() != null) {
                        val updatedProduct = product.copy(
                            name = name,
                            price = price.toDouble(),
                            deliveryDate = currentDate, // Set to current date
                            category = category,
                            isFavorite = isFavorite
                        )
                        onUpdateProduct(updatedProduct)
                        navController.popBackStack()
                        Toast.makeText(context, "Product updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Save Changes")
            }
        }
    }
}
