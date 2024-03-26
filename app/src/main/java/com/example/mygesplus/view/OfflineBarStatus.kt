package com.example.mygesplus.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*@Composable
fun ConnectivityStatusBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .wrapContentHeight(),
        color = OfflineRedLight,
        shadowElevation = 4.dp
    ) {
        Text(
            text = "Pas de connexion Internet, les cours ne sont pas à jours !",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}*/

@Composable
fun ConnectivityStatusBar(isConnected: Boolean) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = isConnected) {
        if (!isConnected) {
            snackbarHostState.showSnackbar(
                message = "Pas de connexion Internet, les cours ne sont pas à jour !",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(0.dp)
    )
}