package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bexai.bexaiinventory.domain.model.Producto
import com.bexai.bexaiinventory.domain.model.SyncInfo
import com.bexai.bexaiinventory.domain.model.Ubicacion
import com.bexai.bexaiinventory.domain.model.UnidadMedida
import com.bexai.bexaiinventory.presentation.ui.components.BranchSelectorCard
import com.bexai.bexaiinventory.presentation.ui.components.StockItemCard
import com.bexai.bexaiinventory.presentation.ui.components.SyncStatusCard
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun StockCatalogScreen(){
    // Estado de la pantalla
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var syncInfo by remember {
        // Nota Mental: Ver esto cuando ya tenga más lógica.
        mutableStateOf(SyncInfo(ultimaSincronizacion = LocalDateTime(2026, 10, 31, 10, 30), isSincronizando = false, mensajeEstado = "OK"))
    }

    // Acá hay que cargar una lista de productos.
    // Esto lo tengo que sacar de acá.
    // Mañana lo hago, istfg.
    val listaProductos = remember {
        listOf(
            Producto(
                sku = "SKU-7704219",
                ean13 = "7801234567890",
                nombre = "Esmeril Angular 4 1/2\" 850W Dewalt",
                descripcion = "Herramientas eléctricas pesadas",
                stockTienda = 24,
                stockMinimo = 10,
                unidadMedida = UnidadMedida.Unidades,
                valor = 148420,
                Ubicacion = Ubicacion("Principal", "B-04", "3", "2")
            ),
            Producto(
                sku = "SKU-8841920",
                ean13 = "7809482100412",
                nombre = "Taladro Percutor Inalámbrico 20V Dewalt",
                descripcion = "Motor Brushless Industrial",
                stockTienda = 3,
                stockMinimo = 8,
                unidadMedida = UnidadMedida.Unidades,
                valor = 95000,
                Ubicacion = Ubicacion("Principal", "A-12", "1", "1")
            ),
            Producto(
                sku = "SKU-3361284",
                ean13 = "7805541299831",
                nombre = "Disco de Corte Metal 4 1/2\" Norton (x10)",
                descripcion = "Pack x10 unidades extra fino",
                stockTienda = 0,
                stockMinimo = 25,
                unidadMedida = UnidadMedida.Cajas,
                valor = 12500,
                Ubicacion = Ubicacion("Principal", "B-02", "2", "1")
            )
        )
    }

    // 3. Lógica de Filtrado (Búsqueda por texto + Filtro por estado: Todos, Crítico, Quiebre)
    val productosFiltrados = listaProductos.filter { producto ->
        val coincideBusqueda = producto.nombre.contains(searchQuery, ignoreCase = true) ||
                producto.sku.contains(searchQuery, ignoreCase = true) ||
                producto.ean13.contains(searchQuery, ignoreCase = true)

        val coincideFiltro = when (selectedFilter) {
            "Crítico" -> producto.stockTienda in 1..producto.stockMinimo
            "Quiebre" -> producto.stockTienda == 0
            else -> true // "Todos"
        }

        coincideBusqueda && coincideFiltro
    }

    // 4. Estructura Visual (UI)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Selector de Sucursal
        BranchSelectorCard(sucursalNombre = "Bodega Central Pudahuel") {
            // Acción al hacer clic en cambiar sucursal
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Barra de Búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar SKU, EAN o descripción") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { /* Acción escanear */ }) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                }
            },
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Chips de Filtro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todos", "Crítico", "Quiebre").forEach { filtro ->
                FilterChip(
                    selected = selectedFilter == filtro,
                    onClick = { selectedFilter = filtro },
                    label = { Text(filtro) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de Productos (LazyColumn usando tu componente modular StockItemCard)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(productosFiltrados) { producto ->
                StockItemCard(producto = producto) {
                    // Acción al hacer clic en la tarjeta (ej. Ver detalle o ajustar stock)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tarjeta de Sincronización SAP (Usando tu componente y función formatearTiempoTranscurrido)
        SyncStatusCard(
            ultimaSincronizacion = syncInfo.ultimaSincronizacion,
            estaSincronizando = syncInfo.isSincronizando
        ) {
            // Acción de actualizar sincronización simulada
            syncInfo = syncInfo.copy(
                isSincronizando = true,
                ultimaSincronizacion = now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
            )
        }
    }
}

