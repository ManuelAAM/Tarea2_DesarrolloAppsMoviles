# Guía de Capturas de Pantalla y Evidencias Gráficas

Este directorio contiene las evidencias visuales organizadas por tecnología y sección temática, requeridas para la entrega de la **Tarea 2: Catálogo de Elementos Básicos de Interfaz de Usuario**.

## Estructura de Nomenclatura en `docs/img/`

Cada archivo corresponde a una sección en una tecnología específica:

| Archivo | Tecnología | Sección | Contenido |
| :--- | :--- | :--- | :--- |
| `views_sec1.png` | Android Views | Sección 1 | Entrada de Texto |
| `views_sec2.png` | Android Views | Sección 2 | Botones y Acciones |
| `views_sec3.png` | Android Views | Sección 3 | Elementos de Selección |
| `views_sec4.png` | Android Views | Sección 4 | Listas y Colecciones |
| `views_sec5.png` | Android Views | Sección 5 | Retroalimentación |
| `views_sec6.png` | Android Views | Sección 6 | Contenedores y Estructura |
| `compose_sec1.png` | Jetpack Compose | Sección 1 | Entrada de Texto |
| `compose_sec2.png` | Jetpack Compose | Sección 2 | Botones y Acciones |
| `compose_sec3.png` | Jetpack Compose | Sección 3 | Elementos de Selección |
| `compose_sec4.png` | Jetpack Compose | Sección 4 | Listas y Colecciones |
| `compose_sec5.png` | Jetpack Compose | Sección 5 | Retroalimentación |
| `compose_sec6.png` | Jetpack Compose | Sección 6 | Contenedores y Estructura |
| `flutter_sec1.png` | Flutter | Sección 1 | Entrada de Texto |
| `flutter_sec2.png` | Flutter | Sección 2 | Botones y Acciones |
| `flutter_sec3.png` | Flutter | Sección 3 | Elementos de Selección |
| `flutter_sec4.png` | Flutter | Sección 4 | Listas y Colecciones |
| `flutter_sec5.png` | Flutter | Sección 5 | Retroalimentación |
| `flutter_sec6.png` | Flutter | Sección 6 | Contenedores y Estructura |
| `react_sec1.png` | React Native | Sección 1 | Entrada de Texto |
| `react_sec2.png` | React Native | Sección 2 | Botones y Acciones |
| `react_sec3.png` | React Native | Sección 3 | Elementos de Selección |
| `react_sec4.png` | React Native | Sección 4 | Listas y Colecciones |
| `react_sec5.png` | React Native | Sección 5 | Retroalimentación |
| `react_sec6.png` | React Native | Sección 6 | Contenedores y Estructura |

---

## Instrucciones para el Estudiante: Cómo Capturar y Reemplazar Pantallas

Si deseas sustituir las imágenes con capturas tomadas directamente desde tu emulador o celular Android:

1. **Desde Android Studio (Emulador)**:
   - Ejecuta la aplicación correspondiente (`android-views`, `android-compose` o `flutter`).
   - Navega a la sección deseada.
   - En la barra lateral de herramientas del emulador, pulsa el botón con icono de cámara (**Screen Capture / Screenshot**).
   - Guarda el archivo con el nombre exacto de la tabla anterior dentro de `docs/img/`.

2. **Vía ADB (Línea de Comandos)**:
   ```powershell
   # Ejemplo para capturar la pantalla actual en el dispositivo:
   adb exec-out screencap -p > docs/img/views_sec1.png
   ```

3. **Subida a GitHub**:
   - Una vez actualizadas tus imágenes en `docs/img/`, ejecuta:
     ```bash
     git add docs/img/
     git commit -m "docs: actualizar capturas de pantalla de la aplicacion"
     git push origin master
     ```
