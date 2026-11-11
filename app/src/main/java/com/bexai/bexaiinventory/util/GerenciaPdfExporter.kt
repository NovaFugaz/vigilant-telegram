package com.bexai.bexaiinventory.util

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.bexai.bexaiinventory.domain.model.BalanceAnioVsMes
import com.bexai.bexaiinventory.domain.model.CapacidadCentro
import com.bexai.bexaiinventory.domain.model.ReporteGerencialSummary
import com.bexai.bexaiinventory.domain.model.ReporteItemTienda
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GerenciaPdfExporter {

    fun exportarReporteGerencial(
        context: Context,
        tituloReporte: String,
        summary: ReporteGerencialSummary,
        tiendas: List<ReporteItemTienda>,
        capacidades: List<CapacidadCentro>,
        balance: BalanceAnioVsMes
    ): File? {
        val pdfDocument = PdfDocument()

        // Página estándar A4 (595 x 842 pt)
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paintText = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            isAntiAlias = true
        }

        val paintTitle = Paint().apply {
            color = Color.rgb(234, 88, 12) // NaranjaIntenso #EA580C
            textSize = 20f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintHeader = Paint().apply {
            color = Color.rgb(12, 36, 72) // fonditoSplash #0C2448
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintTableHead = Paint().apply {
            color = Color.rgb(12, 36, 72)
            textSize = 10f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintSub = Paint().apply {
            color = Color.DKGRAY
            textSize = 10f
            isAntiAlias = true
        }

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fechaActual = dateFormat.format(Date())

        var y = 40f

        // Encabezado del PDF
        canvas.drawText("BexAI Inventario - Tablero Gerencial", 30f, y, paintTitle)
        y += 20f
        canvas.drawText(tituloReporte, 30f, y, paintHeader)
        paintHeader.color = Color.DKGRAY
        canvas.drawText("Fecha de generación: $fechaActual", 380f, y, paintSub)
        y += 25f

        // Línea divisora
        val paintLine = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1.5f
        }
        canvas.drawLine(30f, y, 565f, y, paintLine)
        y += 25f

        // Sección 1: Resumen de Indicadores Clave (KPIs)
        canvas.drawText("1. RESUMEN EJECUTIVO KPI", 30f, y, paintHeader)
        y += 20f

        val valorFormatted = currencyFormat.format(summary.valorStockTotal)
        canvas.drawText("• Valor Total Stock: $valorFormatted", 40f, y, paintText)
        canvas.drawText("• Unidades Red: ${summary.unidadesRedTotales} / ${summary.capacidadRedTotales} u. (${summary.porcentajeOcupacionRed}% ocupado)", 40f, y + 16f, paintText)
        canvas.drawText("• Cuadratura General: ${summary.porcentajeCuadratura}% (Meta > ${summary.metaCuadratura}%)", 40f, y + 32f, paintText)
        canvas.drawText("• Alertas SKU: ${summary.alertasCriticasCount} Críticos, ${summary.alertasQuiebresCount} Quiebres activos", 40f, y + 48f, paintText)

        y += 75f
        canvas.drawLine(30f, y, 565f, y, paintLine)
        y += 20f

        // Sección 2: Desglose por Sucursal
        canvas.drawText("2. DESGLOSE DE VALOR DE STOCK, MERMAS Y DESCALCES POR SUCURSAL", 30f, y, paintHeader)
        y += 25f

        // Encabezado de la tabla
        canvas.drawText("Sucursal", 35f, y, paintTableHead)
        canvas.drawText("Valor Stock", 200f, y, paintTableHead)
        canvas.drawText("Mermas ($)", 310f, y, paintTableHead)
        canvas.drawText("Descalce (%)", 410f, y, paintTableHead)
        canvas.drawText("Descalce ($)", 490f, y, paintTableHead)
        y += 8f
        canvas.drawLine(30f, y, 565f, y, paintLine)
        y += 18f

        paintText.textSize = 10f
        tiendas.forEach { item ->
            canvas.drawText(item.nombreTienda.take(24), 35f, y, paintText)
            canvas.drawText(currencyFormat.format(item.valorStock), 200f, y, paintText)
            canvas.drawText(currencyFormat.format(item.valorMermas), 310f, y, paintText)
            canvas.drawText("${item.porcentajeDescalce}%", 410f, y, paintText)
            canvas.drawText(currencyFormat.format(item.montoDescalce), 490f, y, paintText)
            y += 18f
        }

        y += 15f
        canvas.drawLine(30f, y, 565f, y, paintLine)
        y += 20f

        // Sección 3: Balance Año vs Mes
        canvas.drawText("3. BALANCE COMPARATIVO AÑO VS MES", 30f, y, paintHeader)
        y += 20f
        canvas.drawText("• Mes Actual: ${currencyFormat.format(balance.mesActualValorStock)}", 40f, y, paintText)
        canvas.drawText("• Mes Anterior: ${currencyFormat.format(balance.mesAnteriorValorStock)} (Var: +${balance.variacionMesPorcentaje}%)", 40f, y + 16f, paintText)
        canvas.drawText("• Acumulado Año: ${currencyFormat.format(balance.anioActualValorStock)} (Var: +${balance.variacionAnioPorcentaje}%)", 40f, y + 32f, paintText)

        pdfDocument.finishPage(page)

        // Guardar archivo PDF en la carpeta de cache
        val fileName = "Reporte_Gerencial_${System.currentTimeMillis()}.pdf"
        val pdfFile = File(context.cacheDir, fileName)

        try {
            val fos = FileOutputStream(pdfFile)
            pdfDocument.writeTo(fos)
            pdfDocument.close()
            fos.close()
            return pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            return null
        }
    }

    fun abrirPdf(context: Context, file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: compartir el Intent
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Abrir o compartir PDF"))
        }
    }
}
