package com.example.mygesplus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "cours")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nom") val nom: String,
    @ColumnInfo(name = "date") val date: Timestamp,
    @ColumnInfo(name = "heure_debut") val heureDebut: String,
    @ColumnInfo(name = "heure_fin") val heureFin: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "isPresentiel") val isPresentiel: Boolean,
)
