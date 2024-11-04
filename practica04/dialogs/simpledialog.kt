package com.example.practica04.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


@Composable
fun Alerta(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    show: Boolean
) {
    if (show) {
        AlertDialog(
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun AlertaExito(
    onDismissRequest: () -> Unit,
    dialogText: String,
    show: Boolean
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                // No hacer nada al tocar fuera del diálogo, se cierra solo con "OK"
            },
            title = {
                Text(text = "Producto Agregado")
            },
            text = {
                Text(text = dialogText)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()  // Cierra el diálogo solo al presionar "OK"
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
@Composable
fun AlertaEdit(
    onDismissRequest: () -> Unit,
    dialogText: String,
    show: Boolean
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                // No hacer nada al tocar fuera del diálogo, se cierra solo con "OK"
            },
            title = {
                Text(text = "Producto Editado")
            },
            text = {
                Text(text = dialogText)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()  // Cierra el diálogo solo al presionar "OK"
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
