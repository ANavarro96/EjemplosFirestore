package com.cifpcesar.prueba.repository

import com.cifpcesar.prueba.model.Patata
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    // Variable para guardar el registro de la escucha en tiempo real
    private var listenerRegistration: ListenerRegistration? = null

    fun escucharDatos(onRecibir: (List<Patata>) -> Unit) {
        listenerRegistration = db.collection("patatas").addSnapshotListener { snapshot, error ->
            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }
            if (snapshot != null) {
                onRecibir(snapshot.toObjects(Patata::class.java))
            }
        }
    }

    fun detenerEscucha() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

    // ==========================================
    // MÉTODOS PUNTUALES CON get().await() (SIN LISTENER)
    // ==========================================

    // 1. Obtener todos los documentos una sola vez
    suspend fun obtenerTodas(): List<Patata> {
        val snapshot = db.collection("patatas").get().await()
        return snapshot.toObjects(Patata::class.java)
    }

    // 2. Filtro con 1 campo: whereEqualTo("sabor", sabor)
    suspend fun buscarPorSabor(sabor: String): List<Patata> {
        val snapshot = db.collection("patatas")
            .whereEqualTo("sabor", sabor)
            .get()
            .await()

        return snapshot.toObjects(Patata::class.java)
    }

    // 3. Filtro con 2 campos: whereEqualTo + whereLessThanOrEqualTo
    suspend fun buscarPorSaborYPrecioMaximo(sabor: String, precioMax: Double): List<Patata> {
        val snapshot = db.collection("patatas")
            .whereEqualTo("sabor", sabor)
            .whereLessThanOrEqualTo("precio", precioMax)
            .get()
            .await()
        return snapshot.toObjects(Patata::class.java)
    }

    // 4. Ordenación y límite: orderBy("precio", ASCENDING) + limit(3) -> Top 3 más baratas
    suspend fun obtenerTop3Baratas(): List<Patata> {
        val snapshot = db.collection("patatas")
            .orderBy("precio", Query.Direction.ASCENDING)
            .limit(3)
            .get()
            .await()
        return snapshot.toObjects(Patata::class.java)
    }

    // Operaciones de escritura y borrado
    fun anadirPatata(patata: Patata) {
        val datos = hashMapOf(
            "marca" to patata.marca,
            "precio" to patata.precio,
            "sabor" to patata.sabor
        )
        db.collection("patatas").add(datos)
    }

    fun borrarPatata(id: String) {
        if (id.isNotEmpty()) {
            db.collection("patatas").document(id).delete()
        }
    }
}
