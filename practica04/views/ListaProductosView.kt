package com.example.practica04.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.practica04.viewmodels.ProductoViewModel
import com.example.practica04.R
import com.example.practica04.dialogs.Alerta
import com.example.practica04.model.Producto
import com.example.practica04.navigation.EditarProducto
import com.example.practica04.navigation.FormularioProductos
import com.example.practica04.navigation.Home
import com.example.practica04.navigation.ListaProductos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProductosView(viewModel: ProductoViewModel, navController: NavController, modifier: Modifier = Modifier) {
    var productIdToDelete by remember { mutableStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight,
                    titleContentColor = primaryLight
                ),
                title = {
                    Text(
                        text = "Productos",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryLight
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Home)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(FormularioProductos)
                },
                containerColor = primaryContainerLight,
                contentColor = onPrimaryLight,
            ) {
                Icon(Icons.Filled.Add, "Agregar producto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = primaryLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val estado = viewModel.estado

            if (estado.estaCargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (estado.productos.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "        No hay productos en Existencia",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = onPrimaryLight
                        )
                    }

                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(estado.productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "${producto.nombre} ($${producto.precio} pesos)",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryLight
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                // Aquí se añade la fecha de registro del producto
                                Text(
                                    text = "Fecha de registro: ${producto.fecha}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = primaryLight
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = producto.descripcion,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = primaryLight
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                // Fila de botones para Actualizar y Eliminar
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    SmallFloatingActionButton(
                                        onClick = {
                                            navController.navigate(EditarProducto(productId = producto.id))
                                        },
                                        containerColor = primaryContainerLight,
                                        contentColor = colorResource(id = R.color.white),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Icon(Icons.Filled.Settings, "Editar producto")
                                    }

                                    SmallFloatingActionButton(
                                        onClick = {
                                            showDeleteDialog = true
                                            productIdToDelete = producto.id
                                        },
                                        containerColor = primaryContainerLight,
                                        contentColor = colorResource(id = R.color.white)
                                    ) {
                                        Icon(Icons.Filled.Close, "Eliminar producto")
                                    }
                                }
                            }
                        }
                    }
                }

                // Alerta de confirmación de eliminación
                Alerta(
                    dialogTitle = "Eliminar",
                    dialogText = "¿Desea continuar?",
                    onDismissRequest = {
                        println(productIdToDelete)
                        showDeleteDialog = false
                    },
                    onConfirmation = {
                        viewModel.deleteProduct(Producto(productIdToDelete, "", "", 0, ""))
                        showDeleteDialog = false
                    },
                    show = showDeleteDialog
                )
            }
        }
    }
}


