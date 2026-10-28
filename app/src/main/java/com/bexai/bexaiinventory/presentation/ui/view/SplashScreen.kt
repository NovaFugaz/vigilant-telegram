package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bexai.bexaiinventory.presentation.navigation.Rutas
import com.bexai.bexaiinventory.presentation.ui.theme.fonditoSplash
import com.bexai.bexaiinventory.presentation.ui.theme.muchoTexto
import kotlinx.coroutines.delay

@Composable
fun splashScreen(navController: NavController){

    LaunchedEffect(key1 = true) {
        delay(4500)
        navController.navigate(Rutas.LOGIN){
            popUpTo(route = Rutas.SPLASH) { inclusive = true }
        }
    }

    Box(modifier = Modifier.
    fillMaxSize().
    background(color = fonditoSplash),
        contentAlignment = Alignment.Center){

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "BEXAI",
                fontSize = 50.sp,
                color = muchoTexto,
                fontWeight = FontWeight.ExtraBold)

            Text(
                text = "SISTEMA DE INVENTARIO",
                fontSize = 15.sp,
                color = muchoTexto,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(4.dp)
            )
            CircularProgressIndicator(
                color = muchoTexto,
                modifier = Modifier.padding(32.dp)
            )

            Text(
                text = "cargando...",
                fontSize = 10.sp,
                color = muchoTexto,
                fontWeight = FontWeight.ExtraLight
            )
            Text(text = "version 1.0",
                fontWeight = FontWeight.ExtraLight,
                fontSize = 9.sp,
                color = muchoTexto)
        }
    }
}