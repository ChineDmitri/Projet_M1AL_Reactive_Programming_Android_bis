package com.example.mygesplus.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mygesplus.ui.theme.OfflineRedLight

@Composable
fun ConnectivityStatusBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        color = OfflineRedLight,
        shadowElevation = 4.dp
    ) {
        Text(
            text = "Pas de connexion Internet, les cours ne sont pas à jours !",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}