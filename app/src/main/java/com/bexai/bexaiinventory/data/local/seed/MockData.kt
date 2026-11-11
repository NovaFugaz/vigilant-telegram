package com.bexai.bexaiinventory.data.local.seed

import com.bexai.bexaiinventory.data.local.entity.AlertaEntity
import com.bexai.bexaiinventory.data.local.entity.ConteoFisicoEntity
import com.bexai.bexaiinventory.data.local.entity.MetricasJornadaEntity
import com.bexai.bexaiinventory.data.local.entity.ProductoEntity
import com.bexai.bexaiinventory.data.local.entity.SolicitudReposicionEntity
import com.bexai.bexaiinventory.data.local.entity.TareaEntity
import com.bexai.bexaiinventory.domain.model.AccionTarea
import com.bexai.bexaiinventory.data.local.entity.Operador
import com.bexai.bexaiinventory.data.local.entity.Jornada
import com.bexai.bexaiinventory.domain.model.BalanceAnioVsMes
import com.bexai.bexaiinventory.domain.model.CapacidadCentro
import com.bexai.bexaiinventory.domain.model.ReporteGerencialSummary
import com.bexai.bexaiinventory.domain.model.ReporteItemTienda
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.domain.model.Turno

// me wa permitir dejar esto acá :3
// esta cuestión debería ser un componente para la evaluación, considerando lo útil que es en reducir código de otro lado, xd.


object MockData {

    val operadoresIniciales = listOf(
        Operador("OP-1001", "Rin Tohsaka",             "123456", Rol.TERRENO,  1, Turno.TARDE),
        Operador("OP-1002", "Gilgamesh",               "654321", Rol.BODEGA,   1, Turno.MANANA),
        Operador("OP-1003", "Bate galactico",          "234567", Rol.GERENCIA, 1, Turno.TARDE),
        Operador("OP-1004", "Emiya Shirou",            "111222", Rol.TERRENO,  1, Turno.MANANA),
        Operador("OP-1005", "Matou Sakura",            "333444", Rol.TERRENO,  2, Turno.MANANA),
        Operador("OP-1006", "Artoria Pendragon",       "555666", Rol.BODEGA,   2, Turno.TARDE),
        Operador("OP-1007", "Illyasviel von Einzbern", "777888", Rol.TERRENO,  3, Turno.TARDE),
        Operador("OP-1008", "Tohsaka Tokiomi",         "999000", Rol.GERENCIA, 4, Turno.MANANA)
    )

    private val conJornadaActiva = setOf("OP-1001", "OP-1004", "OP-1005", "OP-1006")

    val jornadasIniciales = operadoresIniciales
        .filter { it.codigo in conJornadaActiva }
        .map { Jornada(it.codigo, it.sucursalId, it.turno) }
    val productosIniciales = listOf(
        ProductoEntity(
            sku = "SKU-7704219",
            ean13 = "7801234567890",
            nombre = "Esmeril Angular 4 1/2\" 850W Dewalt",
            descripcion = "Herramientas eléctricas pesadas",
            stockTienda = 24,
            stockMinimo = 10,
            unidadMedida = "UN",
            valor = 148420,
            pasillo = "B-04",
            estante = "3",
            nivel = "2",
            zona = "Principal",
            sucursalId = 1
        ),
        ProductoEntity(
            sku = "SKU-8841920",
            ean13 = "7809482100412",
            nombre = "Taladro Percutor Inalámbrico 20V Dewalt",
            descripcion = "Motor Brushless Industrial",
            stockTienda = 3,
            stockMinimo = 8,
            unidadMedida = "UN",
            valor = 95000,
            pasillo = "A-12",
            estante = "1",
            nivel = "1",
            zona = "Principal",
            sucursalId = 1
        ),
        ProductoEntity(
            sku = "SKU-3361284",
            ean13 = "7805541299831",
            nombre = "Disco de Corte Metal 4 1/2\" Norton (x10)",
            descripcion = "Pack x10 unidades extra fino",
            stockTienda = 0,
            stockMinimo = 25,
            unidadMedida = "CJ",
            valor = 12500,
            pasillo = "B-02",
            estante = "2",
            nivel = "1",
            zona = "Principal",
            sucursalId = 1
        )
    )

    // Dentro de tu objeto MockData en data/local/seed/MockData.kt

