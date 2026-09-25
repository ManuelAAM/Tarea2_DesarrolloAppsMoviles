package com.escom.tarea2.compose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.escom.tarea2.compose.ui.components.CatalogItemCard
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainersScreen(viewModel: SharedCatalogViewModel) {
    var selectedNavIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Sección 6: Contenedores y Estructura",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Disposición espacial bidimensional en Compose mediante Row, Column, Box, ScrollView, TopAppBar, NavigationBar y pesos Modifier.weight.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 1. Distribución en Fila, Columna y Superposición (Box)
        CatalogItemCard(
            title = "1. Distribución en Fila (Row), Columna (Column) y Superpuesta (Box)",
            description = "Row alinea elementos horizontalmente, Column verticalmente y Box apila elementos en el eje Z con modificadores de alineación."
        ) {
            Column {
                Text("Row (Horizontal):", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Fila 1 (50%)", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Fila 2 (50%)", color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Box Superpuesto (Stack Z-Order):", style = MaterialTheme.typography.labelMedium)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Capa Inferior de Fondo",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Button(
                        onClick = { viewModel.setLastAction("Pulsado botón flotante en Box") },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text("Capa Superior")
                    }
                }
            }
        }

        // 2. Pesos proporcionales
        CatalogItemCard(
            title = "2. Pesos Proporcionales (Modifier.weight)",
            description = "Reparte el ancho disponible de manera proporcional (1:2:1) calculando automáticamente las dimensiones de cada nodo hijo."
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Peso 1 (25%)", color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Peso 2 (50%)", color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Peso 1 (25%)", color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // 3. Barra Superior y Barra Inferior
        CatalogItemCard(
            title = "3. Estructuras Globales: TopAppBar y NavigationBar",
            description = "Scaffolding de Material 3 para barras superiores de aplicación y navegación inferior interactiva entre destinos clave."
        ) {
            Column {
                TopAppBar(
                    title = { Text("TopAppBar de Muestra", style = MaterialTheme.typography.titleSmall) },
                    navigationIcon = {
                        Icon(Icons.Default.GridView, contentDescription = null, modifier = Modifier.padding(start = 8.dp))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                NavigationBar {
                    val navItems = listOf(
                        Triple("Inicio", Icons.Default.Home, 0),
                        Triple("Colección", Icons.Default.List, 1),
                        Triple("Ajustes", Icons.Default.Settings, 2)
                    )

                    navItems.forEach { (label, icon, index) ->
                        NavigationBarItem(
                            selected = selectedNavIndex == index,
                            onClick = {
                                selectedNavIndex = index
                                viewModel.setLastAction("NavigationBar pulsada: $label")
                            },
                            icon = { Icon(icon, contentDescription = null) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    }
}
