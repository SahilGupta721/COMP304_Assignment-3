package com.example.sahil_delannie_comp304sec001_lab03

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductEntity
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModel

@Composable
fun ProductList(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    var showFavorites by remember { mutableStateOf(false) }
    val productList by productViewModel.productList.collectAsState()

    val filteredList = if (showFavorites) {
        productList.filter { it.isFavorite }
    } else {
        productList
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background video
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/about") // Replace with your video resource
                    setVideoURI(videoUri)
                    setZOrderOnTop(false) // Set the video layer behind the text
                    setOnPreparedListener { mp -> mp.isLooping = true } // Loop the video
                    start()
                }
            },
            modifier = Modifier
                .fillMaxHeight() // Make the video fill the entire screen
        )

        // Foreground UI components
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                 // Slightly transparent background to improve text visibility
        ) {
            // Title of the product list
            Text(
                text = "List of Products",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp // Adjusted font size
                ),
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Button to toggle between all products and favorite products
            Button(
                onClick = { showFavorites = !showFavorites },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) {
                Text(
                    text = if (showFavorites) "Show All Products" else "Show Favorite Products",
                    fontSize = 18.sp,
                            color = Color.Black, // Ensure text color is black for better readability
                    fontWeight = FontWeight.Bold
                // Adjusted font size for button text
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredList, key = { product -> product.id }) { product ->
                        ProductCard(product) {
                            navController.navigate("editProduct/${product.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: ProductEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Product name text
                Text(
                    text = product.name,
                    fontSize = 20.sp, // Adjusted font size for product name
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Price text
                Text(
                    text = "Price: \$${"%.2f".format(product.price)}",
                    fontSize = 16.sp, // Adjusted font size for price
                    fontWeight = FontWeight.Normal
                )

                // Category text
                Text(
                    text = "Category: ${product.category}",
                    fontSize = 16.sp, // Adjusted font size for category
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
