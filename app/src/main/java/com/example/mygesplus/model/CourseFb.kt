package com.example.mygesplus.model

import com.google.firebase.firestore.PropertyName

data class CourseFb(
    val id: String = "",
    val nom: String = "",
    val date: String = "",
    val heureDebut: String = "",
    val heureFin: String = "",
    val description: String = "",
    @field:PropertyName("isPresentiel")
    val isPresentiel: Boolean = false
) {
    // No-args constructor
    constructor() : this("","", "", "", "", "", false)
}