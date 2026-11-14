# Política de Testing - BexAI Inventario

Esta política define los estándares, herramientas y directrices de pruebas automatizadas adaptadas específicamente a la arquitectura de **BexAI Inventario** (Jetpack Compose, ViewModels con StateFlow, Room Database, Hilt y Repositorios).

---

## 1. Estructura y Organización de Pruebas
Los archivos de test deben reflejar exactamente la estructura de paquetes del código fuente en `src/test/java/` (para unitarias y de arquitectura) y `src/androidTest/java/` (para instrumentadas/UI).

* **Convención de Nombres:** `<Componente>Test.kt`
  * *Ejemplo:* `GerenciaViewModel.kt` $\rightarrow$ `GerenciaViewModelTest.kt`
  * *Ejemplo:* `ProductoDao.kt` $\rightarrow$ `ProductoDaoTest.kt`

---

## 2. Stack Tecnológico de Testing para BexAI Inventario
Para mantener la ejecución rápida, moderna e idiomática en Kotlin, utilizaremos el siguiente stack (alineado con los estándares universitarios más actuales):

* **Framework de Pruebas & Aserciones:**
  * **JUnit 5 (JUnit Jupiter):** Motor de ejecución moderno con soporte para pruebas jerárquicas y parametrizadas.
  * **Kotest (Assertions):** Librería de aserciones fluidas en Kotlin (`shouldBe`, `shouldNotBe`, `shouldThrow`) que otorga máxima legibilidad a los tests.
* **Mocking & Concurrencia:**
  * **MockK:** Librería de mocking estándar y 100% idiomática para Kotlin.
  * **kotlinx-coroutines-test (`runTest`):** Control preciso de tiempos y despachadores virtuales para corrutinas.
  * **Turbine:** Extensión altamente recomendada para la validación de `StateFlow` y `SharedFlow` en ViewModels.
* **Pruebas de Componentes UI (Jetpack Compose):**
  * **Compose UI Test (`createComposeRule`):** Validación de pantallas y componentes (como `GerenciaKpiCard`, `ProductoItemCard`, etc.) combinado con **Robolectric** para ejecutarse directamente en la JVM sin emulador físico.
* **Capa de Datos:**
  * **Room In-Memory Database:** Pruebas unitarias de DAOs y consultas SQL.

---

## 3. Cobertura y Criterios de Validación

### A. ViewModels (`presentation/viewmodel/`)
Cada ViewModel clave (`GerenciaViewModel`, `LoginViewModel`, `ScannerViewModel`, `BodegaStockViewModel`, etc.) debe verificar:
1. **Estado Inicial:** Validar que el `UiState` arranca con los valores por defecto esperados.
2. **Flujo de Éxito:** Simular respuestas exitosas de los repositorios y verificar que los estados (`StateFlow`) reflejan los datos correctamente.
3. **Flujo de Error / Excepciones:** Simular fallos de red o base de datos y comprobar la gestión de errores (mensajes de error, estados de carga desactivados).

### B. Capa de Datos y Room (`data/local/dao/`)
* Probar todas las consultas personalizadas (`@Query`), transacciones y operaciones CRUD en los DAOs utilizando una instancia en memoria de `AppDataBase`.

### C. Componentes UI / Pantallas (`presentation/ui/`)
* Validar interacciones de usuario críticas (clicks en botones de escaneo, selección de sucursal, filtrado de stock).
* Verificar la correcta visualización de estados vacíos, de carga y con datos.

---

## 4. Guías de Buenas Prácticas & Estilos de Kotest
Todo método de prueba debe estructurarse claramente bajo las siguientes directrices:

### A. Estilos de Kotest según el Componente
* **`StringSpec` (Estándar Principal):** Usado por defecto para la gran mayoría de pruebas unitarias de **ViewModels**, **Repositorios** y **DAOs de Room**. Proporciona un formato plano y limpio que evita anidaciones innecesarias.
  ```kotlin
  "cuando se escanea un código válido, se actualiza el stock" { ... }
  ```
* **`BehaviorSpec` (Flujos Complejos / BDD):** Usado en módulos críticos con reglas de negocio logísticas estrictas (ej. *Gestión de Jornadas de Conteo* o *Diferencias de Bodega*), aplicando la estructura `given` / `when` / `then`.

### B. Patrón de Ejecución
* **`given`**: Configuración de mocks, datos iniciales y preparación del estado.
* **`when`**: Ejecución de la acción del usuario o llamada al método del ViewModel/Repositorio.
* **`then`**: Aserciones expresivas usando las aserciones fluidas de **Kotest** (ej. `resultado shouldBe esperado`) y verificación de llamadas con **MockK**.

---

## 5. Verificación Local Antes de Pushear
Antes de integrar o proponer cambios mayores en el repositorio, se recomienda ejecutar localmente:
```bash
./gradlew testDebugUnitTest
```
