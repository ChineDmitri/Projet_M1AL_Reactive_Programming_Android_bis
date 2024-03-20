package com.example.mygesplus.model

import java.sql.Timestamp

data class CourseFb(
    val id: String? = null,
    val nom: String,
    val date: Timestamp,
    val heureDebut: String,
    val heureFin: String,
    val description: String,
    val isPresentiel: Boolean
)