package com.escom.tarea2.views.data

data class CatalogItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val iconRes: Int = android.R.drawable.ic_menu_agenda
)

data class SectionItem(
    val title: String,
    val isHeader: Boolean = false,
    val description: String = "",
    val badge: String = ""
)
