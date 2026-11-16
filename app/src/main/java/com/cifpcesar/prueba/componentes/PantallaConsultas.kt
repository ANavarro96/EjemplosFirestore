package com.cifpcesar.prueba.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cifpcesar.prueba.viewmodel.ViewModelConsultas

// =======================================================
// PANTALLA 2: Consultas Puntuales con get() (Sin Listener)
// =======================================================
@Composable
fun PantallaConsultas(
    modifier: Modifier = Modifier,
    viewModel: ViewModelConsultas = viewModel()
) {
    val estado by viewModel.estadoUI.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Consultas Puntuales con get()",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Card que informa del filtro o consulta ejecutada
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = estado.ultimoFiltroAplicado,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botones para probar diferentes consultas con get()
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.cargarTodas() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("1. Todas (get)")
                }
                Button(
                    onClick = { viewModel.filtrarPorSabor("Jamón") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("2. Sabor: Jamón")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.filtrarPorSaborYPrecio("Jamón", 2.0) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("3. Jamón y <= 2€")
                }
                Button(
                    onClick = { viewModel.obtenerTop3Baratas() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("4. Top 3 Baratas")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (estado.cargando) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = estado.listaResultados,
                    key = { it.id }
                ) { patata ->
                    ListItem(
                        headlineContent = { Text(patata.marca) },
                        supportingContent = { Text("Sabor: ${patata.sabor} - ${patata.precio} €") }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
