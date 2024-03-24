package com.example.mygesplus.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.mygesplus.model.Course
import com.example.mygesplus.ui.theme.CtxMain
import com.example.mygesplus.viewmodel.FullCourseDescriptionViewModel
import java.io.IOException
import java.net.URL

@Composable
fun FullCourseDescriptionView(
    course: Course,
    fullCourseDescriptionViewModel: FullCourseDescriptionViewModel,
    dispatchTakePictureIntent: () -> Unit,
    removePhoto: () -> Unit,
    /*photoDuCours: Bitmap?*/
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

        val photos by fullCourseDescriptionViewModel.photoCourseLiveData.collectAsState(
            initial = emptyList()
        )

        fullCourseDescriptionViewModel.fetchPhotoForCourse(course.id)

        /*AVEC LIB mais ne fonction pas*/
        /*J'ai dans bdd /storage/emulated/0/Pictures/MyGesPlus/ */
        /*J'ai dans telephone /Intarlal storage/Pictures/MyGesPlus/1711243777429.jpg */
        /*photos.forEach { photo ->
            CoilImage(
                data = photo.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .scale(0.5f)
            )
        }*/

        photos.forEach { photo ->
            Text(text = photo.photoUrl)

            val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

            LaunchedEffect(photo.photoUrl) {
                val loadedImageBitmap = loadImageBitmap(photo.photoUrl)
                imageBitmap.value = loadedImageBitmap
            }

            imageBitmap.value?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                // photo.id pour supprimer la photo de la base
                // photo.photoUrl pour supprimer la photo de la galerie

                Button(
                    onClick = {
                        removePhoto()
                    }
                ) {
                    Text("Supprimer la photo")
                }

//                buttonVisible.value = false
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



        // La photo du cours n'apparaît que si elle existe.
        /*photoDuCours?.let {
            Image(
                bitmap = photoDuCours.asImageBitmap(),
                contentDescription = null, // Ajoutez une description si nécessaire
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Rendre le bouton invisible une fois que l'image est affichée
            buttonVisible.value = false
        }*/
    }


}


/*SI jamais mais ça ne fonction pas*/
suspend fun loadImageBitmap(url: String): ImageBitmap? {
    return try {
        val stream = URL(url).openStream()
        val bitmap = BitmapFactory.decodeStream(stream)
        bitmap.asImageBitmap()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}