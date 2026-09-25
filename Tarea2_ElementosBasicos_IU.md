OBJETIVO
 
Construir un catálogo interactivo de elementos de interfaz de usuario e implementarlo en tres tecnologías distintas, para identificar los componentes básicos de una interfaz móvil, sus equivalencias entre plataformas y las diferencias entre los enfoques de construcción de interfaces.
 
 
TECNOLOGÍAS REQUERIDAS
 
La misma aplicación debe desarrollarse en las siguientes tres tecnologías:
 
1. Android nativo con Views y XML (Kotlin + layouts XML)
2. Android nativo con Jetpack Compose (Kotlin + funciones composable)
3. Flutter (Dart, ejecutándose al menos en Android)
 
Opcional (por puntos extra): una cuarta tecnología a elección, por ejemplo Compose Multiplatform, React Native, .NET MAUI o SwiftUI (esta última solo si cuenta con acceso a macOS).
 
 
ESTRUCTURA DE LA APLICACIÓN
 
Cada versión debe contar con una pantalla principal y seis secciones (Fragments en la versión de Views, composables de destino en Compose, rutas o pantallas en Flutter), una por cada categoría de elementos.
 
SECCIÓN 1: ENTRADA DE TEXTO
- Campo de texto simple con etiqueta o hint
- Campo con validación y mensaje de error visible
- Campo de contraseña con opción para mostrar u ocultar el contenido
- Campos con distintos tipos de teclado: numérico, correo electrónico, teléfono
- Campo multilínea
- Campo con sugerencias automáticas o desplegable de opciones
- Barra de búsqueda
 
SECCIÓN 2: BOTONES Y ACCIONES
- Botón relleno, botón con contorno y botón de solo texto
- Botón con ícono (solo ícono y con ícono más texto)
- Botón de acción flotante, en su versión normal y extendida
- Botón de alternancia (toggle) o selector segmentado
- Botón deshabilitado y botón en estado de carga
- Cada botón debe producir una respuesta visible al pulsarse
 
SECCIÓN 3: ELEMENTOS DE SELECCIÓN
- Casilla de verificación, incluyendo un caso con estado indeterminado
- Grupo de botones de opción mutuamente excluyentes
- Interruptor (switch)
- Deslizador de valor único y deslizador de rango
- Lista desplegable de selección
- Selector de fecha y selector de hora
- Chips de filtro seleccionables
 
SECCIÓN 4: LISTAS Y COLECCIONES
- Lista vertical con al menos quince elementos
- Cuadrícula de elementos
- Lista con encabezados de sección (al menos dos tipos de elemento distintos)
- Selección de un elemento que abra su detalle o muestre su información
- Deslizar un elemento para eliminarlo
- Actualizar la lista arrastrando hacia abajo
- Estado vacío, con mensaje e ilustración, cuando no hay elementos
- Pestañas con contenido deslizable entre ellas
 
SECCIÓN 5: INFORMACIÓN Y RETROALIMENTACIÓN
- Textos con distintos estilos, tamaños y énfasis
- Imagen local e imagen cargada desde una URL, con distintos modos de escalado
- Indicador de progreso lineal y circular, en modo determinado e indeterminado
- Mensaje emergente breve (toast) y mensaje con acción (snackbar)
- Diálogo de confirmación
- Hoja inferior (bottom sheet)
- Tarjeta, separador y distintivo numérico (badge)
 
SECCIÓN 6: CONTENEDORES Y ESTRUCTURA
- Distribución en fila, en columna y superpuesta
- Contenedor con desplazamiento vertical
- Barra superior con título y acciones
- Barra de navegación inferior o menú lateral
- Ejemplo de una distribución con restricciones o con pesos proporcionales
 
 
REQUISITOS TRANSVERSALES
 
Aplican a las tres versiones:
 
- Navegación: debe existir un menú, barra inferior o conjunto de pestañas para moverse entre las seis secciones, y poder regresar a la pantalla principal.
- Documentación en pantalla: cada elemento debe mostrar su nombre, una explicación de dos o tres líneas sobre para qué sirve, y una demostración con la que el usuario pueda interactuar.
- Interacción real: no se aceptan capturas, imágenes ni componentes decorativos. Cada elemento debe responder al usuario.
- Tema claro y oscuro: la aplicación debe adaptarse al modo del sistema.
- Idioma: todos los textos de la interfaz y de la documentación en español.
- Conexión entre secciones: implementar al menos una funcionalidad que vincule dos secciones; por ejemplo, un dato capturado en la Sección 1 que se agregue a la lista de la Sección 4, o una preferencia elegida en la Sección 3 que modifique lo que se muestra en la Sección 5.
- Diseño: interfaz limpia, con espaciado consistente y jerarquía visual clara.
 
 
TABLA DE EQUIVALENCIAS
 
En el README principal debe incluirse una tabla que relacione, para cada elemento del catálogo, el componente utilizado en cada una de las tres tecnologías. Por ejemplo:
 
Elemento: Campo de texto
Views / XML: EditText / TextInputLayout
Jetpack Compose: TextField
Flutter: TextField
 
Elemento: Interruptor
Views / XML: Switch
Jetpack Compose: Switch
Flutter: Switch
 
Elemento: Lista vertical
Views / XML: RecyclerView
Jetpack Compose: LazyColumn
Flutter: ListView.builder
 
La tabla debe cubrir todos los elementos solicitados. Cuando un componente no tenga equivalente directo, indicarlo y explicar cómo se resolvió.
 
 
ENTREGABLES
 
Todo se entrega en un solo repositorio público de GitHub, cuya liga se registra en Classroom.
 
Estructura del repositorio:
 
android-views/ Versión con Views y XML
android-compose/ Versión con Jetpack Compose
flutter/ Versión con Flutter
extra/ Cuarta tecnología (opcional)
docs/ Capturas de pantalla
README.md Documento principal
 
Contenido del README.md principal:
 
- Descripción de la aplicación y de las tecnologías utilizadas
- Datos de identificación: nombre completo, número de boleta y grupo
- Instrucciones de compilación y ejecución de cada versión
- Tabla de equivalencias entre las tres tecnologías
- Capturas de pantalla de las seis secciones en cada tecnología, incrustadas desde la carpeta docs/
- Reflexión final: en cuál tecnología resultó más rápido construir la interfaz, cuál generó código más legible, qué dificultades encontró en cada una y con cuál preferiría trabajar
- Referencias consultadas en formato APA
 
Binarios:
 
- APK de cada una de las tres versiones (Views, Compose y Flutter)
 
 
CRITERIOS DE EVALUACIÓN
 
- Versión con Views y XML, completa y funcional .............. 25 puntos
- Versión con Jetpack Compose, completa y funcional .......... 25 puntos
- Versión con Flutter, completa y funcional .................. 25 puntos
- Tabla de equivalencias y documentación del README .......... 15 puntos
- Navegación, tema claro/oscuro y calidad del diseño ......... 10 puntos
- Cuarta tecnología (opcional) ............................... +10 puntos
 
Se descontarán puntos por elementos presentes pero no funcionales, por textos en un idioma distinto al español y por capturas que no correspondan con el código entregado.
 
 
NOTAS
 
- El historial de commits debe evidenciar trabajo incremental; no se acepta una sola carga al final.
- No se aceptan entregas en Word ni en PDF: toda la documentación vive en el repositorio, en archivos .md.