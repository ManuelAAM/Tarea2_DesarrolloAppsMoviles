package com.escom.tarea2.compose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.escom.tarea2.compose.ui.components.CatalogItemCard
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputsScreen(viewModel: SharedCatalogViewModel) {
    var simpleText by remember { mutableStateOf("") }
    var validationText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var numericText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf("") }
    var multilineText by remember { mutableStateOf("") }

    // Dropdown sugerencias
    val suggestions = listOf("Desarrollo Móvil Nativo", "Sistemas Distribuidos", "Arquitectura de Software", "Ciberseguridad")
    var expandedDropdown by remember { mutableStateOf(false) }
    var selectedSuggestion by remember { mutableStateOf(suggestions[0]) }

    // Barra de búsqueda
    var searchQuery by remember { mutableStateOf("") }
    var searchStatus by remember { mutableStateOf("Consulta: (Ninguna)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Sección 1: Entrada de Texto",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Enfoque declarativo con composables de captura. Cada entrada gestiona su estado mediante elevación de estado (hoisting).",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 1. Campo de texto simple
        CatalogItemCard(
            title = "1. OutlinedTextField Simple con Etiqueta Flotante",
            description = "Campo de entrada con borde exterior y animación intrínseca de etiqueta flotante al recibir el foco del usuario."
        ) {
            OutlinedTextField(
                value = simpleText,
                onValueChange = {
                    simpleText = it
                    viewModel.setLastAction("Texto simple actualizado: $it")
                },
                label = { Text("Nombre del Componente") },
                placeholder = { Text("Ej. Mi nuevo botón") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 2. Campo con validación y mensaje de error
        val isError = validationText.isNotEmpty() && validationText.length < 5
        CatalogItemCard(
            title = "2. Campo con Validación y Error Dinámico",
            description = "Aplica validación reactiva en tiempo real sobre la entrada. Muestra un estado de error y mensaje de apoyo (supportingText) si la longitud es menor a 5."
        ) {
            OutlinedTextField(
                value = validationText,
                onValueChange = { validationText = it },
                label = { Text("Código de Boleta (Mínimo 5 caracteres)") },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text("Error: Muy corto (${validationText.length}/5 caracteres)")
                    } else {
                        Text("Entrada válida")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 3. Campo de contraseña con visibilidad conmutable
        CatalogItemCard(
            title = "3. Campo de Contraseña con Toggle de Visibilidad",
            description = "Utiliza PasswordVisualTransformation para enmascarar caracteres con un IconButton al final para alternar entre texto plano y oculto."
        ) {
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                label = { Text("Contraseña de Acceso") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 4. Distintos tipos de teclado (IME)
        CatalogItemCard(
            title = "4. Campos con Teclados Especializados (KeyboardOptions)",
            description = "Configuran el teclado del sistema para optimizar la entrada según el tipo semántico: numérico, correo y teléfono."
        ) {
            Column {
                OutlinedTextField(
                    value = numericText,
                    onValueChange = { numericText = it },
                    label = { Text("Teclado Numérico") },
                    leadingIcon = { Icon(Icons.Default.Pin, null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = emailText,
                    onValueChange = { emailText = it },
                    label = { Text("Teclado Correo Electrónico") },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phoneText,
                    onValueChange = { phoneText = it },
                    label = { Text("Teclado Telefónico") },
                    leadingIcon = { Icon(Icons.Default.Phone, null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // 5. Campo multilínea
        CatalogItemCard(
            title = "5. Campo de Texto Multilínea (minLines / maxLines)",
            description = "Permite la edición de párrafos extensos y notas libres con crecimiento vertical dinámico entre un mínimo y un máximo de líneas."
        ) {
            OutlinedTextField(
                value = multilineText,
                onValueChange = { multilineText = it },
                label = { Text("Notas u Observaciones del Alumno") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 6. Sugerencias automáticas con ExposedDropdownMenuBox
        CatalogItemCard(
            title = "6. Menú Desplegable Expuesto (ExposedDropdownMenuBox)",
            description = "Presenta una lista emergente de opciones canónicas sugeridas al usuario directamente anclada a la caja de texto."
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = selectedSuggestion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Asignatura") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    suggestions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedSuggestion = option
                                expandedDropdown = false
                                viewModel.setLastAction("Asignatura elegida: $option")
                            }
                        )
                    }
                }
            }
        }

        // 7. Barra de búsqueda interactiva
        CatalogItemCard(
            title = "7. Barra de Búsqueda Interactiva",
            description = "Campo con botones de acción para disparar búsquedas y limpiar instantáneamente la consulta activa."
        ) {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        searchStatus = if (it.isEmpty()) "Consulta: (Ninguna)" else "Buscando: \"$it\""
                    },
                    label = { Text("Buscar componentes...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = {
                                searchQuery = ""
                                searchStatus = "Consulta limpiada"
                            }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = searchStatus,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // Conexión entre Secciones: Agregar a Colección de Sección 4
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Conexión Interactiva: Enviar a Sección 4",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Agrega el valor capturado en el Campo Simple directamente a la lista compartida de la Sección 4.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val title = if (simpleText.isBlank()) "Elemento desde Compose S1" else simpleText
                        val desc = if (multilineText.isBlank()) "Agregado interactivamente desde la Sección 1 de Compose" else multilineText
                        viewModel.addItem(title, desc, "Formulario S1")
                        simpleText = ""
                        multilineText = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Agregar a Lista de Sección 4")
                }
            }
        }
    }
}
