package com.example.sahil_delannie_comp304sec001_lab03

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Welcome to TaskManager")

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {

            navController.navigate("addProduct")
        }) {
            Text(text = "Go to Create Product")
        }
    }
}
