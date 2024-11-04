package com.example.practica04.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.practica04.R
import com.example.practica04.dialogs.Alerta
import com.example.practica04.dialogs.AlertaExito
import com.example.practica04.model.Producto
import com.example.practica04.navigation.ListaProductos
import com.example.practica04.viewmodels.ProductoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductosView(navController: NavController, viewModel: ProductoViewModel, modifier: Modifier = Modifier) {



        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryLight,
                        titleContentColor = onPrimaryLight
                    ),
                    title = {
                        Text(text = "Agregar producto",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = onPrimaryLight))
                    },
                    )
            }
        ) { innerPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(primaryLight)) {
                Formulario(viewModel, navController)
            }
        }



}

@Composable
fun CampoTexto(label: String, value: String, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, textArea: Boolean = false, icono: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {

        OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(text = label) }, keyboardOptions = keyboardOptions, modifier = if (textArea) Modifier.height(200.dp) else Modifier, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.botones),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = colorResource(id = R.color.botones),
            unfocusedLabelColor = colorResource(id = R.color.botones),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ), textStyle = TextStyle(Color.Black)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFecha(datePickerState: DatePickerState, selectedDate: String) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .offset(x = 28.dp)
        .width(IntrinsicSize.Min)) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {  },
            label = { Text("Fecha de registro") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    showDatePicker = !showDatePicker
                }) {
                }
            },
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(

            ),
            textStyle = TextStyle(Color.Black)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                    )
                }
            }
        }
    }
    Button(onClick = { showDatePicker = !showDatePicker  },  colors = ButtonColors(
        containerColor = primaryContainerLight,
        contentColor = colorResource(id = R.color.white),
        disabledContentColor = Color.LightGray,
        disabledContainerColor = Color.LightGray
    ),
        modifier = Modifier
            .fillMaxWidth()) {
        Text(text = "Seleccionar Fecha de Registro")

    }
}

fun convertirMillisAFecha(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(viewModel: ProductoViewModel, navController: NavController, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
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
                    viewModel.addProduct(Producto(nombre = name, descripcion = description, precio = price.toInt(), fecha = selectedDate))

                    successMsg = """
                        Producto agregado exitosamente:
                        - Nombre: $name
                        - Precio: $price
                        - Descripción: $description
                        - Fecha de registro: $selectedDate
                    """.trimIndent()


                    showSuccessDialog = true
                }
            } catch (e: Exception) {
                errorMsg = "Error"
            }
        },                 colors = ButtonColors(
            containerColor = primaryContainerLight,
            contentColor = colorResource(id = R.color.white),
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray
        )) {
            Text(text = "Registrar")
        }
        Button(onClick = { navController.navigate(ListaProductos) },
            colors = ButtonColors(
                containerColor = primaryContainerLight,
                contentColor = colorResource(id = R.color.white),
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            )) {
            Text(text = "Cancelar")
        }

    }
    Spacer(modifier = Modifier.width(16.dp))
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
    AlertaExito(
        onDismissRequest = {
            showSuccessDialog = false
            navController.navigate(ListaProductos)  // Redirigir a la lista de productos después de cerrar el diálogo
        },
        dialogText = successMsg,
        show = showSuccessDialog
    )
}

