# AlkeWallet - Digital Wallet

Una billetera digital moderna desarrollada en Kotlin nativo para Android siguiendo una arquitectura **MVC estricta**.

![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![SDK](https://img.shields.io/badge/SDK-24%2B-lightgrey.svg)
![Status](https://img.shields.io/badge/Status-Complete-success.svg)

AlkeWallet es una aplicación móvil diseñada para la gestión simulada de finanzas personales. Permite a los usuarios autenticarse, visualizar su saldo actual en tiempo real, revisar un historial detallado de movimientos, gestionar su perfil de usuario con cierre de sesión real y realizar operaciones financieras de envío e ingreso de dinero mediante una interfaz limpia y adaptada a **Edge-to-Edge** (Android 15+).

---

## Tabla de Contenidos
1. [Características Principales](#características-principales)
2. [Tecnología y Stack](#tecnología-y-stack)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Instalación y Setup](#instalación-y-setup)
5. [Guía de Uso / Flujo de la App](#guía-de-uso--flujo-de-la-app)
6. [Arquitectura y Decisiones Técnicas](#arquitectura-y-decisiones-técnicas)
7. [Pruebas Unitarias JVM](#pruebas-unitarias-jvm)
8. [Problemas Resueltos](#problemas-resueltos)
9. [Validación del Proyecto](#validación-del-proyecto)
10. [Estructura de Pantallas](#estructura-de-pantallas)
11. [Configuración del Proyecto](#configuración-del-proyecto)
12. [Autores y Contribuciones](#autores-y-contribuciones)

---

## Características Principales

- **Splash Screen:** Pantalla de bienvenida con branding institucional, transición automática de 2.5 segundos e integración de insets del sistema.
- **Autenticación en Memoria:** Registro de usuarios y login con validación de formato de correo (Regex) y prevención de cuentas duplicadas.
- **Dashboard en Tiempo Real (Home):** Muestra el saldo actualizado y la lista dinámica de transacciones (enviadas/recibidas). Soporta estados vacíos (*Empty State*) y actualización automática en `onResume()`.
- **Gestión de Perfil y Cierre de Sesión:** Visualización dinámica del usuario activo, avatar vectorial propio (`ic_avatar_placeholder`) y cierre de sesión real con limpieza completa del back stack (`FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK`).
- **Enviar Dinero:** Formulario interactivo con destinatario editable, monto con verificación de saldo insuficiente y campo opcional de notas (concatenadas como `"Nombre — Nota"` en el historial).
- **Ingresar Dinero:** Formulario interactivo para solicitud o recarga de fondos con límite máximo de **$5000.00 por transacción** y soporte para notas.

---

## Tecnología y Stack

| Componente | Tecnología | Descripción |
|------------|------------|-------------|
| **Lenguaje** | Kotlin 1.9+ | Lenguaje principal del proyecto (compatibilidad Java 11). |
| **Plataforma** | Android | Ejecución nativa (Min SDK 24 / Target 37 / Compile 37). |
| **Build System** | Gradle (Kotlin DSL) | AGP 9.3.1 y Version Catalogs (`libs.versions.toml`). |
| **Arquitectura** | MVC Estricto | Separación clara entre Modelo (JVM puro), Vista (XML) y Controlador (Activities/Fragments). |
| **UI Framework** | XML Layouts | Diseño mediante Android Views y Material Design 3. |
| **Binding** | View Binding | Interacción segura con layouts eliminando `findViewById`. |
| **Edge-to-Edge** | WindowInsetsCompat | Manejo programático de barras del sistema mediante `ViewCompat`. |

---

## Estructura del Proyecto

```text
AlkeWallet/
├── app/src/
│   ├── main/
│   │   ├── java/com/alkewallet/
│   │   │   ├── model/         # Modelo de dominio en Kotlin puro (AuthModel, WalletAccountModel, etc.)
│   │   │   ├── splash/        # SplashActivity (pantalla de bienvenida)
│   │   │   ├── auth/          # AuthActivity, LoginFragment y SignupFragment
│   │   │   ├── home/          # HomePageActivity y TransactionAdapter
│   │   │   ├── profile/       # ProfileActivity y gestión de sesión
│   │   │   └── transactions/  # SendMoneyActivity y RequestMoneyActivity
│   │   └── res/
│   │       ├── drawable/      # Vector Drawables (ic_avatar_placeholder, ic_send, ic_request, etc.)
│   │       ├── layout/        # Layouts XML con View Binding
│   │       └── values/        # Cadenas, colores (alke_*) y dimensiones
│   └── test/java/com/alkewallet/model/ # Pruebas unitarias JVM (AuthModelTest, WalletAccountModelTest)
└── build.gradle.kts
```

---

## Instalación y Setup

### Requisitos Previos
- Android Studio Hedgehog / Iguana / Ladybug o superior.
- JDK 17 / Java 11.
- Android SDK 24+ instalado.
- Git.

### Pasos para clonar y ejecutar
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/BrianSabio/Alke_Wallet.git
   ```
2. Abrir el proyecto en Android Studio.
3. Sincronizar los archivos de Gradle.
4. Conectar un dispositivo físico o emulador (API 24+).
5. Ejecutar la aplicación (`Shift + F10`).
6. Ejecutar las pruebas unitarias en JVM local:
   ```bash
   ./gradlew testDebugUnitTest
   ```

---

## Guía de Uso / Flujo de la App

**Splash (2.5s)** ➔ **Auth Selector / Login / Signup**
- **Registro:** Ingrese nombre, apellido, correo (debe ser un formato válido) y contraseña. Si el correo ya existe, el Modelo rechazará el registro.
- **Login:** Ingrese credenciales registradas. Al autenticarse con éxito, navega al Home y destruye la pantalla de acceso para evitar retrocesos indebidos.

**Home (Dashboard)** ➔ Opciones:
- **Perfil:** Muestra el nombre y correo del usuario activo, avatar vectorial de marca y opción de **Cerrar sesión** (limpia la sesión en `AuthModel` y retorna al acceso cerrando la pila de actividades).
- **Enviar Dinero:** Permite ingresar destinatario, monto y notas. Valida saldo disponible antes de efectuar el débito.
- **Ingresar Dinero:** Permite ingresar solicitante, monto (máximo $5000.00 por transacción) y notas.
- **Actualización:** El saldo y la lista de transacciones se refrescan automáticamente en `onResume()`.

---

## Arquitectura y Decisiones Técnicas

### Patrón MVC Estricto
1. **Capa Modelo (`com.alkewallet.model`):**
   - Desarrollada en **Kotlin puro** sin dependencias ni imports de `android.*`.
   - Contiene los singletons `AuthModel` y `WalletAccountModel`, los modelos inmutables `User` y `Transaction`, y el tipo sellado `WalletResult`.
   - Centraliza la totalidad de las reglas de negocio (validación de expresiones regulares para correos, límites de transferencia, control de saldos e historial).
2. **Capa Controlador (Activities / Fragments):**
   - Gestionan eventos de UI, leen entradas de usuario, invocan el Modelo y presentan el resultado (`WalletResult.Success` o `WalletResult.Error`) vía componentes visuales o Toasts.
3. **Capa Vista (Layouts XML):**
   - Vistas pasivas en XML conectadas mediante **View Binding**.

### Manejo de Edge-to-Edge (WindowInsetsCompat)
Dado que Android 15+ (`targetSdk 37`) fuerza el modo Edge-to-Edge por defecto, se implementó el manejo programático de insets en el método `onCreate()` de las 6 Activities del proyecto:

```kotlin
private fun setupWindowInsets() {
    val initialLeft = binding.root.paddingLeft
    val initialTop = binding.root.paddingTop
    val initialRight = binding.root.paddingRight
    val initialBottom = binding.root.paddingBottom

    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        binding.root.setPadding(
            initialLeft + insets.left,
            initialTop + insets.top,
            initialRight + insets.right,
            initialBottom + insets.bottom
        )
        windowInsets
    }
}
```

---

## Pruebas Unitarias JVM

Se cuenta con una suite de **13 pruebas unitarias de Modelo** en la carpeta `src/test`, ejecutables en la JVM local sin necesidad de emulador ni instrumentación Android:

* **`AuthModelTest` (7 tests):**
  - Registro de nuevo usuario.
  - Rechazo de correos duplicados.
  - Login exitoso y asignación de `currentUser`.
  - Error en login por contraseña incorrecta.
  - Cierre de sesión y limpieza de estado.
  - Validación de rechazo para correos con formato inválido en registro y login.
* **`WalletAccountModelTest` (6 tests):**
  - Envío de dinero con saldo suficiente (descuento e inserción en historial).
  - Error al enviar un monto superior al saldo disponible.
  - Rechazo de envíos con montos cero o negativos.
  - Ingreso de dinero (incremento de saldo e inserción en historial).
  - Rechazo de ingresos con montos cero o negativos.
  - Validación del tope máximo de $5000.00 por transacción (rechazo en $5000.01 y éxito en el límite exacto de $5000.00).

---

## Problemas Resueltos

| Problema | Solución Real Aplicada |
|----------|------------------------|
| **Edge-to-Edge / Barra de estado:** Solapamiento de vistas en Android 15+ (`targetSdk 37`) | Manejo programático de `WindowInsetsCompat` (`Type.systemBars()`) en el contenedor raíz de las 6 Activities, sumando los insets al padding inicial de diseño. |
| **Limpieza de Stack en Logout:** Posibilidad de volver a pantallas protegidas tras cerrar sesión | Inclusión de flags `FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK` al navegar a `AuthActivity` tras invocar `AuthModel.logout()`. |
| **Imágenes Hardcodeadas:** Foto de usuario específica de persona en perfil y home | Creación de un **VectorDrawable** nativo propio de la marca (`ic_avatar_placeholder`) con fondo circular `#BFE0F5` y silueta `#1A87DD`. |
| **Validación de Correo:** Acepcíon de formatos inválidos sin `@` o dominio | Implementación de validación basada en `Regex` en la capa Modelo, testeable en JVM local. |

---

## Validación del Proyecto

| Requerimiento | Estado | Observaciones |
|---------------|:------:|---------------|
| 8 Componentes de Pantalla (6 Activities + 2 Fragments) | ✅ | Estructura modular completa y navegable. |
| View Binding al 100% | ✅ | Utilizado en todas las Activities, Fragments y Adapters. |
| Abstracción de Cadenas en `strings.xml` | ⚠️ Parcial | La mayoría de las cadenas están centralizadas; existen algunos hints/placeholders directos en XML pendientes de abstracción total. |
| Soporte para Modo Oscuro | ⚠️ Parcial | Heredado del tema `DayNight` de Material Components; no cuenta con una paleta de colores personalizada en `values-night/colors.xml`. |
| Cobertura de Pruebas Unitarias de Modelo | ✅ | 13 pruebas JVM en verde sin dependencias de Android. |
| Navegación y Persistencia en Memoria Funcional | ✅ | Integración completa entre flujo de autenticación, dashboard y transacciones. |

---

## Estructura de Pantallas

| # | Pantalla | Tipo | Componente Clave |
|---|----------|------|------------------|
| 1 | Splash | Activity | Logo institucional, delay 2.5s, `noHistory="true"` |
| 2 | Auth Selector | Activity | Contenedor dinámico de fragmentos (`FragmentContainerView`) |
| 3 | Login | Fragment | Campos de acceso vinculados a `AuthModel.login()` |
| 4 | Signup | Fragment | Formulario de registro vinculado a `AuthModel.register()` |
| 5 | Home | Activity | Saldo en tiempo real, `RecyclerView` y refresh automático en `onResume()` |
| 6 | Perfil | Activity | Muestra usuario activo, avatar vectorial e `itemLogout` con limpieza de stack |
| 7 | Enviar Dinero | Activity | Campos editables para destinatario, monto y notas con validación de saldo |
| 8 | Ingresar Dinero | Activity | Campos editables para solicitante, monto y notas con tope de $5000.00 |

---

## Configuración del Proyecto

### `build.gradle.kts` (:app)
- **compileSdk:** 37
- **targetSdk:** 37
- **minSdk:** 24
- **viewBinding:** Habilitado
- **Material Components:** 1.10.0

### `AndroidManifest.xml`
- `SplashActivity` configurada como actividad de lanzamiento (`LAUNCHER`).
- `android:noHistory="true"` asignado al Splash.

---

## Autores y Contribuciones
- **Desarrollador:** Brian Sabio
- **Contribuciones:** Este proyecto es una entrega técnica del Módulo 4 y 5 del curso del SENCE "DESARROLLO DE APLICACIONES MÓVILES ANDROID TRAINEE'. No se aceptan Pull Requests externos en esta etapa.
- **Licencia:** MIT

---

## Contacto y Soporte
- **GitHub Issues:** [Reportar un problema](https://github.com/BrianSabio/Alke_Wallet/issues)
- **LinkedIn:** [Brian Sabio](https://www.linkedin.com/in/brian-ezequiel-sabio/)
