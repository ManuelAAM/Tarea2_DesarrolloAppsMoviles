package com.escom.tarea2.compose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ComposeCatalogItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String
)

class SharedCatalogViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ComposeCatalogItem>>(emptyList())
    val items: StateFlow<List<ComposeCatalogItem>> = _items.asStateFlow()

    private val _lastAction = MutableStateFlow("Bienvenido al Catálogo Compose")
    val lastAction: StateFlow<String> = _lastAction.asStateFlow()

    private val _progressValue = MutableStateFlow(45)
    val progressValue: StateFlow<Int> = _progressValue.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todas")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        resetToDefault()
    }

    fun resetToDefault() {
        _items.value = listOf(
            ComposeCatalogItem("1", "OutlinedTextField", "Campo de captura con borde exterior y etiqueta flotante animada.", "Entrada"),
            ComposeCatalogItem("2", "TextField con Error", "Gestión declarativa de validación y textos de apoyo (supportingText).", "Entrada"),
            ComposeCatalogItem("3", "Campo de Contraseña", "Uso de PasswordVisualTransformation con toggle reactivo de visibilidad.", "Entrada"),
            ComposeCatalogItem("4", "Configuración IME", "Teclados numéricos, email y teléfono vía KeyboardOptions.", "Entrada"),
            ComposeCatalogItem("5", "TextField Multilínea", "Entrada expandible mediante los parámetros minLines y maxLines.", "Entrada"),
            ComposeCatalogItem("6", "ExposedDropdownMenuBox", "Menú desplegable integrado en la caja del campo de texto.", "Entrada"),
            ComposeCatalogItem("7", "SearchBar de M3", "Barra moderna de búsqueda con dock interactivo y filtrado.", "Entrada"),
            ComposeCatalogItem("8", "Button Relleno", "Componente Button estándar con elevación y color primario.", "Botones"),
            ComposeCatalogItem("9", "OutlinedButton", "Botón con borde perimetral para acciones secundarias.", "Botones"),
            ComposeCatalogItem("10", "TextButton", "Botón minimalista sin contenedor de fondo.", "Botones"),
            ComposeCatalogItem("11", "IconButton", "Disparador táctil circular para iconos Material.", "Botones"),
            ComposeCatalogItem("12", "FloatingActionButton", "FAB circular y ExtendedFloatingActionButton con texto.", "Botones"),
            ComposeCatalogItem("13", "SegmentedButton", "Selector segmentado de opciones en una sola franja.", "Botones"),
            ComposeCatalogItem("14", "TriStateCheckbox", "Casilla de verificación con estado ToggleableState.Indeterminate.", "Selección"),
            ComposeCatalogItem("15", "RadioButton", "Opciones circulares de selección única excluyente.", "Selección"),
            ComposeCatalogItem("16", "Switch", "Interruptor deslizante binario con iconos opcionales en el thumb.", "Selección")
        )
        _lastAction.value = "Lista restablecida a valores iniciales"
    }

    fun addItem(title: String, description: String, category: String) {
        val newItem = ComposeCatalogItem(
            id = System.currentTimeMillis().toString(),
            title = title,
            description = description,
            category = category
        )
        _items.value = listOf(newItem) + _items.value
        _lastAction.value = "Elemento agregado a Sección 4: $title"
    }

    fun removeItem(item: ComposeCatalogItem) {
        _items.value = _items.value.filter { it.id != item.id }
        _lastAction.value = "Elemento eliminado: ${item.title}"
    }

    fun restoreItem(item: ComposeCatalogItem, index: Int) {
        val list = _items.value.toMutableList()
        val targetIndex = index.coerceIn(0, list.size)
        list.add(targetIndex, item)
        _items.value = list
        _lastAction.value = "Elemento restaurado: ${item.title}"
    }

    fun setLastAction(action: String) {
        _lastAction.value = action
    }

    fun setProgressValue(value: Int) {
        _progressValue.value = value
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }
}
