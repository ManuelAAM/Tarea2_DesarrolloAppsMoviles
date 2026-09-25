package com.escom.tarea2.compose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.escom.tarea2.compose.ui.components.CatalogItemCard
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(viewModel: SharedCatalogViewModel) {
    // 1. Checkboxes
    var simpleChecked by remember { mutableStateOf(false) }
    var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }

    // 2. Radio buttons
    val radioOptions = listOf("Nivel Básico (Recomendado)", "Nivel Intermedio", "Nivel Avanzado")
    var selectedRadio by remember { mutableStateOf(radioOptions[0]) }

    // 3. Switch
    var switchState by remember { mutableStateOf(true) }

    // 4. Sliders (sincronizados con el ViewModel para impactar Sección 5)
    val progressValue by viewModel.progressValue.collectAsState()
    var rangeValues by remember { mutableStateOf(20f..80f) }

    // 5. Dropdown
    val categories = listOf("Todas las categorías", "Componentes de Entrada", "Disparadores de Acción", "Estructura y Listas")
    var expandedDropdown by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    // 6. Pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("Fecha no seleccionada") }
    var selectedTimeText by remember { mutableStateOf("Hora no seleccionada") }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)

    // 7. Chips de filtro
    val techChips = listOf("Android Views", "Jetpack Compose", "Flutter", "React Native")
    var selectedChips by remember { mutableStateOf(setOf("Jetpack Compose")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Sección 3: Elementos de Selección",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Componentes para parametrizar selecciones discretas, valores continuos en rieles, fechas y horas en calendarios modales.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 1. Checkboxes
        CatalogItemCard(
            title = "1. Casillas de Verificación (Simple y Tri-State)",
            description = "Permiten selección múltiple no excluyente y el componente TriStateCheckbox representa estados parciales indeterminados."
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        simpleChecked = !simpleChecked
                        viewModel.setLastAction("Checkbox simple: ${if (simpleChecked) "Activo" else "Inactivo"}")
                    }
                ) {
                    Checkbox(
                        checked = simpleChecked,
                        onCheckedChange = {
                            simpleChecked = it
                            viewModel.setLastAction("Checkbox simple: ${if (it) "Activo" else "Inactivo"}")
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Acepto términos y condiciones del sistema")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        triState = when (triState) {
                            ToggleableState.Indeterminate -> ToggleableState.On
                            ToggleableState.On -> ToggleableState.Off
                            ToggleableState.Off -> ToggleableState.Indeterminate
                        }
                        viewModel.setLastAction("Tri-State cambiado a: $triState")
                    }
                ) {
                    TriStateCheckbox(
                        state = triState,
                        onClick = {
                            triState = when (triState) {
                                ToggleableState.Indeterminate -> ToggleableState.On
                                ToggleableState.On -> ToggleableState.Off
                                ToggleableState.Off -> ToggleableState.Indeterminate
                            }
                            viewModel.setLastAction("Tri-State cambiado a: $triState")
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Selección de subelementos (Tri-State: $triState)")
                }
            }
        }

        // 2. Radio buttons
        CatalogItemCard(
            title = "2. Botones de Opción Excluyentes (RadioButtons)",
            description = "Garantizan la exclusión mutua estricta dentro de un grupo lógico de alternativas finitas."
        ) {
            Column {
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedRadio = text
                                viewModel.setLastAction("Opción elegida: $text")
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedRadio),
                            onClick = {
                                selectedRadio = text
                                viewModel.setLastAction("Opción elegida: $text")
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text)
                    }
                }
            }
        }

        // 3. Switch
        CatalogItemCard(
            title = "3. Interruptor Conmutador (Switch)",
            description = "Control deslizante digital para alternar instantáneamente entre dos estados antagónicos (encendido/apagado)."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Habilitar notificaciones en tiempo real")
                Switch(
                    checked = switchState,
                    onCheckedChange = {
                        switchState = it
                        viewModel.setLastAction("Switch notificaciones: ${if (it) "Activado" else "Desactivado"}")
                    }
                )
            }
        }

        // 4. Sliders (Conexión entre secciones)
        CatalogItemCard(
            title = "4. Deslizador Simple y Deslizador de Rango (RangeSlider)",
            description = "Ajusta valores numéricos en un riel continuo. El valor del Slider simple se comparte en tiempo real con la Sección 5."
        ) {
            Column {
                Text(
                    text = "Slider Único: $progressValue% (Afecta Sección 5)",
                    style = MaterialTheme.typography.labelMedium
                )
                Slider(
                    value = progressValue.toFloat(),
                    onValueChange = {
                        viewModel.setProgressValue(it.toInt())
                        viewModel.setLastAction("Progreso modificado en S3: ${it.toInt()}%")
                    },
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "RangeSlider: ${rangeValues.start.toInt()} a ${rangeValues.endInclusive.toInt()}",
                    style = MaterialTheme.typography.labelMedium
                )
                RangeSlider(
                    value = rangeValues,
                    onValueChange = { rangeValues = it },
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // 5. Lista desplegable (Dropdown)
        CatalogItemCard(
            title = "5. Menú Desplegable de Selección (ExposedDropdownMenu)",
            description = "Comprime el catálogo de opciones en una superficie desplegable compacta que no satura la pantalla."
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtrar por Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                viewModel.setSelectedCategory(category)
                                viewModel.setLastAction("Categoría seleccionada en S3: $category")
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
        }

        // 6. Selectores de Fecha y Hora (Pickers)
        CatalogItemCard(
            title = "6. Diálogos Modales de Fecha y Hora (Pickers de M3)",
            description = "Presentan una interfaz gráfica de calendario o reloj circular estandarizada para capturar tiempos con precisión."
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Elegir Fecha")
                    }

                    OutlinedButton(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Elegir Hora")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$selectedDateText | $selectedTimeText",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDatePicker = false
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
                                    selectedDateText = "Fecha: $formatted"
                                    viewModel.setLastAction("Fecha elegida en S3: $formatted")
                                }
                            }) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                if (showTimePicker) {
                    AlertDialog(
                        onDismissRequest = { showTimePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showTimePicker = false
                                val hour = timePickerState.hour
                                val minute = timePickerState.minute
                                val formatted = String.format(Locale.getDefault(), "%02d:%02d hrs", hour, minute)
                                selectedTimeText = "Hora: $formatted"
                                viewModel.setLastAction("Hora elegida en S3: $formatted")
                            }) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
                        },
                        text = {
                            TimePicker(state = timePickerState)
                        }
                    )
                }
            }
        }

        // 7. Chips de filtro
        CatalogItemCard(
            title = "7. Chips de Filtro Seleccionables (FilterChip)",
            description = "Pastillas interactivas que alternan su estado y muestran un icono de marca de verificación al estar seleccionadas."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                techChips.forEach { chipName ->
                    val isSelected = selectedChips.contains(chipName)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedChips = if (isSelected) {
                                selectedChips - chipName
                            } else {
                                selectedChips + chipName
                            }
                            viewModel.setLastAction("Filtro chip: $chipName (${if (!isSelected) "Activo" else "Inactivo"})")
                        },
                        label = { Text(chipName, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    }
}
