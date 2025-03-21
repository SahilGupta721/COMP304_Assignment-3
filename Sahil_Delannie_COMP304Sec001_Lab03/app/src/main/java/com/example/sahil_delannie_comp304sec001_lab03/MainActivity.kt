package com.example.sahil_delannie_comp304sec001_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.*
import com.example.sahil_delannie_comp304sec001_lab03.ui.theme.Sahil_Delannie_COMP304Sec001_Lab03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sahil_Delannie_COMP304Sec001_Lab03Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { Home(navController) }
                    composable("addProduct") {
                        CreateProduct(navController) { product ->
                            println("Product added: $product") // Action when a product is added
                        }
                    }
                }
            }
        }
    }
}

