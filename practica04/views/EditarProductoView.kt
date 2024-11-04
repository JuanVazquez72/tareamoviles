package com.example.practica04.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.practica04.R
import com.example.practica04.dialogs.Alerta
import com.example.practica04.dialogs.AlertaEdit
import com.example.practica04.dialogs.AlertaExito
import com.example.practica04.model.Producto
import com.example.practica04.navigation.Home
import com.example.practica04.navigation.ListaProductos
import com.example.practica04.viewmodels.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoView(productId: Int, navController: NavController, viewModel: ProductoViewModel, modifier: Modifier = Modifier) {

    val colorTextPrimary1 = Color(0xFFC5705D)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight,
                    titleContentColor = colorTextPrimary1
                ),
                title = {
                    Text(text = "Editar producto", style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryLight))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {

                    }
                },
            )
        }
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = primaryLight)) {
            FormularioEditar(producto = viewModel.getProductById(productId), viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEditar(producto: Producto?, viewModel: ProductoViewModel, navController: NavController, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf(producto?.nombre ?: "") }
    var price by remember { mutableStateOf(producto?.precio.toString() ?: "") }
    var description by remember { mutableStateOf(producto?.descripcion ?: "") }
    val datePickerState = rememberDatePickerState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var successMsg by remember { mutableStateOf("") }
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertirMillisAFecha(it)
    } ?: ""

    var errorMsg by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CampoTexto(label = "Nombre", value = name, icono = R.color.pastel, onValueChange = { name = it })
        CampoTexto(label = "Precio", value = price, icono = R.color.pastel, onValueChange = { price = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        CampoTexto(label = "Descripcion", value = description, icono = R.color.pastel, onValueChange = { description = it })
        SelectorFecha(datePickerState, selectedDate)
    }
    Row {
        Button(onClick = {
            try {
                if (name.isBlank() || description.isBlank() || selectedDate.isBlank()) {
                    errorMsg = "Favor de llenar los campos"
                    showErrorDialog = true
                } else if (price.toIntOrNull() == null) {
                    errorMsg = "Favor de ingresar un precio válido"
                    showErrorDialog = true
                } else {
                    viewModel.updateProduct(Producto(id = producto?.id!!, nombre = name, descripcion = description, precio = price.toInt(), fecha = selectedDate))
                    successMsg = """
                        Producto editado exitosamente:
                    """.trimIndent()


                    showSuccessDialog = true
                }
            } catch (e: Exception) {
                errorMsg = "Error"
            }
        }, modifier = modifier, colors =
        ButtonColors(
            containerColor = primaryContainerLight,
            contentColor = colorResource(id = R.color.white),
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray
        )) {
            Text(text = "Actualizar")
        }
        Button(onClick = { navController.popBackStack() },
            colors = ButtonColors(
                containerColor = primaryContainerLight,
                contentColor = colorResource(id = R.color.white),
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            )) {
            Text(text = "Cancelar")
        }
    }

    Alerta(
        dialogTitle = "Error",
        dialogText = errorMsg,
        onDismissRequest = {
            showErrorDialog = false
        },
        onConfirmation = {
            showErrorDialog = false
        },
        show = showErrorDialog
    )
    AlertaEdit(
        onDismissRequest = {
            showSuccessDialog = false
            navController.navigate(ListaProductos)  // Redirigir a la lista de productos después de cerrar el diálogo
        },
        dialogText = successMsg,
        show = showSuccessDialog
    )
}