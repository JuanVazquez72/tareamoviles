package com.example.practica04.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.colorTextSecondary
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.practica04.R

@Composable
fun BotonRegresar(navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = { navController.popBackStack() },
        modifier = modifier.size(width = 80.dp, height = 40.dp).padding(top = 20.dp, end = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryContainerLight,
            contentColor = Color.White
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic), // Asumiendo que tienes un icono de flecha
            contentDescription = "Regresar"
        )
    }
}

@Composable
fun Textos(modifier: Modifier = Modifier) {
    Text(
        text = "Vazquez German Juan Luis \n\nSoporte Técnico",
        fontSize = 23.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = onPrimaryLight,
        modifier = modifier
    )
}

@Composable
fun ContactRow(iconId: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 18.sp, color = colorTextSecondary)
    }
}

@Composable
fun PresentacionView(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(primaryLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            BotonRegresar(navController)
        }

        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.foto),
            contentDescription = "Juan Luis Vazquez",
            modifier = Modifier
                .size(250.dp)
                .padding(top = 30.dp)
                .graphicsLayer(rotationZ = 0f),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )

        Textos(modifier = Modifier.padding(top = 30.dp))

        Spacer(modifier = Modifier.height(70.dp))

        ContactRow(iconId = R.drawable.llamada, text = "+52 662 106 1284")
        ContactRow(iconId = R.drawable.linkedin, text = "vazquezgermanjuanluis72")
        ContactRow(iconId = R.drawable.mail, text = "a22022308@unison.mx")
    }
}
