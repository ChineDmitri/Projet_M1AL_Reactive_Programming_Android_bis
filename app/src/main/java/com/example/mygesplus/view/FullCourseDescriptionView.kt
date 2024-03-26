package com.example.mygesplus.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.getColumnIndex
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
    removePhoto: (id: Int, uri: String) -> Unit,
    /*photoDuCours: Bitmap?*/
) {

    // Définir un état pour gérer la visibilité du bouton
    val buttonVisible = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(232, 252, 128, 255)),
    ) {

        //Topbar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .background(Color(249, 25, 21, 255)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            )
        {
            Text(
                text = course.heureDebut + " - " + course.heureFin,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(color = Color.White, fontSize = 26.sp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = course.nom,
                style = TextStyle(color = Color.Black, fontSize = 26.sp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = course.date,
                style = TextStyle(color = Color.Black, fontSize = 22.sp))
        }

        // Card Description
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {

            Column(
                modifier = Modifier
                    .background(Color(73, 158, 4, 255))
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(text = course.isPresentiel.toString(),
                    style = TextStyle(color = Color.White, fontSize = 22.sp))
            }

            Column(
                modifier = Modifier
                    .background(Color(146, 250, 61, 255))
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
            ) {
                Text(modifier = Modifier
                    .padding(5.dp)
                    ,text = course.description,)
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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    dispatchTakePictureIntent() // Appel de la fonction de prise de photo
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.Black
                )
            ) {
                Text("Prendre une photo")
            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )

            {
                itemsIndexed(photos) { index, photo ->
                    //Text(text = index.toString() + " " + photo.photoUrl)

                    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

                    LaunchedEffect(photo.photoUrl) {
                        val loadedImageBitmap = loadImageBitmap(photo.photoUrl)
                        imageBitmap.value = loadedImageBitmap
                    }

                    Box(modifier = Modifier
                        .padding(10.dp),
                        )
                    {

                        imageBitmap.value?.let { bitmap ->
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(470.dp),
                            )

                            // photo.id pour supprimer la photo de la base
                            // photo.photoUrl pour supprimer la photo de la galerie

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement =  Arrangement.End
                            ) {
                                Button( modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                    onClick = {
                                        removePhoto(photo.id, photo.photoUrl)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(249, 25, 21, 255),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(modifier = Modifier,
                                        text = "X")
                                }

                            }

                    }

//                buttonVisible.value = false
                    }
                }
            }

            Button(
                onClick = {
                    dispatchTakePictureIntent() // Appel de la fonction de prise de photo
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Prendre une photo")
            }
        }


        /*photos.forEach { photo ->
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
                        removePhoto(photo.id, photo.photoUrl)
                    }
                ) {
                    Text("Supprimer la photo")
                }

//                buttonVisible.value = false
            }
        }*/

        // Bouton pour prendre une photo
        /*if (buttonVisible.value) {
            Button(
                onClick = {
                    dispatchTakePictureIntent() // Appel de la fonction de prise de photo
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Prendre une photo")
            }
        }*/


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