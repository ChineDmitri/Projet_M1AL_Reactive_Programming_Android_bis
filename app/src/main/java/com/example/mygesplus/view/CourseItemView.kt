package com.example.mygesplus.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mygesplus.FullDescriptionCourseActivity
import com.example.mygesplus.model.Course
import com.example.mygesplus.ui.theme.Blanc
import com.example.mygesplus.ui.theme.JaunePomme
import com.example.mygesplus.ui.theme.RougePomme
import com.example.mygesplus.ui.theme.VertClair
import com.google.gson.Gson

@Composable
fun CourseItemView(
    course: Course,
/*    onItemClick: () -> Unit*/
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(10.dp)
            .clickable {
                navigateToFullDescriptionCourseActivity(context, course)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(VertClair)
                .padding(25.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = course.heureDebut)
                    Text(text = course.heureFin)
                }
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = course.description)
                }
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = course.isPresentiel.toString())
                }
            }
        }
    }
}

private fun navigateToFullDescriptionCourseActivity(context: Context, course: Course) {
    val intent = Intent(context, FullDescriptionCourseActivity::class.java)
    intent.putExtra("cours", Gson().toJson(course))
    context.startActivity(intent)
}