    val alertasIniciales = listOf(
        AlertaEntity(
            id = "ALT-001",
            tituloAlerta = "QUIEBRE ACTIVO",
            sku = "SKU-3361284",
            ean13 = "7805541299831",
            nombreProducto = "Disco de Corte Metal 4 1/2\" Norton",
            stockActual = 0,
            stockRequerido = 25,
            ubicacionNombre = "Santiago Centro Express - Pasillo 04-B",
            severidadAlerta = "QUIEBRE_CRITICO",
            fechaIso = "2024-05-20",
            horaIso = "09:30:00"
        ),
        AlertaEntity(
            id = "ALT-002",
            tituloAlerta = "STOCK CRÍTICO INMINENTE",
            sku = "SKU-8841920",
            ean13 = "7809482100412",
            nombreProducto = "Taladro Percutor Inalámbrico 20V Dewalt",
            stockActual = 3,
            stockRequerido = 8,
            ubicacionNombre = "Bodega Central Pudahuel - Rack 12-C",
            severidadAlerta = "STOCK_CRITICO_INMINENTE",
            fechaIso = "2024-05-20",
            horaIso = "10:15:00"
        ),
        AlertaEntity(
            id = "ALT-003",
            tituloAlerta = "DESCALCE CONTEO FÍSICO",
            sku = "SKU-7704219",
            ean13 = "7801234567890",
            nombreProducto = "Esmeril Angular 4 1/2\" Dewalt",
            stockActual = 21,
            stockRequerido = 24,
            ubicacionNombre = "Bodega Central - Pasillo B-04",
            severidadAlerta = "DESCALCE_CONTEO",
            fechaIso = "2024-05-20",
            horaIso = "11:00:00"
        ),
        AlertaEntity(
            id = "ALT-004",
            tituloAlerta = "ADVERTENCIA PREVENTIVA",
            sku = "SKU-1029482",
            ean13 = "7801234599999",
            nombreProducto = "Guantes Nitrilo Industrial Talla L",
            stockActual = 6,
            stockRequerido = 15,
            ubicacionNombre = "Sucursal Viña del Mar",
            severidadAlerta = "ADVERTENCIA_PREVENTIVA",
            fechaIso = "2024-05-20",
            horaIso = "11:30:00"
        )
    )

    fun metricasIniciales(codigo: String) = when (codigo) {
        "OP-1001" -> MetricasJornadaEntity(codigo, 48, 14, 2, 6, 8)   // jornada avanzada, con discrepancias
        "OP-1004" -> MetricasJornadaEntity(codigo, 92, 7, 0, 7, 8)    // casi terminando, sin incongruencias
        "OP-1005" -> MetricasJornadaEntity(codigo, 15, 3, 1, 2, 9)    // recién avanzando
        "OP-1007" -> MetricasJornadaEntity(codigo, 0, 0, 0, 0, 6)     // turno recién iniciado
        else      -> MetricasJornadaEntity(codigo, 0, 0, 0, 0, 0)     // bodega / gerencia
    }

    private fun tarea(codigo: String, titulo: String, detalle: String, accion: AccionTarea) =
        TareaEntity(operadorCodigo = codigo, titulo = titulo, detalle = detalle, accion = accion)

    fun tareasIniciales(codigo: String): List<TareaEntity> = when (codigo) {
        "OP-1001" -> listOf(
            tarea(codigo, "Conteo Pasillo: C-03", "42 / 60 SKUs verificados", AccionTarea.CONTINUAR),
            tarea(codigo, "Recontar Lote: #B892", "Discrepancia detectada (-3 u.)", AccionTarea.REVISAR),
            tarea(codigo, "Recepción Pallet MP-104", "Muelle 2 · En espera de escaneo", AccionTarea.INICIAR)
        )
        "OP-1004" -> listOf(
            tarea(codigo, "Conteo Pasillo: B-04", "31 / 45 SKUs verificados", AccionTarea.CONTINUAR),
            tarea(codigo, "Revisar Lote: #D207", "7 SKUs pendientes de revisión", AccionTarea.REVISAR),
            tarea(codigo, "Recepción Pallet MP-210", "Muelle 1 · En espera de escaneo", AccionTarea.INICIAR)
        )
        "OP-1005" -> listOf(
            tarea(codigo, "Conteo Pasillo: A-02", "9 / 30 SKUs verificados", AccionTarea.CONTINUAR),
            tarea(codigo, "Recontar Lote: #F118", "Discrepancia detectada (-1 u.)", AccionTarea.REVISAR)
        )
        "OP-1007" -> listOf(
            tarea(codigo, "Conteo Pasillo: D-01", "0 / 38 SKUs verificados", AccionTarea.INICIAR),
            tarea(codigo, "Conteo Pasillo: D-02", "0 / 52 SKUs verificados", AccionTarea.INICIAR),
            tarea(codigo, "Recepción Pallet MP-305", "Muelle 3 · En espera de escaneo", AccionTarea.INICIAR)
        )
        else -> emptyList()   // bodega / gerencia no tienen tareas de terreno
    }

