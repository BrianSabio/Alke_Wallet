# AlkeWallet - Digital Wallet

Una billetera digital moderna desarrollada en Kotlin nativo para Android.

![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![SDK](https://img.shields.io/badge/SDK-24%2B-lightgrey.svg)
![Status](https://img.shields.io/badge/Status-Complete-success.svg)

AlkeWallet es una aplicación móvil diseñada para la gestión simulada de finanzas personales. Permite a los usuarios visualizar su saldo, revisar un historial detallado de movimientos, gestionar su perfil y realizar operaciones de envío y solicitud de dinero mediante una interfaz intuitiva.

---

## Tabla de Contenidos
1. [Características Principales](#características-principales)
2. [Tecnología y Stack](#tecnología-y-stack)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Instalación y Setup](#instalación-y-setup)
5. [Guía de Uso / Flujo de la App](#guía-de-uso--flujo-de-la-app)
6. [Arquitectura y Decisiones Técnicas](#arquitectura-y-decisiones-técnicas)
7. [Problemas Resueltos](#problemas-resueltos)
8. [Validación del Proyecto](#validación-del-proyecto)
9. [Estructura de Pantallas](#estructura-de-pantallas)
10. [Configuración del Proyecto](#configuración-del-proyecto)
11. [Autores y Contribuciones](#autores-y-contribuciones)

---

## Características Principales

- **Splash Screen:** Pantalla de bienvenida con branding institucional y transición automática de 2.5 segundos.
- **Módulo de Autenticación:** Sistema híbrido de Activities y Fragments para el selector de acceso, inicio de sesión y registro.
- **Dashboard (Home):** Resumen de saldo y lista dinámica de transacciones (enviadas/recibidas) con estados visuales diferenciados.
- **Gestión de Perfil:** Pantalla de usuario con menú de opciones para información, tarjetas y configuración.
- **Operaciones Financieras:** Flujos dedicados para enviar dinero a contactos y solicitar ingresos de saldo.

---

## Tecnología y Stack

| Componente | Tecnología | Descripción |
|------------|------------|-------------|
| **Lenguaje** | Kotlin 1.9+ | Lenguaje principal del proyecto. |
| **Plataforma** | Android | Ejecución nativa (Min SDK 24 / Target 37). |
| **Build System** | Gradle (Kotlin DSL) | Gestión de dependencias y compilación. |
| **Arquitectura** | Modular por Features | Organización de paquetes según funcionalidad. |
| **UI Framework** | XML Layouts | Diseño de interfaces mediante Android Views. |
| **Binding** | View Binding | Interacción segura y eficiente con las vistas. |
| **Diseño** | Material Design 3 | Uso de componentes y estilos estandarizados. |

---

## Estructura del Proyecto

```text
AlkeWallet/
├── app/src/main/
│   ├── java/com/alkewallet/
│   │   ├── splash/         # Lógica de arranque
│   │   ├── auth/           # Login, Signup y Selector
│   │   ├── home/           # Dashboard y Adapters de lista
│   │   ├── profile/        # Gestión de perfil de usuario
│   │   └── transactions/   # Módulos de envío y solicitud
│   ├── res/
│   │   ├── layout/         # Definiciones de interfaz XML
│   │   ├── drawable/       # Recursos gráficos vectoriales
│   │   └── values/         # Cadenas, colores y dimensiones
│   └── AndroidManifest.xml
└── build.gradle.kts
```

---

## Instalación y Setup

### Requisitos Previos
- Android Studio Quail 3 (o superior).
- JDK 17+.
- Android SDK 24 instalado.
- Git.

### Pasos para clonar y ejecutar
1. Clone el repositorio:
   ```bash
   git clone https://github.com/BrianSabio/Alke_Wallet.git
   ```
2. Abra el proyecto en Android Studio.
3. Sincronice el proyecto con los archivos de Gradle.
4. Conecte un dispositivo físico o emulador (API 24+).
5. Presione `Shift + F10` para ejecutar la aplicación.

---

## Guía de Uso / Flujo de la App

**Splash (2.5s)** ➔ **Auth Selector** ➔ **Login / Signup**
- **Login:** Ingrese credenciales simuladas para acceder al Dashboard.
- **Signup:** Complete el registro para volver al inicio de sesión.

**Home (Dashboard)** ➔ Click en:
- **Perfil:** Visualización de datos de cuenta.
- **Enviar Dinero:** Formulario con campo de monto (borde azul) y botón de confirmación.
- **Ingresar Dinero:** Formulario con campo de monto (borde verde) y botón de confirmación.
- **Atrás:** El botón de retroceso (`finish()`) siempre devuelve al usuario a la pantalla anterior sin duplicar tareas.

---

## Arquitectura y Decisiones Técnicas

### Patrón de UI
Se implementó **View Binding** de forma estricta para evitar el uso de `findViewById`. Todos los layouts raíz son `ConstraintLayout` para optimizar el rendimiento mediante jerarquías planas.

```kotlin
// Implementación estándar en Fragments
private var _binding: FragmentLoginBinding? = null
private val binding get() = _binding!!
override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
```

### Gestión de Recursos
- **Strings:** Centralizados en `strings.xml` para escalabilidad.
- **Colores:** Paleta personalizada `alke_*` definida en `colors.xml`.
- **Dimensiones:** Márgenes y tamaños tipográficos en `dimens.xml`.

---

## Problemas Resueltos

| Problema | Solución |
|----------|----------|
| **Modo Oscuro:** Inputs invisibles | Se forzaron colores de stroke y hint en Material Components. |
| **Diseño:** Vistas bajo la barra de estado | Uso de `android:fitsSystemWindows="true"` en layouts raíz. |
| **Estabilidad:** Crashes por imágenes | Conversión de SVG inestables a **Vector Drawables**. |
| **UX:** Textos dobles en campos | Eliminación de `hint` en EditText, manteniendo solo el del TextInputLayout. |

---

## Validación del Proyecto

| Requerimiento | Estado |
|---------------|:------:|
| 9 Pantallas implementadas según consigna | ✅ |
| View Binding implementado al 100% | ✅ |
| Cero valores hardcodeados en layouts | ✅ |
| ConstraintLayout en todos los archivos XML | ✅ |
| Navegación funcional entre todos los módulos | ✅ |
| Soporte para Modo Claro y Modo Oscuro | ✅ |

---

## Estructura de Pantallas

| # | Pantalla | Tipo | Componente Clave |
|---|----------|------|------------------|
| 1 | Splash | Activity | Logo institucional, delay 2.5s |
| 2 | Selector | Activity | Botones de acceso y navegación fragmentada |
| 3 | Login | Fragment | TextInputLayout con toggle de contraseña |
| 4 | Signup | Fragment | 5 campos de registro validados visualmente |
| 5 | Home | Activity | RecyclerView con datos de transacción exactos |
| 6 | Perfil | Activity | MaterialCardView con opciones de navegación |
| 7 | Envío | Activity | Input con `boxStrokeColor` azul primario |
| 8 | Ingreso | Activity | Input con `boxStrokeColor` verde acción |

---

## Configuración del Proyecto

### build.gradle.kts (app)
- **minSdk:** 24
- **targetSdk:** 37
- **viewBinding:** Habilitado
- **Material Components:** 1.10.0

### AndroidManifest.xml
- `SplashActivity` configurada como categoría `LAUNCHER`.
- `android:noHistory="true"` aplicado al Splash para limpiar la pila de actividades.

---

## Autores y Contribuciones
- **Desarrollador:** Brian Sabio
- **Contribuciones:** Este proyecto es una entrega técnica del Módulo 4 (ABP). No se aceptan Pull Requests externos en esta etapa.
- **Licencia:** MIT

---

## Contacto y Soporte
- **GitHub Issues:** [Reportar un problema](https://github.com/BrianSabio/Alke_Wallet/issues)
- **LinkedIn:** [Brian Sabio](https://www.linkedin.com/in/briansabio/)

