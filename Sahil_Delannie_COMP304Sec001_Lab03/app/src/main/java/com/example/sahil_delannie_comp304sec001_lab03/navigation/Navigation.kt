package com.example.sahil_delannie_comp304sec001_lab03.navigation

import AddProductScreen
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sahil_delannie_comp304sec001_lab03.AboutUs
import com.example.sahil_delannie_comp304sec001_lab03.EditProduct
import com.example.sahil_delannie_comp304sec001_lab03.MainScreen
import com.example.sahil_delannie_comp304sec001_lab03.ProductList
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModel

@Composable
fun NavigationGraph(navController: NavHostController, productViewModel: ProductViewModel) {
    NavHost(navController = navController, startDestination = "main") { // Cambiar a "main"

        composable("main") {
            MainScreen(navController = navController)
        }

        composable("products_list") {
            ProductList(
                navController = navController,
                productViewModel = productViewModel
            )
        }

        composable("addProduct") {
            AddProductScreen(
                navController = navController,
                productViewModel = productViewModel
            )
        }

        composable("editProduct/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()

            productId?.let {
                val productState = productViewModel.getProductById(it).collectAsState(initial = null).value

                if (productState != null) {
                    EditProduct(
                        navController = navController,
                        productId = it,
                        productViewModel = productViewModel,
                        onUpdateProduct = { updatedProduct ->
                            productViewModel.updateProduct(updatedProduct)

                            // Mover el toast dentro de EditProduct, no en NavigationGraph
                            navController.popBackStack("editProduct/{productId}", inclusive = true)
                            navController.navigate("products_list")
                        }
                    )
                } else {
                    Text("Loading product...")
                }

            }
        }
        composable("aboutus") {
            AboutUs(
                navController = navController,
            )
        }
    }
}
