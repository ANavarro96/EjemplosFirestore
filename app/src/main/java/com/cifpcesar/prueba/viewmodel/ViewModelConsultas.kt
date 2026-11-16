package com.cifpcesar.prueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cifpcesar.prueba.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConsultasUiState(
    val listaResultados: List<Any> = emptyList(),
    val cargando: Boolean = false,
)

class ViewModelConsultas : ViewModel() {

    private val repository = FirestoreRepository()

    private val _estadoUI = MutableStateFlow(ConsultasUiState())
    val estadoUI = _estadoUI.asStateFlow()

    // 1. Obtener todas las patatas (get() puntual)
    fun cargarTodas() {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.obtenerTodas()
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false)
        }
    }

    // 2. Filtro con 1 campo
    fun filtrarUnCampo(campo: Any = "") {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.buscarUnCampo(campo)
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false)
        }
    }

    // 3. Filtro con 2 campos
    fun filtrarDosCampos(algo: Any = "", otraCosa: Any = "") {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.buscarDosCampos(algo, otraCosa)
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false)
        }
    }

    // 4. Ordenar y Top 3
    fun obtenerTop3Baratas() {
        viewModelScope.launch {

        }
    }
}
