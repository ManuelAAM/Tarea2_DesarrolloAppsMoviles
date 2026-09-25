package com.escom.tarea2.compose.ui.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.escom.tarea2.compose.ui.components.CatalogItemCard
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(viewModel: SharedCatalogViewModel) {
    var buttonFeedback by remember { mutableStateOf("Respuesta: Pulsa cualquier botón para interactuar") }
    var selectedSegmentIndex by remember { mutableIntStateOf(0) }
    val segmentOptions = listOf("Día", "Semana", "Mes")

    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun triggerFeedback(name: String) {
        val msg = "Respuesta visible: Pulsado '$name'"
        buttonFeedback = msg
        viewModel.setLastAction(msg)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Sección 2: Botones y Acciones",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Disparadores de eventos táctiles. Todos los botones producen una respuesta visible y tangible inmediata.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Banner de respuesta visual inmediata
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.SmartButton,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = buttonFeedback,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 1. Jerarquía de botones
        CatalogItemCard(
            title = "1. Jerarquía de Énfasis: Relleno, Contorno y Solo Texto",
            description = "Estructuran las llamadas a la acción según su prioridad en pantalla: acción primaria destacada, secundaria perimetral y terciaria ligera."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { triggerFeedback("Button Relleno (Primary)") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Relleno")
                }
                OutlinedButton(
                    onClick = { triggerFeedback("OutlinedButton (Secondary)") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Contorno")
                }
                TextButton(
                    onClick = { triggerFeedback("TextButton (Tertiary)") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Texto")
                }
            }
        }

        // 2. Botones con ícono
        CatalogItemCard(
            title = "2. Botones con Ícono (Solo Ícono y Mixto)",
            description = "Sintetizan la intención de la interacción mediante glifos vectoriales que refuerzan el reconocimiento semántico inmediato."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { triggerFeedback("IconButton de Favorito") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { triggerFeedback("Botón con Ícono y Texto") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Añadir a Favoritos")
                }
            }
        }

        // 3. Botones de Acción Flotante (FAB)
        CatalogItemCard(
            title = "3. Botón de Acción Flotante (Normal y Extendido)",
            description = "Componente flotante sobre la superficie para la acción prioritaria de la pantalla, con variante extendida que añade texto explicativo."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { triggerFeedback("FAB Normal Circular") }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                ExtendedFloatingActionButton(
                    onClick = { triggerFeedback("Extended FAB con Rótulo") },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Crear Registro") }
                )
            }
        }

        // 4. Selector Segmentado (SegmentedButton)
        CatalogItemCard(
            title = "4. Selector Segmentado (SingleChoiceSegmentedButtonRow)",
            description = "Agrupación horizontal de opciones contiguas mutuamente excluyentes implementada con el estándar de Material 3."
        ) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                segmentOptions.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = segmentOptions.size),
                        onClick = {
                            selectedSegmentIndex = index
                            triggerFeedback("Segmento seleccionado: $label")
                        },
                        selected = index == selectedSegmentIndex
                    ) {
                        Text(label)
                    }
                }
            }
        }

        // 5. Botón deshabilitado y botón con estado de carga
        CatalogItemCard(
            title = "5. Botón Deshabilitado y Estado de Carga (Loading)",
            description = "Representa estados de bloqueo temporal ante validaciones insatisfechas o ejecución asíncrona mediante un indicador de giro circular."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Inactivo")
                }

                FilledTonalButton(
                    onClick = {
                        if (!isLoading) {
                            isLoading = true
                            buttonFeedback = "Ejecutando tarea asíncrona..."
                            scope.launch {
                                delay(2000)
                                isLoading = false
                                triggerFeedback("Operación asíncrona completada exitosamente")
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cargando...")
                    } else {
                        Text("Iniciar Carga")
                    }
                }
            }
        }
    }
}
