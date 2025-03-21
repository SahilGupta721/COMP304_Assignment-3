package com.example.sahil_delannie_comp304sec001_lab03

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(navController: NavController, onAddProduct: (Product_data) -> Unit) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf(TextFieldValue(LocalDate.now().toString())) }
    var category by remember { mutableStateOf("Cell Phone") }
    var isFavorite by remember { mutableStateOf(false) }

    val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")
    var expanded by remember { mutableStateOf(false) }

    val isFormValid = id.toIntOrNull()?.let { it in 101..999 } == true &&
            name.isNotEmpty() &&
            price.toDoubleOrNull()?.let { it > 0 } == true &&
            deliveryDate.text.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Product", fontSize = 26.sp, modifier = Modifier.padding(8.dp))

        // ID
        TextField(
            value = id,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {//we have to make sure that user is entering greater that 100
                    id = newValue
                }
            },
            label = { Text("ID (101-999) *") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Price
        TextField(
            value = price,
            onValueChange = {
                price = it
            },
            label = { Text("Price (Positive Only) *") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date of Delivery
        TextField(
            value = deliveryDate.text,
            onValueChange = { value ->
                val dateFormat = DateTimeFormatter.ISO_LOCAL_DATE
                val parsedDate = try {
                    val parsed = LocalDate.parse(value, dateFormat)
                    if (!parsed.isBefore(LocalDate.now())) TextFieldValue(value) else deliveryDate
                } catch (e: Exception) {
                    deliveryDate
                }
                deliveryDate = parsedDate
            },
            label = { Text("Date of Delivery (No earlier than today) *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expanded = true }
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

        Spacer(modifier = Modifier.height(12.dp))

        // Favorite toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Favorite", modifier = Modifier.padding(end = 8.dp))
            Switch(checked = isFavorite, onCheckedChange = { isFavorite = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Debugging Output
        Text("Form Valid: $isFormValid", fontSize = 18.sp) // Debugging

        // Add Product Button (Disabled if Form is Invalid)
        Button(
            onClick = {
                val product = Product_data(
                    id = id.toInt(),
                    name = name,
                    price = price.toDouble(),
                    deliveryDate = deliveryDate.text,
                    category = category,
                    isFavorite = isFavorite
                )
                onAddProduct(product) // Pass product to Home screen
                navController.navigate("productList") // Navigate to product list screen
            },
            modifier = Modifier.padding(top = 16.dp),
            enabled = isFormValid
        ) {
            Text("Add Product")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Back Button
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Back to Home")
        }
    }
}
