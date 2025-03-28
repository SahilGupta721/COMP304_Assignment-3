package com.example.sahil_delannie_comp304sec001_lab03

import android.net.Uri
import android.widget.Toast
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductEntity
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModel
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(
    navController: NavController,
    productId: Int,
    productViewModel: ProductViewModel,
    onUpdateProduct: (ProductEntity) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var product by remember { mutableStateOf<ProductEntity?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        productViewModel.getProductById(productId).collect { fetchedProduct ->
            product = fetchedProduct
        }
    }

    if (product == null) {
        Text("Loading product...")
        return
    }

    var name by remember { mutableStateOf(TextFieldValue(product!!.name)) }
    var price by remember { mutableStateOf(TextFieldValue(product!!.price.toString())) }
    var category by remember { mutableStateOf(product!!.category) }
    var isFavorite by remember { mutableStateOf(product!!.isFavorite) }

    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Add Background Video
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/about")
                    setVideoURI(videoUri)
                    setZOrderOnTop(false)
                    setOnPreparedListener { mp -> mp.isLooping = true }
                    start()
                }
            },
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Edit Product",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 28.sp
                ),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(bottom = 24.dp)
            )

            // Product Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text="Product Name", fontSize = 18.sp,fontWeight = FontWeight.Bold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),

                )

            // Price Field
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(text="Price", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category", fontSize = 18.sp) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(bottom = 12.dp)
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

            // Checkbox for Favorite
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it }
                )
                Text("Mark as Favorite", fontSize = 18.sp)
            }
            val formattedDate = product!!.deliveryDate.toString() // Suponiendo que 'date' es una propiedad de tipo Date o String
            TextField(
                value = formattedDate,
                onValueChange = {},
                label = { Text("Date Added") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp) // Espacio entre el campo de fecha y otros campos
            )

            // Button to Save Changes
            Button(
                onClick = {
                    if (name.text.isNotEmpty() && price.text.isNotEmpty() && price.text.toDoubleOrNull() != null) {
                        val updatedProduct = product!!.copy(
                            name = name.text,
                            price = price.text.toDouble(),
                            category = category,
                            isFavorite = isFavorite
                        )

                        coroutineScope.launch {
                            onUpdateProduct(updatedProduct)
                        }

                        navController.popBackStack("editProduct/{productId}", inclusive = true)
                        navController.navigate("products_list")
                        Toast.makeText(context, "Product updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) {
                Text("Save Changes", color = Color.Black, fontSize = 18.sp)
            }

            // Delete Product Button
            Button(
                onClick = {
                    showDeleteConfirmation = true
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.7f))
            ) {
                Text("Delete Product", color = Color.Black)
            }

            // Confirmation Dialog for Deletion
            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete this product?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    productViewModel.deleteProduct(product!!)
                                }

                                navController.popBackStack("editProduct/{productId}", inclusive = true)
                                navController.navigate("products_list")
                                Toast.makeText(context, "Product deleted successfully!", Toast.LENGTH_SHORT).show()
                                showDeleteConfirmation = false
                            }
                        ) {
                            Text("Yes", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmation = false }) {
                            Text("No")
                        }
                    }
                )
            }
            }
        }
}