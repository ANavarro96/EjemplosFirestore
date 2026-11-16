package com.cifpcesar.prueba.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val RUTA_COLECCION = "CAMBIAME POR EL NOMBRE DE TU COLECCION"

    // Variable para guardar el registro de la escucha en tiempo real
    private var listenerRegistration: ListenerRegistration? = null

    fun escucharDatos(onRecibir: (List<Any>) -> Unit) {
        listenerRegistration = db.collection(RUTA_COLECCION)
            .addSnapshotListener { snapshot, error ->
            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }
            if (snapshot != null) {
                onRecibir(snapshot.toObjects(Any::class.java))
            }
        }
    }

    fun detenerEscucha() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

    // Operaciones de escritura y borrado
    fun anadirElemento(elemento: Any) {

    }

    fun borrarElemento(id: String) {

    }

    // TODO: Métodos con get (únicos, puntuales)

    // TODO: Cambiad el Any por la clase creada
    suspend fun obtenerTodas(): List<Any> {
        val snapshot = db.collection(RUTA_COLECCION).get().await()
        return snapshot.toObjects(Any::class.java)
    }



    // 2. Filtro con 1 campo
    suspend fun buscarUnCampo(algo : Any): List<Any> {
        return emptyList()
    }

    // 3. Filtro con varios campos
    suspend fun buscarDosCampos(algo: Any, otraCosa: Any): List<Any> {
        return emptyList()
    }

    // 4. Orden y filtro
    suspend fun obtenerTop3Baratas(): List<Any> {
        return emptyList()
    }


}
