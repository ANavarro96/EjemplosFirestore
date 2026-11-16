package com.cifpcesar.prueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.cifpcesar.prueba.componentes.PantallaConsultas
import com.cifpcesar.prueba.componentes.PantallaListener
import com.cifpcesar.prueba.navegacion.Rutas
import com.cifpcesar.prueba.ui.theme.PruebaFirestoreTheme

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
            NavigationBar {
                NavigationBarItem(
                    selected = rutaActual == Rutas.PantallaPrincipal,
                    onClick = {
                        if (rutaActual != Rutas.PantallaPrincipal) {
                            backStack.clear()
                            backStack.add(Rutas.PantallaPrincipal)
                        }
                    },
                    label = { Text("Tiempo Real") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = rutaActual == Rutas.PantallaConsultas,
                    onClick = {
                        if (rutaActual != Rutas.PantallaConsultas) {
                            backStack.clear()
                            backStack.add(Rutas.PantallaConsultas)
                        }
                    },
                    label = { Text("Consultas") },
                    icon = { Icon(Icons.Filled.Search, contentDescription = null) }
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
