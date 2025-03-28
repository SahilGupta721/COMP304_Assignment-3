import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.sahil_delannie_comp304sec001_lab03.viewModel.ProductViewModel
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(productViewModel: ProductViewModel, navController: NavController) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Select Category") }
    var isFavorite by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")

    //DateofDelivery
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayDate = dateFormat.format(Calendar.getInstance().time)
    var deliveryDate by remember { mutableStateOf(todayDate) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Add Background Video
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
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Add a New Product",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = id,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                        id = newValue
                    }
                },
                label = { Text(text = "Product ID (101-999)", fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold), // Make input text bold
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Product Name", fontWeight = FontWeight.Bold) },
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold), // Make input text bold
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { newValue ->
                    if (newValue.toDoubleOrNull() != null || newValue.isEmpty()) {
                        price = newValue
                    }
                },
                label = { Text(text = "Price (Must be positive)", fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold), // Make input text bold
                modifier = Modifier.fillMaxWidth()
            )

            // Field of date editable
            OutlinedTextField(
                value = deliveryDate,
                onValueChange = { newDate -> deliveryDate = newDate },
                label = { Text(text = "Delivery Date (YYYY-MM-DD)", fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.ExtraBold), // Make input text bold
                modifier = Modifier.fillMaxWidth()
            )


            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Category", fontWeight = FontWeight.Bold) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold) // Make input text bold
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, fontWeight = FontWeight.Bold) }, // Make category text bold
                            onClick = {
                                category = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Favorite", fontWeight = FontWeight.Bold,fontSize = 28.sp) // Make text bold
                Switch(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it }
                )
            }

            // Submit Button
            Button(
                onClick = {
                    val parsedId = id.toIntOrNull()
                    val parsedPrice = price.toDoubleOrNull()

                    val existingProduct = productViewModel.productList.value.find { it.id == parsedId }

                    // Validation for date
                    if (deliveryDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                        val inputDate = dateFormat.parse(deliveryDate)
                        val today = dateFormat.parse(todayDate)

                        if (inputDate != null && today != null && inputDate.before(today)) {
                            errorMessage = "Cannot select past dates"
                        } else {
                            errorMessage = ""
                        }
                    } else {
                        errorMessage = "Invalid date format. Use YYYY-MM-DD"
                    }

                    if (existingProduct != null) {
                        errorMessage = "Product ID already exists. Please choose a different one."
                    } else if (parsedId in 101..999 && parsedPrice != null && parsedPrice > 0.0 && category in categories && errorMessage.isEmpty()) {
                        val product = ProductEntity(
                            id = parsedId!!,
                            name = name,
                            price = parsedPrice,
                            deliveryDate = deliveryDate,
                            category = category,
                            isFavorite = isFavorite
                        )

                        coroutineScope.launch {
                            productViewModel.insertProduct(product)
                            navController.navigate("products_list") {
                                popUpTo("add_product") { inclusive = true }
                            }
                        }
                    } else {
                        errorMessage = "Please enter a valid date"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) {
                Text(text = "Add Product", fontWeight = FontWeight.Bold,color = Color.Black) // Make button text bold
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Button to List products
            Button(
                onClick = { navController.navigate("products_list") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB).copy(alpha = 0.7f))
            ) {
                Text("List products", fontWeight = FontWeight.Bold,color = Color.Black) // Make text bold
            }
        }
    }
}
