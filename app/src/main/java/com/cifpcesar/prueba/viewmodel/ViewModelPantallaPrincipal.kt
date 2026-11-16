package com.cifpcesar.prueba.viewmodel

import androidx.lifecycle.ViewModel
import com.cifpcesar.prueba.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PantallaPrincipalUiState(
    val listaPatatas: List<Any> = emptyList()
)

class ViewModelPantallaPrincipal : ViewModel() {

    private val repository = FirestoreRepository()

    private val _estadoUI = MutableStateFlow(PantallaPrincipalUiState())
    val estadoUI = _estadoUI.asStateFlow()

    // Este init se lanza al inicio... lo que hacemos es escuchar los cambios en la lista
    init {
        repository.escucharDatos { nuevaLista ->
            _estadoUI.value = PantallaPrincipalUiState(listaPatatas = nuevaLista)
        }
    }

    fun addElemento() {
        // TODO: Llamar al método del repositorio
    }

    fun borrarPatata(id: String) {
        // TODO: Llamar al método del repositorio
    }

    // Esto se lanza en el viewmodel cuando
    // salimos de la pantalla y se destruye
    override fun onCleared() {
        repository.detenerEscucha()
    }
}
