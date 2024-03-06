package com.example.mygesplus.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mygesplus.model.Course
import com.example.mygesplus.ui.theme.CtxMain
import com.example.mygesplus.ui.theme.JaunePomme
import com.example.mygesplus.ui.theme.RougePomme
import com.example.mygesplus.ui.theme.VertClair

@Composable
fun FullCourseDescriptionView(
    course: Course
) {
    Box(modifier = Modifier
        .background(JaunePomme)
        .fillMaxWidth()
        .fillMaxHeight()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(0.dp,100.dp,0.dp,0.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(VertClair)
                .fillMaxWidth()
                .padding(50.dp,50.dp)
        )

        {
            Text(text = course.description + " " + course.date + " " + course.isPresentiel.toString())
        }
    }

    Box(modifier = Modifier
        .background(RougePomme)
        .fillMaxWidth()
        .fillMaxHeight(0.1f)
    )
}