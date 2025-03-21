package com.example.sahil_delannie_comp304sec001_lab03

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // This for center all the elements
    ) {
        // Title "Product Manager"
        Text(
            text = "Product Manager",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                fontSize = 36.sp // Hacer el texto más grande
            ),
            modifier = Modifier
                .padding(top = 64.dp) // Agregar un margen superior
                .align(Alignment.CenterHorizontally) // Centrar horizontalmente
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espaciado entre el título y el botón

        // Botón centrado horizontalmente
        Button(
            onClick = { navController.navigate("addProduct") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally) // Centrar el botón
        ) {
            Text("Add Product")
        }
    }
}
