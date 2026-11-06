package com.bexai.bexaiinventory.presentation.ui.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.CameraPreview
import com.bexai.bexaiinventory.presentation.ui.components.NuevoProductoDialog
import com.bexai.bexaiinventory.presentation.ui.components.RectificarConteoDialog
import com.bexai.bexaiinventory.presentation.viewmodel.ScannerViewModel

// te pasaste nacchi, al menos ira bien grupalmente
@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Verificación de permisos
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Si es necesario pedir permisos, preparamos contrato.
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    // Lanzamos la petición al entrar a la pantalla en caso de que no hayan permisos.
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        Box(Modifier.fillMaxSize()) {
            // Llamamos al componente de preview de la cámara con escáner automático
            CameraPreview(
                onCodigoEscaneado = { codigoDetectado ->
                    viewModel.onCodigoEscaneado(codigoDetectado)
                }
            )

            // Interfaz de usuario (Overlay)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Alinee el código de barras o QR",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }

                // Tarjeta flotante inferior con información del producto o QR detectado
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val producto = uiState.productoEncontrado
                        if (producto != null) {
                            Text(
                                text =  "Captura Exitosa",
                                color = Color(0xFF166534),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "SKU: ${producto.sku} • Stock: ${producto.stockTienda} u. • Pasillo: ${producto.Ubicacion.pasillo}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else if (uiState.codigoEscaneado != null) {
                            Text(
                                text = "QR Detectado: ${uiState.codigoEscaneado}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Producto no registrado en sistema.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text(
                                text = "Esperando lectura de código QR...",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.abrirDialogoRectificar() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiState.productoEncontrado != null
                        ) {
                            Text("Rectificar conteo")
                        }
                    }
                }
            }

            // Diálogo emergente para registrar un nuevo producto cuando el QR no existe
            if (uiState.mostrarDialogoNuevo && uiState.codigoEscaneado != null) {
                NuevoProductoDialog(
                    codigoQr = uiState.codigoEscaneado!!,
                    onDismiss = { viewModel.cerrarDialogo() },
                    onGuardar = { nombre, descripcion, stock, pasillo ->
                        viewModel.guardarNuevoProducto(nombre, descripcion, stock, pasillo)
                    }
                )
            }

            if (uiState.mostrarDialogoRectificar && uiState.productoEncontrado != null) {
                RectificarConteoDialog(
                    producto = uiState.productoEncontrado!!,
                    onDismiss = { viewModel.cerrarDialogoRectificar() },
                    onConfirmarRectificacion = { nuevoStock, _ ->
                        viewModel.rectificarStockProducto(uiState.productoEncontrado!!.sku, nuevoStock)
                    }
                )
            }
        }
    }
}
