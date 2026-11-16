package com.cifpcesar.prueba.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cifpcesar.prueba.viewmodel.ViewModelPantallaPrincipal
import kotlin.random.Random

// =======================================================
// PANTALLA 1: Tiempo Real con addSnapshotListener
// =======================================================
@Composable
fun PantallaListener(
    modifier: Modifier = Modifier,
    viewModel: ViewModelPantallaPrincipal = viewModel()
) {
    val estado by viewModel.estadoUI.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Escucha en Tiempo Real (addSnapshotListener)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(
                items = estado.listaPatatas,
                key = { it.id }
            ) { patata ->
                ListItem(
                    headlineContent = { Text(patata.marca) },
                    supportingContent = { Text("Sabor: ${patata.sabor} - ${patata.precio} €") },
                    trailingContent = {
                        Button(onClick = { viewModel.borrarPatata(patata.id) }) {
                            Text("Borrar")
                        }
                    }
                )
                HorizontalDivider()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val marcas = listOf("Lays", "Ruffles", "Pringles", "Doritos")
                val sabores = listOf("Campesina", "Jamón", "Queso", "Sal")
                val marcaAzar = marcas.random()
                val saborAzar = sabores.random()
                val precioAzar = (Random.nextDouble(1.0, 3.0) * 100).toInt() / 100.0

                viewModel.anadirPatata(marcaAzar, precioAzar, saborAzar)
            }) {
                Text("Añadir Patata Aleatoria")
            }
        }
    }
}
