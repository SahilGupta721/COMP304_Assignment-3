package com.example.sahil_delannie_comp304sec001_lab03

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
fun Home(navController: NavController) {
//    var productList by remember { mutableStateOf(listOf<Product_data>()) }
//
//    // Define the delete functionality
//    val onDeleteProduct: (Int) -> Unit = { id ->
//        productList = productList.filterNot { it.id == id }
//    }
//
//    // Define the update functionality
//    val onUpdateProduct: (Product_data) -> Unit = { product ->
//        // Your logic for updating the product can go here
//        // For now, just print the product to be updated
//        println("Updating product: ${product.name}")
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Manage Buddy",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            ),
            modifier = Modifier
                .padding(top = 64.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("addProduct") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Get Started")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Passing the product list and functions to ProductList composable
//        ProductList(
//            navController = navController,
//            productList = productList,  // Dynamic list of products
//            onDeleteProduct = { id ->
//                // Handle delete action (remove product from the list)
//                productList = productList.filterNot { it.id == id }
//            },
//            onUpdateProduct = { product ->
//                // Handle update action
//                println("Update product: ${product.name}")
//            }
//        )
    }
}
