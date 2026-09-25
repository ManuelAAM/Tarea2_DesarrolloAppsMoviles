import 'package:flutter/material.dart';
import 'screens/buttons_screen.dart';
import 'screens/containers_screen.dart';
import 'screens/feedback_screen.dart';
import 'screens/home_screen.dart';
import 'screens/lists_screen.dart';
import 'screens/selection_screen.dart';
import 'screens/text_inputs_screen.dart';
import 'state/catalog_state.dart';
import 'theme/app_theme.dart';

void main() {
  runApp(const CatalogApp());
}

class CatalogApp extends StatefulWidget {
  const CatalogApp({super.key});

  @override
  State<CatalogApp> createState() => _CatalogAppState();
}

class _CatalogAppState extends State<CatalogApp> {
  final CatalogState _state = CatalogState();

  @override
  Widget build(BuildContext context) {
    return ListenableBuilder(
      listenable: _state,
      builder: (context, _) {
        return MaterialApp(
          title: 'Catálogo UI (Flutter)',
          debugShowCheckedModeBanner: false,
          theme: AppTheme.lightTheme,
          darkTheme: AppTheme.darkTheme,
          themeMode: _state.themeMode,
          home: MainNavigationScaffold(state: _state),
        );
      },
    );
  }
}

class MainNavigationScaffold extends StatefulWidget {
  final CatalogState state;

  const MainNavigationScaffold({super.key, required this.state});

  @override
  State<MainNavigationScaffold> createState() => _MainNavigationScaffoldState();
}

class _MainNavigationScaffoldState extends State<MainNavigationScaffold> {
  int _currentIndex = 0;

  final List<String> _titles = [
    'Catálogo UI (Inicio)',
    '1. Entrada de Texto',
    '2. Botones y Acciones',
    '3. Elementos de Selección',
    '4. Listas y Colecciones',
    '5. Retroalimentación',
    '6. Contenedores y Estructura',
  ];

  void _navigateTo(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _showAboutDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Información Institucional'),
          content: const SingleChildScrollView(
            child: Text(
              'Instituto Politécnico Nacional\n'
              'Escuela Superior de Cómputo (ESCOM)\n\n'
              'Materia: Desarrollo de Aplicaciones Móviles Nativas\n'
              'Semestre: 2027-1\n'
              'Tarea 2: Catálogo de Elementos Básicos de Interfaz de Usuario\n\n'
              'Alumno: Aragón Martínez Manuel Alejandro\n'
              'Boleta: 2023630411\n'
              'Grupo: 7CV4\n'
              'Profesor: Gabriel Hurtado Avilés\n'
              'Tecnología: Flutter (Dart) con Material 3',
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Aceptar'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final isDark = widget.state.themeMode == ThemeMode.dark;

    Widget currentBody;
    switch (_currentIndex) {
      case 1:
        currentBody = TextInputsScreen(state: widget.state);
        break;
      case 2:
        currentBody = ButtonsScreen(state: widget.state);
        break;
      case 3:
        currentBody = SelectionScreen(state: widget.state);
        break;
      case 4:
        currentBody = ListsScreen(state: widget.state);
        break;
      case 5:
        currentBody = FeedbackScreen(state: widget.state);
        break;
      case 6:
        currentBody = ContainersScreen(state: widget.state);
        break;
      case 0:
      default:
        currentBody = HomeScreen(
          state: widget.state,
          onNavigate: _navigateTo,
        );
        break;
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(_titles[_currentIndex]),
        backgroundColor: theme.colorScheme.primaryContainer,
        foregroundColor: theme.colorScheme.onPrimaryContainer,
        actions: [
          IconButton(
            icon: Icon(isDark ? Icons.light_mode : Icons.dark_mode),
            tooltip: 'Cambiar modo claro/oscuro',
            onPressed: () {
              widget.state.toggleTheme();
            },
          ),
          IconButton(
            icon: const Icon(Icons.info_outline),
            tooltip: 'Información institucional',
            onPressed: () => _showAboutDialog(context),
          ),
        ],
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            DrawerHeader(
              decoration: BoxDecoration(
                color: theme.colorScheme.primary,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  const Icon(Icons.flutter_dash, size: 48, color: Colors.white),
                  const SizedBox(height: 8),
                  const Text(
                    'Catálogo UI Flutter',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    'ESCOM - DAMN Tarea 2',
                    style: TextStyle(
                      color: Colors.white.withValues(alpha: 0.85),
                      fontSize: 12,
                    ),
                  ),
                ],
              ),
            ),
            _buildDrawerTile(0, 'Inicio', Icons.home),
            _buildDrawerTile(1, '1. Entrada de Texto', Icons.text_fields),
            _buildDrawerTile(2, '2. Botones y Acciones', Icons.smart_button),
            _buildDrawerTile(3, '3. Selección', Icons.radio_button_checked),
            _buildDrawerTile(4, '4. Listas y Colecciones', Icons.list),
            _buildDrawerTile(5, '5. Retroalimentación', Icons.info),
            _buildDrawerTile(6, '6. Contenedores', Icons.grid_view),
          ],
        ),
      ),
      body: currentBody,
    );
  }

  Widget _buildDrawerTile(int index, String title, IconData icon) {
    final isSelected = _currentIndex == index;
    return ListTile(
      leading: Icon(icon, color: isSelected ? Theme.of(context).colorScheme.primary : null),
      title: Text(
        title,
        style: TextStyle(
          fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
          color: isSelected ? Theme.of(context).colorScheme.primary : null,
        ),
      ),
      selected: isSelected,
      onTap: () {
        Navigator.pop(context); // Cerrar drawer
        _navigateTo(index);
      },
    );
  }
}
