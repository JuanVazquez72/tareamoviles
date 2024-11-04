package com.example.practica04.views

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.colorTextSecondary
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.practica04.R
import com.example.practica04.navigation.ListaProductos
import com.example.practica04.navigation.Presentacion


@Composable
fun HomeView(navController:NavController ,modifier: Modifier = Modifier) {

    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
        .fillMaxSize()
        .background(primaryLight)
    ){

        Row( modifier= modifier.padding(top = 30.dp)) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "imagen",
                modifier = Modifier
                    .size(180.dp)
                    .padding(top = 30.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,

                )

        }
        Row(modifier= modifier.padding(top = 20.dp)) {

            Text(
                text = "SnackHere",
                color = onPrimaryLight,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {

            Button(onClick = {
                navController.navigate(ListaProductos)
            }, modifier = modifier.size(width = 150.dp, height =70.dp),
                    colors = ButtonColors(
                    containerColor = primaryContainerLight,
                contentColor = Color.White,
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            )) {
                Text(text = "Listado")
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {

            Button(onClick = {
                navController.navigate(Presentacion)
            }, modifier = modifier.size(width = 150.dp, height =70.dp),
                colors = ButtonColors(
                    containerColor = primaryContainerLight,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray
                )) {
                Text(text = "Presentación")
            }

        }

        Spacer(modifier = Modifier.width(8.dp))
        Spacer(modifier = Modifier.height(180.dp))
        Text(
            text = "Vazquez German Juan Luis",
            fontSize = 20.sp,
            color = colorTextSecondary,
            modifier = Modifier.padding(16.dp)
        )





    }

}
/*
@Preview
@Composable
fun view() {
    val navController = rememberNavController()
    Home(navController =navController )
}
*/