    val conteosIniciales = listOf(
        ConteoFisicoEntity(
            id = 1,
            sku = "SKU-7704219",
            stockTeorico = 24,
            conteoFisico = 21,
            motivo = "Merma / Daño",
            fechaIso = "2024-05-20",
            sucursalId = 1
        ),
        ConteoFisicoEntity(
            id = 2,
            sku = "SKU-8841920",
            stockTeorico = 8,
            conteoFisico = 3,
            motivo = "Error previo",
            fechaIso = "2024-05-20",
            sucursalId = 1
        ),
        ConteoFisicoEntity(
            id = 3,
            sku = "SKU-3361284",
            stockTeorico = 25,
            conteoFisico = 20,
            motivo = "Traspaso no registrado",
            fechaIso = "2024-05-19",
            sucursalId = 1
        )
    )

    val solicitudesIniciales = listOf(
        SolicitudReposicionEntity(
            id = 1,
            sku = "SKU-3361284",
            nombreProducto = "Disco de Corte Metal 4 1/2\" Norton (x10)",
            cantidad = 30,
            destino = "Bodega Central",
            prioridad = "Urgente",
            observacion = "Stock en quiebre total en bodega local",
            estado = "Pendiente",
            fechaIso = "2024-05-20",
            sucursalId = 1
        ),
        SolicitudReposicionEntity(
            id = 2,
            sku = "SKU-8841920",
            nombreProducto = "Taladro Percutor Inalámbrico 20V Dewalt",
            cantidad = 15,
            destino = "Proveedor Directo",
            prioridad = "Normal",
            observacion = "Stock actual (3 u.) bajo stock mínimo (8 u.)",
            estado = "En Tránsito",
            fechaIso = "2024-05-19",
            sucursalId = 1
        )
    )

    // Datos iniciales de Gerencia
    val summaryGerencialInicial = ReporteGerencialSummary(
        valorStockTotal = 148420000.0,
        capacidadStockUnidades = 20000,
        unidadesRedTotales = 34850,
        capacidadRedTotales = 41900,
        sucursalesActivas = 4,
        porcentajeOcupacionRed = 83,
        porcentajeCuadratura = 96.8,
        variacionCuadratura = 1.8,
        metaCuadratura = 95.0,
        alertasCriticasCount = 5,
        alertasQuiebresCount = 2
    )

    val capacidadesCentrosIniciales = listOf(
        CapacidadCentro(1, "CD Pudahuel (Hub Central)", 14800, 16000, 92, "#EA580C"),
        CapacidadCentro(2, "Santiago Centro Express", 8120, 10000, 81, "#16A34A"),
        CapacidadCentro(3, "Sucursal Viña del Mar", 6930, 8500, 81, "#16A34A"),
        CapacidadCentro(4, "Sucursal Concepción Sur", 5000, 7400, 67, "#64748B")
    )

    val reporteTiendasIniciales = listOf(
        ReporteItemTienda(
            sucursalId = 1,
            nombreTienda = "CD Pudahuel (Hub Central)",
            valorStock = 68400000.0,
            unidadesStock = 14800,
            valorMermas = 1250000.0,
            cantidadMermas = 18,
            porcentajeDescalce = 1.4,
            montoDescalce = 950000.0,
            quiebresCount = 0
        ),
        ReporteItemTienda(
            sucursalId = 2,
            nombreTienda = "Santiago Centro Express",
            valorStock = 38500000.0,
            unidadesStock = 8120,
            valorMermas = 890000.0,
            cantidadMermas = 12,
            porcentajeDescalce = 3.2,
            montoDescalce = 1230000.0,
            quiebresCount = 1
        ),
        ReporteItemTienda(
            sucursalId = 3,
            nombreTienda = "Sucursal Viña del Mar",
            valorStock = 26200000.0,
            unidadesStock = 6930,
            valorMermas = 450000.0,
            cantidadMermas = 6,
            porcentajeDescalce = 2.1,
            montoDescalce = 550000.0,
            quiebresCount = 1
        ),
        ReporteItemTienda(
            sucursalId = 4,
            nombreTienda = "Sucursal Concepción Sur",
            valorStock = 15320000.0,
            unidadesStock = 5000,
            valorMermas = 310000.0,
            cantidadMermas = 4,
            porcentajeDescalce = 1.8,
            montoDescalce = 275000.0,
            quiebresCount = 0
        )
    )

    val balanceAnioVsMesInicial = BalanceAnioVsMes(
        mesActualValorStock = 148420000.0,
        mesAnteriorValorStock = 139500000.0,
        anioActualValorStock = 1620000000.0,
        variacionMesPorcentaje = 6.39,
        variacionAnioPorcentaje = 12.4
    )

    val sucursalesGerencia = listOf(
        "Todas las Sucursales (4 activas)",
        "CD Pudahuel (Hub Central)",
        "Santiago Centro Express",
        "Sucursal Viña del Mar",
        "Sucursal Concepción Sur"
    )

}