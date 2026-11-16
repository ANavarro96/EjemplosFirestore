package com.cifpcesar.prueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cifpcesar.prueba.model.Patata
import com.cifpcesar.prueba.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConsultasUiState(
    val listaResultados: List<Patata> = emptyList(),
    val cargando: Boolean = false,
    val ultimoFiltroAplicado: String = "Pulsa un botón para ejecutar get()"
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
                cargando = false,
                ultimoFiltroAplicado = "Consulta get(): Todas las patatas (${lista.size})"
            )
        }
    }

    // 2. Filtro con 1 campo: whereEqualTo("sabor", sabor)
    fun filtrarPorSabor(sabor: String = "Jamón") {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.buscarPorSabor(sabor)
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false,
                ultimoFiltroAplicado = "Filtro 1 campo: sabor == '$sabor' (${lista.size})"
            )
        }
    }

    // 3. Filtro con 2 campos: whereEqualTo + whereLessThanOrEqualTo
    fun filtrarPorSaborYPrecio(sabor: String = "Jamón", maxPrecio: Double = 2.0) {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.buscarPorSaborYPrecioMaximo(sabor, maxPrecio)
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false,
                ultimoFiltroAplicado = "Filtro 2 campos: sabor == '$sabor' AND precio <= $maxPrecio € (${lista.size})"
            )
        }
    }

    // 4. Ordenar y Top 3: orderBy("precio", ASCENDING) + limit(3)
    fun obtenerTop3Baratas() {
        viewModelScope.launch {
            _estadoUI.value = _estadoUI.value.copy(cargando = true)
            val lista = repository.obtenerTop3Baratas()
            _estadoUI.value = ConsultasUiState(
                listaResultados = lista,
                cargando = false,
                ultimoFiltroAplicado = "Ordenación + Límite: Top 3 más baratas"
            )
        }
    }
}
