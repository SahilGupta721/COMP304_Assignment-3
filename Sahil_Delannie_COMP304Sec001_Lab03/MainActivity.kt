package com.example.sahil_delannie_comp304sec001_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sahil_delannie_comp304sec001_lab03.ui.theme.Sahil_Delannie_COMP304Sec001_Lab03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sahil_Delannie_COMP304Sec001_Lab03Theme {
                DisplayScreen()
            }
        }
    }
}

@Composable
fun DisplayScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display Header
        Home().DisplayHeader()

        // Space between head and form
        Spacer(modifier = Modifier.height(16.dp))

        // Display Product Form
        CreateProduct().AddProduct(onAddProduct = { product ->
            println("Product added: $product") // Placeholder action
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayContentPreview() {
    Sahil_Delannie_COMP304Sec001_Lab03Theme {
        DisplayScreen()
    }
}
