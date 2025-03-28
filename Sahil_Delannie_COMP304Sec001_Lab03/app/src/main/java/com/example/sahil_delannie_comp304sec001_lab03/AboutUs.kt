package com.example.sahil_delannie_comp304sec001_lab03

import android.widget.VideoView
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@Composable
fun AboutUs(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background video using AndroidView
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    // Set your video URI (local or remote)
                    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/about") // Replace with your video resource
                    setVideoURI(videoUri)
                    setZOrderOnTop(false) // Set the video layer behind the text
                    setOnPreparedListener { mp -> mp.isLooping = true } // Loop the video
                    start()
                }
            },
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)// Make the video fill the entire screen
        )

        // Column to vertically arrange the text
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Center the column in the Box
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // About Us description
            Text(
                text = "ManageBuddy is your ultimate product management app! Effortlessly create, edit, " +
                        "and delete products on the go with an intuitive, user-friendly interface. " +
                        "Stay organized and in control of your inventory, all in the palm of your hand!",
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.65f)) // Background with transparency
                    // Rounded corners for the background
                    .padding(40.dp), // Add padding around the text
                color = Color.White
            )
        }
    }
}
