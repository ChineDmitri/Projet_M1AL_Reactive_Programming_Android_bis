package com.example.mygesplus.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Prendre une photo")
            }
        }
    }


}