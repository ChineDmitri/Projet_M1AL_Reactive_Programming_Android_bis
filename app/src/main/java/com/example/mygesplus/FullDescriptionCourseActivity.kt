package com.example.mygesplus

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mygesplus.model.Course
import com.example.mygesplus.view.FullCourseDescriptionView
import com.google.gson.Gson
import java.io.IOException


class FullDescriptionCourseActivity : ComponentActivity() {

    // Gestion de la photo

    val REQUEST_IMAGE_CAPTURE = 1
    var photoDuCours by mutableStateOf<Bitmap?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val str = intent.getStringExtra("cours")
        Log.wtf("courseSelected str", str)

        val course = Gson().fromJson(str, Course::class.java)
        Log.wtf("courseSelected class", course.description)

        setContent {
            FullCourseDescriptionView(
                course,
                this@FullDescriptionCourseActivity::dispatchTakePictureIntent,
                photoDuCours
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

            // Quand l'image apparaît, le bouton devient invisible.
            // on pourrait le désactiver si on avait + de place.

            // Sauvegarde de l'image dans le stockage interne de l'appareil
            saveBitmapImage(photoDuCours!!)
        }
    }

    private fun saveBitmapImage(bitmap: Bitmap) {
        val timeStamp = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, timeStamp / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, timeStamp)
        }

        var imageFilePath: String? = null

        // Version supérieure ou égale à la version Q (Android 10)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + getString(R.string.app_name))
            values.put(MediaStore.Images.Media.IS_PENDING, true)

            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let { imageUri ->
                try {
                    contentResolver.openOutputStream(imageUri)?.use{ outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(imageUri, values, null, null)

                    imageFilePath = getImageFilePathFromUri(imageUri)

                    persistPathInRoomDatabase(imageFilePath!!)

                } catch (e: IOException){
                    Toast.makeText(this, "Une erreur est survenue lors de la sauvegarde de l'image sur votre appareil.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "La version d'Android que votre appareil utilise ne permet pas l'enregistrement de l'image...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun persistPathInRoomDatabase(imagePath: String) {
        // Logique de sauvegarde du chemin de l'image dans la base de données ROOM.
    }

    private fun getImageFilePathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if(cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}

