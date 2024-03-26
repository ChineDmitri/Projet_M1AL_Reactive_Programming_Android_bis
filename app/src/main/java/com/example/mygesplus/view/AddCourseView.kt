package com.example.mygesplus.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygesplus.viewmodel.AddCourseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddCourseView(
    addCourseViewModel: AddCourseViewModel
){

    val inputNom = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf(Calendar.getInstance()) }
    val heureDebut = remember { mutableStateOf(Calendar.getInstance()) }
    val heureFin = remember { mutableStateOf(Calendar.getInstance()) }
    val isPresentiel = remember{ mutableStateOf(false) }
    val errorText = remember{ mutableStateOf("") }
    val shouldClose = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val inputDateText = remember { mutableStateOf(formatDate(selectedDate.value)) }
    val inputStartTimeText = remember { mutableStateOf(formatTime(heureDebut.value)) }
    val inputEndTimeText = remember { mutableStateOf(formatTime(heureFin.value)) }


    fun showDatePicker(){
        val datePicker = DatePickerDialog(
            context,
            {_, year, month, dayOfMonth ->
                selectedDate.value.set(year,month,dayOfMonth)
                // Update the text whenever a new date is chosen
                inputDateText.value = formatDate(selectedDate.value)
            },
            selectedDate.value.get(Calendar.YEAR),
            selectedDate.value.get(Calendar.MONTH),
            selectedDate.value.get(Calendar.DAY_OF_MONTH)
        )


    // Set the date format to dd/MM/yyyy
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        datePicker.datePicker.maxDate = System.currentTimeMillis() // Set maximum date
        datePicker.setTitle("Select Date")
        datePicker.show()
        datePicker.show()
    }

    fun showTimePicker(isStartTime: Boolean){
        val timePicker = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                if (isStartTime) {
                    heureDebut.value.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    heureDebut.value.set(Calendar.MINUTE, minute)
                    inputStartTimeText.value = formatTime(heureDebut.value)
                } else {
                    heureFin.value.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    heureFin.value.set(Calendar.MINUTE, minute)
                    inputEndTimeText.value = formatTime(heureFin.value)
                }
            },
            if (isStartTime) heureDebut.value.get(Calendar.HOUR_OF_DAY) else heureFin.value.get(Calendar.HOUR_OF_DAY),
            if (isStartTime) heureDebut.value.get(Calendar.MINUTE) else heureFin.value.get(Calendar.MINUTE),
            true
        )

        timePicker.show()
    }

    // Callback function to handle successful submission
    val onSubmissionSuccess: () -> Unit = {
        shouldClose.value = true // Set the shouldClose flag to true
    }

    // If shouldClose is true, finish the activity
    if (shouldClose.value) {
        // Finish the activity
        (context as? Activity)?.finish()
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ajout d'un cours", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = inputNom.value,
                onValueChange = { inputNom.value = it },
                label = { Text("Nom du cours")},
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                label = { Text("Description") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showDatePicker() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.White)
            ) {
                Text(
                    text = "Choisir la date: ${inputDateText.value}",
                    style = TextStyle(color = Color.White)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showTimePicker(true) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.White)
            ) {
                Text(
                    text = "Heure de début: ${inputStartTimeText.value}",
                    style = TextStyle(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showTimePicker(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.White)
            ) {
                Text(
                    text = "Heure de fin: ${inputEndTimeText.value}",
                    style = TextStyle(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Présentiel :")
            Switch(
                checked = isPresentiel.value,
                onCheckedChange = {isPresentiel.value = it},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (addCourseViewModel.isValidForm(inputNom.value, inputDateText.value, inputStartTimeText.value, inputEndTimeText.value, description.value)) {
                        addCourseViewModel.submitToDatabase(
                            inputNom.value,
                            inputDateText.value,
                            inputStartTimeText.value,
                            inputEndTimeText.value,
                            description.value,
                            isPresentiel.value,
                            context,
                            onSuccess = onSubmissionSuccess
                        )
                        errorText.value = ""
                    } else {
                        errorText.value = "Veuillez remplir tous les champs"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.White
                )) {
                    Text(
                        text = "Soumettre",
                        style = TextStyle(color = Color.White)
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorText.value, color = MaterialTheme.colorScheme.error)
        }
    }
}

fun formatDate(calendar: Calendar): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calendar.time)
}

fun formatTime(calendar: Calendar): String {
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return String.format("%02d:%02d", hour, minute)
}
