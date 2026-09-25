package com.escom.tarea2.views.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedCatalogViewModel : ViewModel() {

    private val _items = MutableLiveData<MutableList<CatalogItem>>()
    val items: LiveData<MutableList<CatalogItem>> = _items

    private val _lastAction = MutableLiveData<String>("Bienvenido al Catálogo de UI")
    val lastAction: LiveData<String> = _lastAction

    private val _selectedCategory = MutableLiveData<String>("Todas")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _progressValue = MutableLiveData<Int>(45)
    val progressValue: LiveData<Int> = _progressValue

    init {
        _items.value = createDefaultItems()
    }

    private fun createDefaultItems(): MutableList<CatalogItem> {
        return mutableListOf(
            CatalogItem("1", "EditText Simple", "Campo estándar para captura de cadenas de caracteres alfanuméricas.", "Entrada"),
            CatalogItem("2", "Input con Validación", "Evalúa patrones de expresión regular (Regex) en tiempo real.", "Entrada"),
            CatalogItem("3", "Campo Contraseña", "Oculta el texto mediante máscara con opción de revelado dinámico.", "Entrada"),
            CatalogItem("4", "Teclados Específicos", "Ajusta la distribución del IME (Numérico, Email, Teléfono).", "Entrada"),
            CatalogItem("5", "EditText Multilínea", "Permite captura libre de párrafos extensos y notas.", "Entrada"),
            CatalogItem("6", "AutoCompleteTextView", "Filtra sugerencias flotantes conforme el usuario escribe.", "Entrada"),
            CatalogItem("7", "SearchView", "Barra optimizada para consultas con disparadores de búsqueda.", "Entrada"),
            CatalogItem("8", "MaterialButton Relleno", "Botón con alto énfasis visual para la acción primaria.", "Botones"),
            CatalogItem("9", "OutlinedButton", "Botón de énfasis medio con borde perimetral.", "Botones"),
            CatalogItem("10", "TextButton", "Botón sin fondo ideal para diálogos y acciones terciarias.", "Botones"),
            CatalogItem("11", "IconButton", "Acciones directas representadas mediante glifos vectoriales.", "Botones"),
            CatalogItem("12", "FloatingActionButton", "Botón circular elevado para la acción prioritaria de pantalla.", "Botones"),
            CatalogItem("13", "MaterialButtonToggleGroup", "Conjunto de botones contiguos de selección excluyente.", "Botones"),
            CatalogItem("14", "MaterialCheckBox Tri-State", "Casilla de selección con soporte para estados indeterminados.", "Selección"),
            CatalogItem("15", "RadioGroup", "Contenedor de exclusión mutua para selección única.", "Selección"),
            CatalogItem("16", "MaterialSwitch", "Conmutador instantáneo de propiedades binarias (On/Off).", "Selección")
        )
    }

    fun addItem(title: String, description: String, category: String) {
        val currentList = _items.value ?: mutableListOf()
        val newItem = CatalogItem(
            id = System.currentTimeMillis().toString(),
            title = title,
            description = description,
            category = category
        )
        currentList.add(0, newItem)
        _items.value = currentList
        _lastAction.value = "Elemento agregado a Sección 4: $title"
    }

    fun removeItem(item: CatalogItem) {
        val currentList = _items.value ?: mutableListOf()
        currentList.remove(item)
        _items.value = currentList
        _lastAction.value = "Elemento eliminado: ${item.title}"
    }

    fun restoreItem(position: Int, item: CatalogItem) {
        val currentList = _items.value ?: mutableListOf()
        currentList.add(position, item)
        _items.value = currentList
        _lastAction.value = "Elemento restaurado: ${item.title}"
    }

    fun clearAllItems() {
        _items.value = mutableListOf()
        _lastAction.value = "Lista vaciada completamente"
    }

    fun resetItems() {
        _items.value = createDefaultItems()
        _lastAction.value = "Lista restaurada con elementos iniciales"
    }

    fun setLastAction(action: String) {
        _lastAction.value = action
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setProgressValue(value: Int) {
        _progressValue.value = value
    }
}
