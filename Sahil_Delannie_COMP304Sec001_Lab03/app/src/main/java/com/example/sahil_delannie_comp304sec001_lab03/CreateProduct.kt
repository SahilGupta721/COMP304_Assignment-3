package com.example.sahil_delannie_comp304sec001_lab03


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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 64.dp), // Aquí agregamos el margen superior para todo el contenido
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Product", fontSize = 26.sp, modifier = Modifier.padding(8.dp))

        // ID
        TextField(
            value = id,
            onValueChange = { newValue ->
                if (newValue.length <= 3 && newValue.all { it.isDigit() }) {
                    id = newValue
                }
            },
            label = { Text("ID (101-999)") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp)) // Espaciado entre los campos

        // Name
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp)) // Espaciado entre los campos

        // Price
        TextField(
            value = price,
            onValueChange = {
                if (it.toDoubleOrNull() != null && it.toDouble() > 0) price = it
            },
            label = { Text("Price (Positive Only)") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp)) // Espaciado entre los campos

        // Date of Delivery
        TextField(
            value = deliveryDate,
            onValueChange = { value ->
                val dateFormat = DateTimeFormatter.ISO_LOCAL_DATE
                val parsedDate = try {
                    LocalDate.parse(value.text, dateFormat)
                    value
                } catch (e: Exception) {
                    TextFieldValue(LocalDate.now().toString()) // Default to current date if invalid
                }
                deliveryDate = parsedDate
            },
            label = { Text("Date of Delivery (No earlier than today)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp)) // Espaciado entre los campos

        // Category Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
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

        Spacer(modifier = Modifier.height(12.dp)) // Espaciado entre los campos

        // Favorite toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Favorite", modifier = Modifier.padding(end = 8.dp))
            Switch(checked = isFavorite, onCheckedChange = { isFavorite = it })
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado antes del botón

        // Add Product Button
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
                onAddProduct(product)
                navController.navigate("home") // 👈 Navegar de regreso a Home después de agregar
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Product")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre el botón y el botón de regreso

        // Back Button
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Back to Home")
        }
    }
}
