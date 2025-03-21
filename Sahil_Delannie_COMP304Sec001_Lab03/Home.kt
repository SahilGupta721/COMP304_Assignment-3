package com.example.sahil_delannie_comp304sec001_lab03

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

class Home {
    @Composable
    //will display just heading
    fun DisplayHeader(modifier: Modifier=Modifier){
    Box(modifier = Modifier.fillMaxWidth().height(170.dp)){//giving fixed height
        Text(text = "Project Manager",
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.Center),
        style = TextStyle(fontSize = 32.sp)
        )

    }
    }
}