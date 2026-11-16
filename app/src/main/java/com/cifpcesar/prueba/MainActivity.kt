package com.cifpcesar.prueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.cifpcesar.prueba.componentes.PantallaConsultas
import com.cifpcesar.prueba.componentes.PantallaListener
import com.cifpcesar.prueba.navegacion.Rutas
import com.cifpcesar.prueba.ui.theme.PruebaFirestoreTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaFirestoreTheme {
                AppNavegacion()
            }
        }
    }
}

@Composable
fun AppNavegacion() {
    val backStack = rememberNavBackStack(Rutas.PantallaPrincipal)
    val rutaActual = backStack.lastOrNull() ?: Rutas.PantallaPrincipal

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (rutaActual != Rutas.PantallaPrincipal) {
                            backStack.clear()
                            backStack.add(Rutas.PantallaPrincipal)
                        }
                    },
                    icon = { Text("⚡") },
                    text = { Text("Tiempo Real") }
                )
                ExtendedFloatingActionButton(
                    onClick = {
                        if (rutaActual != Rutas.PantallaConsultas) {
                            backStack.clear()
                            backStack.add(Rutas.PantallaConsultas)
                        }
                    },
                    icon = { Text("🔍") },
                    text = { Text("Consultas get()") }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Rutas.PantallaPrincipal> {
                    PantallaListener(modifier = Modifier.padding(innerPadding))
                }
                entry<Rutas.PantallaConsultas> {
                    PantallaConsultas(modifier = Modifier.padding(innerPadding))
                }
            }
        )
    }
}
