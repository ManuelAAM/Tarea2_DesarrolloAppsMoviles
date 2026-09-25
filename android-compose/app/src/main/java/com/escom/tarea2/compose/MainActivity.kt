package com.escom.tarea2.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.escom.tarea2.compose.ui.screens.ButtonsScreen
import com.escom.tarea2.compose.ui.screens.ContainersScreen
import com.escom.tarea2.compose.ui.screens.FeedbackScreen
import com.escom.tarea2.compose.ui.screens.HomeScreen
import com.escom.tarea2.compose.ui.screens.ListsScreen
import com.escom.tarea2.compose.ui.screens.SelectionScreen
import com.escom.tarea2.compose.ui.screens.TextInputsScreen
import com.escom.tarea2.compose.ui.theme.AndroidComposeCatalogTheme
import com.escom.tarea2.compose.viewmodel.SharedCatalogViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: SharedCatalogViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemDark) }

            AndroidComposeCatalogTheme(darkTheme = isDarkMode) {
                MainAppScreen(
                    viewModel = viewModel,
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    viewModel: SharedCatalogViewModel,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var currentRoute by remember { mutableStateOf("home") }
    var showAboutDialog by remember { mutableStateOf(false) }

    val navItems = listOf(
        Triple("Inicio", "home", Icons.Default.Home),
        Triple("1. Entrada de Texto", "text_inputs", Icons.Default.TextFields),
        Triple("2. Botones y Acciones", "buttons", Icons.Default.SmartButton),
        Triple("3. Selección", "selection", Icons.Default.RadioButtonChecked),
        Triple("4. Listas y Colecciones", "lists", Icons.Default.List),
        Triple("5. Retroalimentación", "feedback", Icons.Default.Info),
        Triple("6. Contenedores", "containers", Icons.Default.GridView)
    )

    val currentTitle = navItems.find { it.second == currentRoute }?.first ?: "Catálogo Compose"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Catálogo UI Compose",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ESCOM - DAMN Tarea 2",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                navItems.forEach { (label, route, icon) ->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(label) },
                        selected = currentRoute == route,
                        onClick = {
                            currentRoute = route
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentTitle) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = onToggleTheme) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Cambiar tema"
                            )
                        }
                        IconButton(onClick = { showAboutDialog = true }) {
                            Icon(Icons.Default.Info, contentDescription = "Acerca de")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentRoute) {
                    "home" -> HomeScreen(viewModel = viewModel, onNavigateToSection = { currentRoute = it })
                    "text_inputs" -> TextInputsScreen(viewModel = viewModel)
                    "buttons" -> ButtonsScreen(viewModel = viewModel)
                    "selection" -> SelectionScreen(viewModel = viewModel)
                    "lists" -> ListsScreen(viewModel = viewModel)
                    "feedback" -> FeedbackScreen(viewModel = viewModel, snackbarHostState = snackbarHostState)
                    "containers" -> ContainersScreen(viewModel = viewModel)
                }
            }
        }
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("Información Institucional") },
            text = {
                Text(
                    "Instituto Politécnico Nacional\n" +
                    "Escuela Superior de Cómputo (ESCOM)\n\n" +
                    "Materia: Desarrollo de Aplicaciones Móviles Nativas\n" +
                    "Semestre: 2027-1\n" +
                    "Tarea 2: Catálogo de Elementos Básicos de Interfaz de Usuario\n\n" +
                    "Alumno: Aragón Martínez Manuel Alejandro\n" +
                    "Boleta: 2023630411\n" +
                    "Grupo: 7CV4\n" +
                    "Profesor: Gabriel Hurtado Avilés"
                )
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}
