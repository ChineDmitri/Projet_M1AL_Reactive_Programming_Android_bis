package com.example.mygesplus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.CoursePhoto
import com.example.mygesplus.view.FullCourseDescriptionView
import com.example.mygesplus.viewmodel.FullCourseDescriptionViewModel
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class FullDescriptionCourseActivity : ComponentActivity() {

    private val viewModel: FullCourseDescriptionViewModel by viewModels()

    private lateinit var course: Course
    // Gestion de la photo

    val REQUEST_IMAGE_CAPTURE = 1
    var photoDuCours by mutableStateOf<Bitmap?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val str = intent.getStringExtra("cours")
        Log.wtf("courseSelected str", str)
        course = Gson().fromJson(str, Course::class.java)


        Log.wtf("courseSelected class", course.description)

        setContent {
            val fullCourseDescriptionViewModel: FullCourseDescriptionViewModel = viewModel(factory = FullCourseDescriptionViewModel.factory)

            FullCourseDescriptionView(
                course,
                fullCourseDescriptionViewModel,
                this@FullDescriptionCourseActivity::dispatchTakePictureIntent,
                this@FullDescriptionCourseActivity::removePhoto,
            )
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // ERREUR
            Toast.makeText(this, "Une erreur est survenue lors de l'ouverture de la caméra.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoDuCours = data?.extras?.get("data") as Bitmap

            // Sauvegarde de l'image dans le stockage interne de l'appareil
            saveBitmapImage(photoDuCours!!)
        }
    }

    /**
     * Cette fonction permet de sauvegarder une image Bitmap dans le stockage interne.
     * @param bitmap L'image Bitmap à sauvegarder
     */
    private fun saveBitmapImage(bitmap: Bitmap) {
        // On récupère le répertoire des images publiques (généralement le répertoire Pictures)
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // On créer un fichier pour l'image avec un nom unique basé sur le timestamp actuel.
        val imageFile = File(imagesDir, "my_ges_pp_${System.currentTimeMillis()}.jpg")

        return try {
            // On ouvre un flux de sortie vers le fichier image
            val outputStream = FileOutputStream(imageFile)
            // On compresse et on écrit l'image Bitmap dans le fichier au format JPEG avec une qualité de 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            // Une fois terminé, on ferme le flux de sortie.
            outputStream.close()

            // On récupère l'URI du fichier image
            val uriPhoto = Uri.fromFile(imageFile)

            // On persiste le chemin de l'image dans la base de données Room.
            this.persistPathInRoomDatabase(uriPhoto.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun persistPathInRoomDatabase(imagePath: String) {
        val coursePhoto = CoursePhoto(courseId = course.id, photoUrl = imagePath)
        Log.wtf("URI PHOT: ", imagePath + " " + coursePhoto.courseId)
        // Logique de sauvegarde du chemin de l'image dans la base de données ROOM.
        viewModel.addPhotoToCourse(coursePhoto)
    }

    /**
     * Fonction permettant la suppression de la photo d'un cours.
     *
     * @param id Id de la ligne où se trouve la liaison entre le cours et la photo  supprimer.
     */
    private fun removePhoto(id: Int, uriString: String){
        // Supprimer la ligne dans room
        viewModel.removeCoursPhoto(id)

        // On supprime l'image stockée dans l'appareil
        val uri = Uri.parse(uriString)
        val fileToDelete = File(uri.path ?: "")
        if(fileToDelete.exists()) {
            fileToDelete.delete()
        }
    }
}

