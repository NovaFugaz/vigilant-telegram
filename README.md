# BexAI Inventario

Aplicación móvil para Android desarrollada en **Kotlin** y **Jetpack Compose** orientada a la gestión de inventarios y control de acceso de operadores mediante autenticación por código y PIN, construida siguiendo principios de arquitectura moderna (MVVM), como parte del curso DSY1105 de DUOC 2026.

---

## Características Principales

- **Por añadir**
- **Los contenidos de este readme son modificables en tanto todavía estamos trabajando en el proyecto**

---

## Arquitectura del Proyecto

El proyecto sigue una estructura modular orientada a capas (MVVM + Repository Pattern):

```text
com.bexai.bexaiinventory/
│
├── data/
│   ├── local/          # Base de datos Room, Daos y Entidades (Operador, etc.)
│   ├── remote/         # DTOs y servicios de red (API)
│   └── repository/     # Implementación de repositorios de datos
│
├── domain/
│   ├── model/          # Modelos de dominio y Enums (Rol, LoginModel)
│   ├── repository/     # Interfaces de repositorios
│   └── usecase/        # Casos de uso de la aplicación
│
├── presentation/
│   ├── navigation/     # Configuración de navegación y rutas (Screen, AppNavigation)
│   ├── ui/
│   │   ├── components/ # Componentes reutilizables (Buttons, FormTextField, RoleOptionCard)
│   │   ├── screen/     # Pantallas principales (LoginScreen, MainScreen, UnderConstructionScreen)
│   │   └── theme/      # Tema, colores y tipografía Material 3
│   └── viewmodel/      # ViewModels (LoginViewModel con StateFlow)
│
└── di/                 # Módulos de inyección de dependencias (DataBaseModule)
```

---

## Tecnologías y Librerías

- **Lenguaje**: Kotlin
- **UI Toolkit**: Jetpack Compose & Material 3
- **Arquitectura**: ViewModel, StateFlow, Coroutines
- **Base de Datos**: Room (con KSP)
- **Inyección de Dependencias**: Hilt
- **Navegación**: Navigation Compose
- **Build System**: Gradle Kotlin DSL (`build.gradle.kts`)

---

## Requisitos del Sistema

- **Android Studio**: Ladybug / Koala o superior (soporte para AGP 8+ / KSP).
- **SDK Mínimo**: Android 7.0 (API 24)
- **SDK Objetivo / Compile SDK**: Android 15 / 16 (API 37)
- **JDK**: Versión 11 o superior.

---

## Compilación y Ejecución

1. Clona el repositorio o abre la carpeta del proyecto en Android Studio.
2. Espera a que Gradle sincronice las dependencias (`Sync Project with Gradle Files`).
3. Ejecuta la aplicación en un emulador o dispositivo físico con Android 7.0 (API 24) o superior.
