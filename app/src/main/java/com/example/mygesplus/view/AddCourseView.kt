package com.example.mygesplus.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygesplus.viewmodel.AddCourseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

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


    fun showDatePicker() {
        val selectedCalendar = selectedDate.value
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedCalendar.set(year, month, dayOfMonth)
                // Update the text whenever a new date is chosen
                inputDateText.value = formatDate(selectedCalendar)
            },
            selectedCalendar.get(Calendar.YEAR),
            selectedCalendar.get(Calendar.MONTH),
            selectedCalendar.get(Calendar.DAY_OF_MONTH)
        )

        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.YEAR, 10)
        datePicker.datePicker.maxDate = maxCalendar.timeInMillis

        datePicker.setTitle("Select Date")
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
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(232, 252, 128, 255)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Topbar pour la date
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
                    text = "Ajout d'un cours",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(color = Color.White, fontSize = 26.sp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
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
                    contentColor = Color.Black)
            ) {
                Text(
                    text = "Choisir la date: ${inputDateText.value}",
                    style = TextStyle(color = Color.Black)
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
                    style = TextStyle(color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showTimePicker(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(146, 250, 61, 255),
                    contentColor = Color.Black)
            ) {
                Text(
                    text = "Heure de fin: ${inputEndTimeText.value}",
                    style = TextStyle(color = Color.Black)
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
                    containerColor = Color(51, 109, 3, 255),
                    contentColor = Color.White
                )) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = "Soumettre",
                        style = TextStyle(color = Color.White)
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorText.value, color = MaterialTheme.colorScheme.error)
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
