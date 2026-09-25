import 'package:flutter/material.dart';
import '../state/catalog_state.dart';
import '../widgets/catalog_card.dart';

class ContainersScreen extends StatefulWidget {
  final CatalogState state;

  const ContainersScreen({super.key, required this.state});

  @override
  State<ContainersScreen> createState() => _ContainersScreenState();
}

class _ContainersScreenState extends State<ContainersScreen> {
  int _miniNavIndex = 0;

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Sección 6: Contenedores y Estructura',
            style: theme.textTheme.headlineMedium?.copyWith(
              color: theme.colorScheme.primary,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            'Esquemas de composición bidimensional mediante Row, Column, Stack, SingleChildScrollView, AppBar y flex proporcionales.',
            style: theme.textTheme.bodyMedium?.copyWith(
              color: theme.colorScheme.onSurfaceVariant,
            ),
          ),
          const SizedBox(height: 12),

          // 1. Fila, Columna y Superposición (Stack)
          CatalogCard(
            title: '1. Distribución en Fila (Row), Columna (Column) y Superpuesta (Stack)',
            description: 'Row dispone hijos sobre el eje horizontal, Column sobre el vertical y Stack los apila en el eje Z permitiendo posiciones absolutas (Positioned).',
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('Row (Horizontal):', style: TextStyle(fontWeight: FontWeight.w600)),
                const SizedBox(height: 6),
                Row(
                  children: [
                    Expanded(
                      child: Container(
                        height: 40,
                        decoration: BoxDecoration(
                          color: theme.colorScheme.primaryContainer,
                          borderRadius: BorderRadius.circular(8),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          'Fila 1 (50%)',
                          style: TextStyle(color: theme.colorScheme.onPrimaryContainer),
                        ),
                      ),
                    ),
                    const SizedBox(width: 8),
                    Expanded(
                      child: Container(
                        height: 40,
                        decoration: BoxDecoration(
                          color: theme.colorScheme.secondaryContainer,
                          borderRadius: BorderRadius.circular(8),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          'Fila 2 (50%)',
                          style: TextStyle(color: theme.colorScheme.onSecondaryContainer),
                        ),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 12),
                const Text('Stack (Superposición):', style: TextStyle(fontWeight: FontWeight.w600)),
                const SizedBox(height: 6),
                SizedBox(
                  height: 90,
                  width: double.infinity,
                  child: Stack(
                    children: [
                      Container(
                        width: double.infinity,
                        height: double.infinity,
                        decoration: BoxDecoration(
                          color: theme.colorScheme.surfaceContainerHighest,
                          borderRadius: BorderRadius.circular(8),
                        ),
                        alignment: Alignment.center,
                        child: const Text('Capa Inferior de Fondo'),
                      ),
                      Positioned(
                        right: 8,
                        bottom: 8,
                        child: FilledButton.tonal(
                          onPressed: () {
                            widget.state.setLastAction('Pulsado botón flotante en Stack');
                          },
                          child: const Text('Capa Flotante'),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),

          // 2. Pesos proporcionales (Expanded con flex)
          CatalogCard(
            title: '2. Pesos Proporcionales (Expanded con flex)',
            description: 'Divide el espacio disponible en proporciones exactas mediante la propiedad flex (1:2:1 = 25% : 50% : 25%).',
            child: Row(
              children: [
                Expanded(
                  flex: 1,
                  child: Container(
                    height: 48,
                    decoration: BoxDecoration(
                      color: theme.colorScheme.primary,
                      borderRadius: const BorderRadius.horizontal(left: Radius.circular(8)),
                    ),
                    alignment: Alignment.center,
                    child: const Text('Flex 1 (25%)', style: TextStyle(color: Colors.white, fontSize: 11)),
                  ),
                ),
                Expanded(
                  flex: 2,
                  child: Container(
                    height: 48,
                    color: theme.colorScheme.secondary,
                    alignment: Alignment.center,
                    child: const Text('Flex 2 (50%)', style: TextStyle(color: Colors.white, fontSize: 11)),
                  ),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                    height: 48,
                    decoration: BoxDecoration(
                      color: theme.colorScheme.tertiary,
                      borderRadius: const BorderRadius.horizontal(right: Radius.circular(8)),
                    ),
                    alignment: Alignment.center,
                    child: const Text('Flex 1 (25%)', style: TextStyle(color: Colors.white, fontSize: 11)),
                  ),
                ),
              ],
            ),
          ),

          // 3. Barra Superior e Inferior Demostrativas
          CatalogCard(
            title: '3. Barras de Estructura: Superior (AppBar) e Inferior (NavigationBar)',
            description: 'Widgets estructurales que delimitan las áreas de título, acciones de cabecera y selección de rutas principales.',
            child: Column(
              children: [
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
                  decoration: BoxDecoration(
                    color: theme.colorScheme.primaryContainer,
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.grid_view, color: theme.colorScheme.onPrimaryContainer),
                          const SizedBox(width: 12),
                          Text(
                            'AppBar de Muestra',
                            style: TextStyle(
                              color: theme.colorScheme.onPrimaryContainer,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                      Icon(Icons.more_vert, color: theme.colorScheme.onPrimaryContainer),
                    ],
                  ),
                ),
                const SizedBox(height: 10),
                NavigationBar(
                  selectedIndex: _miniNavIndex,
                  onDestinationSelected: (idx) {
                    setState(() => _miniNavIndex = idx);
                    final titles = ['Inicio', 'Colección', 'Ajustes'];
                    widget.state.setLastAction('NavigationBar seleccionada: ${titles[idx]}');
                  },
                  destinations: const [
                    NavigationDestination(icon: Icon(Icons.home), label: 'Inicio'),
                    NavigationDestination(icon: Icon(Icons.list), label: 'Colección'),
                    NavigationDestination(icon: Icon(Icons.settings), label: 'Ajustes'),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
