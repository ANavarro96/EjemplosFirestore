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

@Composable
fun PantallaListener(
    modifier: Modifier = Modifier,
    viewModel: ViewModelPantallaPrincipal = viewModel()
) {
    val estado by viewModel.estadoUI.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Lista de elementos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(
                items = emptyList<String>()/*TODO: Acceder a la lista de elementos*/,
                //key = { it.id }
            ) { elemento ->
                /* TODO: Pintar cada elemento en un ListItem */
                ListItem(
                    headlineContent = { },
                    supportingContent = { },
                    trailingContent = {
                        Button(onClick = {  }) {
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

                //viewModel.addElemento()
            }) {
                Text("Añadir Elemento")
            }
        }
    }
}
