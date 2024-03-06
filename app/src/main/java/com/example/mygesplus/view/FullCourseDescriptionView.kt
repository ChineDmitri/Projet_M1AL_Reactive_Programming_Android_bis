package com.example.mygesplus.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.mygesplus.model.Course
import com.example.mygesplus.ui.theme.CtxMain

@Composable
fun FullCourseDescriptionView(
    course: Course,
    dispatchTakePictureIntent: () -> Unit,
    photoDuCours: Bitmap?
) {

    // Définir un état pour gérer la visibilité du bouton
    val buttonVisible = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier.background(CtxMain)
        ) {
            Text(text = course.description + " " + course.date + " " + course.isPresentiel.toString())
        }
    }

    // Bouton pour prendre une photo
    if (buttonVisible.value) {
        Button(
            onClick = {
                dispatchTakePictureIntent() // Appel de la fonction de prise de photo
            }
        ) {
            Text("Prendre une photo")
        }
    }

    // La photo du cours n'apparaît que si elle existe.
    photoDuCours?.let {
        Image(
            bitmap = photoDuCours.asImageBitmap(),
            contentDescription = null, // Ajoutez une description si nécessaire
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        // Rendre le bouton invisible une fois que l'image est affichée
        buttonVisible.value = false
    }

}