package com.example.sahil_delannie_comp304sec001_lab03

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDatabase
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepositoryImpl
import com.example.sahil_delannie_comp304sec001_lab03.navigation.NavigationGraph
import com.example.sahil_delannie_comp304sec001_lab03.ui.theme.Sahil_Delannie_COMP304Sec001_Lab03Theme
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModel
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Sahil_Delannie_COMP304Sec001_Lab03Theme {

                val navController = rememberNavController()
                val context = LocalContext.current


                val database = remember { ProductDatabase.getDatabase(context) }
                val productDao = remember { database.productDao() }
                val productRepository = remember { ProductRepositoryImpl(productDao) }


                val productViewModel: ProductViewModel = viewModel(
                    factory = ProductViewModelFactory(productRepository)
                )


                NavigationGraph(navController = navController, productViewModel = productViewModel)
            }
        }
    }
}



@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current

    // Box to stack the video and the content
    Box(modifier = Modifier.fillMaxHeight()) {

        // Background video using AndroidView
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    // Get URI for the video from the raw folder
                    val uri = Uri.parse("android.resource://${context.packageName}/raw/home")//path to our video
                    setVideoURI(uri)
                    setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = true // Make the video loop
                        mediaPlayer.setVolume(0f, 0f) // Optionally mute the video
                    }
                    start()
                }
            },
            modifier = Modifier.fillMaxHeight()
                .align(Alignment.Center)// Fill the entire screen
        )

        // Column to place text and button over the video

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title text
            Spacer(modifier = Modifier.height(260.dp))
            Text(
                text = "Welcome to ManageBuddy",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,  // Increase font size
                    color = Color(0xFF87CEEB)
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the text
                    .padding(bottom = 32.dp) // Add bottom padding
            )

            // Spacer between title and button
            Spacer(modifier = Modifier.height(16.dp))

            // Button to navigate to AddProductScreen
            Button(
                onClick = {
                    navController.navigate("addProduct") // Navigate to AddProductScreen
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate("aboutus")
            }, modifier = Modifier
                    ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) { Text("About Us") }
        }
    }
}
