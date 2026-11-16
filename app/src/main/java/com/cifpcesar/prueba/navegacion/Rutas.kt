package com.cifpcesar.prueba.navegacion

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Rutas {
    @Serializable
    data object PantallaPrincipal : NavKey

    @Serializable
    data object PantallaConsultas : NavKey
}