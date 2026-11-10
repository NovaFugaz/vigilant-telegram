package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.domain.model.Producto
import com.bexai.bexaiinventory.presentation.ui.components.NuevaSolicitudDialog
import com.bexai.bexaiinventory.presentation.viewmodel.SolicitudReposicionViewModel

@Composable
fun SolicitudReposicionScreen(
    viewModel: SolicitudReposicionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var productoSeleccionadoDialogo by remember { mutableStateOf<Producto?>(null) }
    var esAlertaManualDialogo by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.mensajeExito) {
        uiState.mensajeExito?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header con botones de acción principales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        productoSeleccionadoDialogo = null
                        esAlertaManualDialogo = false
                        mostrarDialogo = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Solicitar Reposición", fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        productoSeleccionadoDialogo = null
                        esAlertaManualDialogo = true
                        mostrarDialogo = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Alertar Quiebre", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 1: Productos bajo Stock Mínimo
            if (uiState.productosBajoMinimo.isNotEmpty()) {
                Text(
                    text = "Productos Bajo Stock Mínimo (${uiState.productosBajoMinimo.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.productosBajoMinimo, key = { it.sku }) { prod ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = prod.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        text = "Stock: ${prod.stockTienda} / Mín: ${prod.stockMinimo} u.",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                Button(
                                    onClick = {
                                        productoSeleccionadoDialogo = prod
                                        esAlertaManualDialogo = false
                                        mostrarDialogo = true
                                    },
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("Solicitar", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Sección 2: Historial de Solicitudes Emitidas
            Text(
                text = "Historial de Solicitudes Emitidas",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.cargando) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.solicitudes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay solicitudes de reposición registradas.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.solicitudes, key = { it.id }) { sol ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(sol.sku, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                                    Surface(
                                        color = when (sol.estado) {
                                            "Aprobada" -> Color(0xFFDCFCE7)
                                            "En Tránsito" -> Color(0xFFDBEAFE)
                                            else -> Color(0xFFFFEDD5)
                                        },
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text(
                                            text = sol.estado,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = when (sol.estado) {
                                                "Aprobada" -> Color(0xFF166534)
                                                "En Tránsito" -> Color(0xFF1E40AF)
                                                else -> Color(0xFF9A3412)
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(sol.nombreProducto, fontWeight = FontWeight.Bold, fontSize = 14.sp)

                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Cant. Solicitada: ${sol.cantidad} u.", fontSize = 12.sp)
                                    Text("Destino: ${sol.destino}", fontSize = 12.sp, color = Color.Gray)
                                }

                                if (sol.observacion.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Obs: ${sol.observacion}", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo && uiState.todosLosProductos.isNotEmpty()) {
        NuevaSolicitudDialog(
            productos = uiState.todosLosProductos,
            productoInicial = productoSeleccionadoDialogo,
            esAlertaManual = esAlertaManualDialogo,
            onDismiss = { mostrarDialogo = false },
            onConfirmarSolicitud = { sku, nombre, cantidad, destino, prioridad, observacion ->
                viewModel.emitirSolicitud(sku, nombre, cantidad, destino, prioridad, observacion)
            },
            onConfirmarAlerta = { sku, nombre, stockActual, stockRequerido, ubicacion ->
                viewModel.emitirAlertaQuiebreInminente(sku, nombre, stockActual, stockRequerido, ubicacion)
            }
        )
    }
}
