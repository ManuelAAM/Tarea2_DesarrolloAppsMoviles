import 'package:flutter/material.dart';
import '../state/catalog_state.dart';

class HomeScreen extends StatelessWidget {
  final CatalogState state;
  final Function(int) onNavigate;

  const HomeScreen({
    super.key,
    required this.state,
    required this.onNavigate,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return ListenableBuilder(
      listenable: state,
      builder: (context, _) {
        return SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Banner institucional
              Card(
                color: theme.colorScheme.primaryContainer,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'ESCOM - IPN | DAMN',
                        style: theme.textTheme.labelLarge?.copyWith(
                          color: theme.colorScheme.onPrimaryContainer,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Catálogo UI en Flutter (Dart)',
                        style: theme.textTheme.headlineSmall?.copyWith(
                          fontWeight: FontWeight.bold,
                          color: theme.colorScheme.onPrimaryContainer,
                        ),
                      ),
                      const SizedBox(height: 8),
                      Text(
                        'Construcción de interfaces móviles con árbol reactivo de widgets (Material 3). Cubre más de 35 elementos interactivos en seis categorías, adaptación a modo claro/oscuro y reactividad en tiempo real.',
                        style: theme.textTheme.bodyMedium?.copyWith(
                          color: theme.colorScheme.onPrimaryContainer,
                        ),
                      ),
                      const SizedBox(height: 12),
                      Text(
                        'Alumno: Aragón Martínez Manuel Alejandro | Boleta: 2023630411 | Grupo: 7CV4',
                        style: theme.textTheme.labelSmall?.copyWith(
                          color: theme.colorScheme.onPrimaryContainer,
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // Tarjeta de estado reactivo global
              Card(
                elevation: 0,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                  side: BorderSide(color: theme.colorScheme.outlineVariant),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Estado Global de la Aplicación',
                        style: theme.textTheme.titleSmall?.copyWith(
                          color: theme.colorScheme.primary,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Última acción: ${state.lastAction}',
                        style: theme.textTheme.bodyMedium,
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Total de elementos en la colección: ${state.items.length}',
                        style: theme.textTheme.labelSmall?.copyWith(
                          color: theme.colorScheme.secondary,
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              const SizedBox(height: 20),

              Text(
                'Secciones del Catálogo',
                style: theme.textTheme.titleMedium,
              ),

              const SizedBox(height: 12),

              _buildNavButton(
                context: context,
                index: 1,
                title: '1. Entrada de Texto (Campos, Validación, Teclados)',
                icon: Icons.text_fields,
              ),
              _buildNavButton(
                context: context,
                index: 2,
                title: '2. Botones y Acciones (Filled, Outlined, FAB, Loading)',
                icon: Icons.smart_button,
              ),
              _buildNavButton(
                context: context,
                index: 3,
                title: '3. Elementos de Selección (Check, Radio, Sliders, Pickers)',
                icon: Icons.radio_button_checked,
              ),
              _buildNavButton(
                context: context,
                index: 4,
                title: '4. Listas y Colecciones (ListView, Grid, Swipe, Tabs)',
                icon: Icons.list,
              ),
              _buildNavButton(
                context: context,
                index: 5,
                title: '5. Información y Retroalimentación (Progress, Modales)',
                icon: Icons.info,
              ),
              _buildNavButton(
                context: context,
                index: 6,
                title: '6. Contenedores y Estructura (Row, Column, Stack, Flex)',
                icon: Icons.grid_view,
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildNavButton({
    required BuildContext context,
    required int index,
    required String title,
    required IconData icon,
  }) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: FilledButton.tonal(
        onPressed: () => onNavigate(index),
        style: FilledButton.styleFrom(
          alignment: Alignment.centerLeft,
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        ),
        child: Row(
          children: [
            Icon(icon),
            const SizedBox(width: 12),
            Expanded(
              child: Text(
                title,
                style: const TextStyle(fontWeight: FontWeight.w500),
              ),
            ),
            const Icon(Icons.arrow_forward_ios, size: 14),
          ],
        ),
      ),
    );
  }
}
