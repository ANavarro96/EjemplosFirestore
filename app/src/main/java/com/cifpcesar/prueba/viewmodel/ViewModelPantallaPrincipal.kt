package com.cifpcesar.prueba.viewmodel

import androidx.lifecycle.ViewModel
import com.cifpcesar.prueba.model.Patata
import com.cifpcesar.prueba.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PantallaPrincipalUiState(
    val listaPatatas: List<Patata> = emptyList()
)

class ViewModelPantallaPrincipal : ViewModel() {

    private val repository = FirestoreRepository()

    private val _estadoUI = MutableStateFlow(PantallaPrincipalUiState())
    val estadoUI = _estadoUI.asStateFlow()

    init {
        repository.escucharDatos { nuevaLista ->
            _estadoUI.value = PantallaPrincipalUiState(listaPatatas = nuevaLista)
        }
    }

    fun anadirPatata(marca: String, precio: Double, sabor: String) {
        val nuevaPatata = Patata(
            marca = marca,
            precio = precio,
            sabor = sabor
        )
        repository.anadirPatata(nuevaPatata)
    }

    fun borrarPatata(id: String) {
        repository.borrarPatata(id)
    }

    // Esto se lanza en el viewmodel cuando
    // salimos de la pantalla y se destruye
    override fun onCleared() {
        repository.detenerEscucha()
    }
}
