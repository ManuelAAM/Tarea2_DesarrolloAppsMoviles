package com.escom.tarea2.compose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.escom.tarea2.compose.ui.components.CatalogItemCard
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    viewModel: SharedCatalogViewModel,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val progressValue by viewModel.progressValue.collectAsState()
    val items by viewModel.items.collectAsState()

    var showConfirmDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Sección 5: Información y Retroalimentación",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Componentes para comunicar retroalimentación visual, escalas tipográficas, imágenes locales/remotas, barras de progreso y modales.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 1. Textos y estilos tipográficos
        CatalogItemCard(
            title = "1. Estilos, Tamaños y Énfasis Tipográfico",
            description = "Estructura la información combinando diferentes escalas tipográficas de Material 3 con variaciones de peso, cursiva y colores primarios."
        ) {
            Column {
                Text(
                    text = "Display Small (24sp, Negrita)",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Title Medium con énfasis en color primario",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Cuerpo de texto regular en cursiva para anotaciones complementarias",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Etiqueta pequeña (LabelSmall) para subtítulos y metadatos",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // 2. Imagen local y remota
        CatalogItemCard(
            title = "2. Imágenes (Local y Remota con Modos de Escalado)",
            description = "Renderiza recursos vectoriales locales y descarga asíncrona por URL vía Coil AsyncImage, demostrando ContentScale.Crop y Fit."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Local (Fit)", style = MaterialTheme.typography.labelSmall)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                        contentDescription = "Pikachu Remoto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Remota (Crop)", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // 3. Indicadores de progreso (sincronizados con Sección 3)
        CatalogItemCard(
            title = "3. Indicadores de Progreso (Determinado e Indeterminado)",
            description = "El progreso lineal y circular determinado refleja en tiempo real el valor ajustado en el Slider de la Sección 3 ($progressValue%)."
        ) {
            Column {
                Text(
                    text = "Progreso Lineal Determinado ($progressValue% desde S3):",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progressValue / 100f },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Progreso Lineal Indeterminado:",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(progress = { progressValue / 100f })
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Circular Fijo ($progressValue%)", style = MaterialTheme.typography.labelSmall)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Circular Continuo", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // 4. Notificaciones efímeras: Toast y Snackbar
        CatalogItemCard(
            title = "4. Notificaciones Breves: Toast y Snackbar con Acción",
            description = "El Toast muestra una alerta fugaz no intrusiva; el Snackbar proporciona un mensaje flotante con opción reactiva de 'Deshacer'."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Toast breve desde Jetpack Compose", Toast.LENGTH_SHORT).show()
                        viewModel.setLastAction("Toast lanzado desde Compose")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lanzar Toast")
                }

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.setLastAction("Snackbar lanzado desde Compose")
                            val result = snackbarHostState.showSnackbar(
                                message = "Elemento archivado con éxito",
                                actionLabel = "Deshacer",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                Toast.makeText(context, "Acción deshecha", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lanzar Snackbar")
                }
            }
        }

        // 5. Diálogo de confirmación y Hoja Inferior
        CatalogItemCard(
            title = "5. Diálogo Modal y Hoja Inferior (ModalBottomSheet)",
            description = "Interrumpen la navegación para solicitar confirmación o desplegar una sábana inferior con opciones contextuales."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalButton(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Abrir Diálogo")
                }

                FilledTonalButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Abrir BottomSheet")
                }
            }
        }

        // 6. Tarjeta, Separador y Distintivo Numérico (Badge)
        CatalogItemCard(
            title = "6. Tarjeta (Card), Separador (Divider) y Badge",
            description = "Agrupa visualmente elementos en tarjetas con elevación, separa secciones y presenta distintivos numéricos reactivos."
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Notificaciones del Sistema",
                            style = MaterialTheme.typography.titleMedium
                        )
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text("${items.size}")
                                }
                            }
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text = "El distintivo numérico refleja la cantidad viva de elementos registrados en la colección (${items.size}).",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        // AlertDialog de confirmación
        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Confirmar Acción") },
                text = { Text("¿Deseas confirmar la sincronización de los componentes con la nube?") },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        viewModel.setLastAction("Diálogo: Acción confirmada")
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        viewModel.setLastAction("Diálogo: Acción cancelada")
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Modal BottomSheet
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Hoja Inferior Modal (Compose)",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ModalBottomSheet se desliza desde la parte inferior de la pantalla para presentar contenido complementario o flujos de acción secundarios.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) showBottomSheet = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar Hoja")
                    }
                }
            }
        }
    }
}
