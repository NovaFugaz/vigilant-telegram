package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.viewmodel.GerenciaViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GerenciaReportesScreen(
    viewModel: GerenciaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Título de la sección
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Reportes Ejecutivos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Generar Reportes PDF",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }

            Button(
                onClick = { viewModel.exportarTableroPdf(context) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Exportar Todo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 1: Valor de Stock Por Tienda y Total
        ReporteSeccionCard(
            titulo = "Valor de Stock por Tienda y Total",
            subtitulo = "Desglose por centro operativo",
            icono = Icons.Default.Assessment,
            colorIcono = Color(0xFF2563EB),
            onExportarPdf = { viewModel.exportarTableroPdf(context) }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Valor Stock Total Red:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(
                            text = currencyFormat.format(uiState.summary.valorStockTotal),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                uiState.tiendasReportes.forEach { tienda ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(tienda.nombreTienda, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("${"%,d".format(tienda.unidadesStock)} unidades", fontSize = 11.sp, color = Color.Gray)
                        }
                        Text(
                            currencyFormat.format(tienda.valorStock),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF1E293B)
                        )
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 2: Mermas y % / $ Descalces por Tienda
        ReporteSeccionCard(
            titulo = "2. Mermas y Descalces",
            subtitulo = "Monitoreo de mermas y descalces en conteos",
            icono = Icons.Default.Equalizer,
            colorIcono = Color(0xFFD97706),
            onExportarPdf = { viewModel.exportarTableroPdf(context) }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                uiState.tiendasReportes.forEach { tienda ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(tienda.nombreTienda, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Mermas ($)", fontSize = 10.sp, color = Color.Gray)
                                Text(currencyFormat.format(tienda.valorMermas), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                            }
                            Column {
                                Text("% Descalce", fontSize = 10.sp, color = Color.Gray)
                                Text("${tienda.porcentajeDescalce}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                            }
                            Column {
                                Text("$ Descalce", fontSize = 10.sp, color = Color.Gray)
                                Text(currencyFormat.format(tienda.montoDescalce), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            }
                        }
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 3: Balance Año vs Mes
        val b = uiState.balance
        ReporteSeccionCard(
            titulo = "3. Balance Comparativo Año / Mes",
            subtitulo = "Evolución mensual y acumulado anual del patrimonio en stock",
            icono = Icons.AutoMirrored.Filled.TrendingUp,
            colorIcono = Color(0xFF16A34A),
            onExportarPdf = { viewModel.exportarTableroPdf(context) }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Valor Mes Actual", fontSize = 11.sp, color = Color.Gray)
                        Text(currencyFormat.format(b.mesActualValorStock), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Variación vs Mes Anterior", fontSize = 11.sp, color = Color.Gray)
                        Text("+${b.variacionMesPorcentaje}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Acumulado Año", fontSize = 11.sp, color = Color.Gray)
                        Text(currencyFormat.format(b.anioActualValorStock), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Crecimiento Anual", fontSize = 11.sp, color = Color.Gray)
                        Text("+${b.variacionAnioPorcentaje}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 4: Reporte de Quiebres de Stock
        ReporteSeccionCard(
            titulo = "4. Quiebres y Alertas Criticas",
            subtitulo = "Productos por debajo del stock mínimo o en quiebre",
            icono = Icons.Default.Warning,
            colorIcono = Color(0xFFDC2626),
            onExportarPdf = { viewModel.exportarTableroPdf(context) }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Quiebres Totales Activos:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF991B1B))
                        Text("${uiState.summary.alertasQuiebresCount} SKUs en Quiebre", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF991B1B))
                    }
                }
            }
        }
    }
}

@Composable
fun ReporteSeccionCard(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    colorIcono: Color,
    onExportarPdf: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icono,
                        contentDescription = null,
                        tint = colorIcono,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                        Text(subtitulo, fontSize = 11.sp, color = Color(0xFF64748B))
                    }
                }

                Button(
                    onClick = onExportarPdf,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9), contentColor = Color(0xFF0F172A)),
                    shape = RoundedCornerShape(8.dp),
                    elevation = null
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "PDF",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFE2E8F0))
            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}
