package com.example.sahil_delannie_comp304sec001_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.sahil_delannie_comp304sec001_lab03.ui.theme.Sahil_Delannie_COMP304Sec001_Lab03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Ensuring edge-to-edge display

        setContent {
            Sahil_Delannie_COMP304Sec001_Lab03Theme {
                val navController = rememberNavController()

                // State to hold the list of products
                var productList by remember { mutableStateOf(listOf<Product_data>()) }
                var showSnackbar by remember { mutableStateOf(false) }
                val snackbarHostState = remember { SnackbarHostState() }

                // Navigation Host
                NavHost(navController = navController, startDestination = "home") {
                    // Home screen
                    composable("home") {
                        Home(navController)
                    }

                    // Add Product screen
                    composable("addProduct") {
                        CreateProduct(navController) { product ->
                            // Add the new product to the product list
                            productList = productList + product
                            showSnackbar = true

                            // After adding the product, navigate to productList screen
                            navController.navigate("productList")
                        }
                    }

                    // Product List screen
                    composable("productList") {
                        // Display the product list
                        ProductList(
                            navController = navController,
                            productList = productList,  // Dynamic list
                            onDeleteProduct = { id ->
                                // Handle delete action (remove product from the list)
                                productList = productList.filterNot { it.id == id }
                            },

                            onUpdateProduct = { updatedProduct ->
                                // Handle update action (update product in the list)
                                productList = productList.map {
                                    if (it.id == updatedProduct.id) updatedProduct else it
                                }
                            }
                        )
                    }

                    // Edit Product screen with dynamic ID
                    composable("editProduct/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toInt() ?: return@composable
                        EditProduct(
                            navController = navController,
                            id = id, // Pass the ID to the EditProduct screen
                            onUpdateProduct = { updatedProduct ->
                                // Handle update action (update product in the list)
                                productList = productList.map {
                                    if (it.id == updatedProduct.id) updatedProduct else it
                                }
                            },
                            productList = productList
                        )
                    }
                }


                //below code is repsonsible to generate a black pop up saying item is created
                if (showSnackbar) {
                    LaunchedEffect(showSnackbar) {
                        snackbarHostState.showSnackbar("Product added successfully!")
                        showSnackbar = false // Hide snackbar after it's shown
                    }
                }

                // Snackbar Host
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}
