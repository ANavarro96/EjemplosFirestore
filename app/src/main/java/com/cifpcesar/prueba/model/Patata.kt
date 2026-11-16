package com.cifpcesar.prueba.model

import com.google.firebase.firestore.DocumentId

data class Patata(
    @DocumentId val id: String = "",
    val marca: String = "",
    val precio: Double = 0.0,
    val sabor: String = ""
)
