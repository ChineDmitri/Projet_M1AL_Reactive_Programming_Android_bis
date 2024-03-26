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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygesplus.FullDescriptionCourseActivity
import com.example.mygesplus.model.Course
import com.google.gson.Gson

@Composable
fun CourseItemView(
    course: Course,
    /*    onItemClick: () -> Unit*/
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(15.dp)
            .clickable {
                navigateToFullDescriptionCourseActivity(context, course)
            },
        shape = RoundedCornerShape(25.dp,0.dp,25.dp,25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {

        Row(
            modifier = Modifier
                .background(Color(146, 250, 61, 255))
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(120.dp)
                    .background(Color(146, 250, 61, 255)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            )
            {
                Text(text = course.heureDebut,
                    style = TextStyle(color = Color.Black, fontSize = 18.sp))
                Text(text = course.heureFin,
                    style = TextStyle(color = Color.Black, fontSize = 18.sp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(120.dp)
                    .background(Color(73, 158, 4, 255), RoundedCornerShape(25.dp, 0.dp, 0.dp, 0.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            )

            {
                Text(text = course.nom,
                    style = TextStyle(color = Color.White, fontSize = 22.sp))
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
        )
        {
            Box{
                Column(
                    modifier = Modifier
                        .background(Color(73, 158, 4, 255))
                        .fillMaxWidth(1f)
                        .fillMaxHeight(),
                )
                {
                }
                Column(
                    modifier = Modifier
                        .background(Color(146, 250, 61, 255))
                        .fillMaxWidth(0.35f)
                        .fillMaxHeight(),
                )
                {
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(249, 25, 21, 255), RoundedCornerShape(6.dp, 6.dp, 0.dp, 0.dp)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,

                )
                {
                    Text(text = course.isPresentiel.toString(),
                        style = TextStyle(color = Color.White, fontSize = 18.sp))
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