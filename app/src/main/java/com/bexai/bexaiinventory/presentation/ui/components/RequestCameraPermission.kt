 package com.bexai.bexaiinventory.presentation.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCameraPermission(onPermissionGranted: @Composable () -> Unit){
    val context = LocalContext.current

    // Checar si hay permiso, pensando que puede cambiar.
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()){
            granted -> hasPermission = granted
        }

    // Si no tiene permiso, se pide
    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }
        if (hasPermission){
            onPermissionGranted()
        } else {
            // Deberíamos mostrar un botón que haga que se habilite la pantalla de pedir permiso?
            Text("Se requieren permisos de la cámara.")
        }
    }

