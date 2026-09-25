import 'package:flutter/material.dart';
import '../state/catalog_state.dart';
import '../widgets/catalog_card.dart';

class ButtonsScreen extends StatefulWidget {
  final CatalogState state;

  const ButtonsScreen({super.key, required this.state});

  @override
  State<ButtonsScreen> createState() => _ButtonsScreenState();
}

class _ButtonsScreenState extends State<ButtonsScreen> {
  String _feedbackMessage = 'Respuesta: Pulsa cualquier botón para interactuar';
  Set<int> _selectedSegment = {0};
  bool _isLoading = false;

  void _triggerFeedback(String buttonName) {
    final msg = 'Respuesta visible: Pulsado "$buttonName"';
    setState(() {
      _feedbackMessage = msg;
    });
    widget.state.setLastAction(msg);
    ScaffoldMessenger.of(context).clearSnackBars();
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(msg),
        duration: const Duration(seconds: 1),
        behavior: SnackBarBehavior.floating,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Sección 2: Botones y Acciones',
            style: theme.textTheme.headlineMedium?.copyWith(
              color: theme.colorScheme.primary,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            'Componentes interactivos para disparar acciones con feedback inmediato visual y háptico.',
            style: theme.textTheme.bodyMedium?.copyWith(
              color: theme.colorScheme.onSurfaceVariant,
            ),
          ),
          const SizedBox(height: 12),

          // Banner de retroalimentación inmediata
          Card(
            color: theme.colorScheme.surfaceContainerHighest,
            child: Padding(
              padding: const EdgeInsets.all(14.0),
              child: Row(
                children: [
                  Icon(Icons.smart_button, color: theme.colorScheme.primary),
                  const SizedBox(width: 12),
                  Expanded(
                    child: Text(
                      _feedbackMessage,
                      style: theme.textTheme.labelLarge?.copyWith(
                        color: theme.colorScheme.primary,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),

          const SizedBox(height: 8),

          // 1. Jerarquía de botones
          CatalogCard(
            title: '1. Jerarquía de Énfasis: Relleno, Contorno y Solo Texto',
            description: 'Permiten jerarquizar acciones en la vista según su relevancia: primaria (FilledButton), secundaria (OutlinedButton) y terciaria (TextButton).',
            child: Row(
              children: [
                Expanded(
                  child: FilledButton(
                    onPressed: () => _triggerFeedback('FilledButton (Relleno)'),
                    child: const Text('Relleno'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: OutlinedButton(
                    onPressed: () => _triggerFeedback('OutlinedButton (Contorno)'),
                    child: const Text('Contorno'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextButton(
                    onPressed: () => _triggerFeedback('TextButton (Texto)'),
                    child: const Text('Texto'),
                  ),
                ),
              ],
            ),
          ),

          // 2. Botones con ícono
          CatalogCard(
            title: '2. Botones con Ícono (Solo Ícono y Mixto)',
            description: 'Comunican la acción mediante iconos universales, ya sea de forma compacta (IconButton) o acompañada de texto explicativo.',
            child: Row(
              children: [
                IconButton.filledTonal(
                  onPressed: () => _triggerFeedback('IconButton (Favorito)'),
                  icon: const Icon(Icons.favorite),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: FilledButton.icon(
                    onPressed: () => _triggerFeedback('FilledButton con Ícono'),
                    icon: const Icon(Icons.favorite),
                    label: const Text('Favorito con Texto'),
                  ),
                ),
              ],
            ),
          ),

          // 3. Botones de Acción Flotante (FAB)
          CatalogCard(
            title: '3. Botón de Acción Flotante (FAB Estándar y Extendido)',
            description: 'Se eleva por encima del contenido para promover la acción principal. Su variante extendida incluye etiqueta de texto.',
            child: Row(
              children: [
                FloatingActionButton(
                  heroTag: 'fab_flutter_normal',
                  onPressed: () => _triggerFeedback('FAB Normal Circular'),
                  child: const Icon(Icons.add),
                ),
                const SizedBox(width: 20),
                FloatingActionButton.extended(
                  heroTag: 'fab_flutter_extended',
                  onPressed: () => _triggerFeedback('FloatingActionButton.extended'),
                  icon: const Icon(Icons.add),
                  label: const Text('Nuevo Registro'),
                ),
              ],
            ),
          ),

          // 4. Selector segmentado
          CatalogCard(
            title: '4. Selector Segmentado (SegmentedButton)',
            description: 'Agrupa un conjunto de opciones mutuamente excluyentes en una franja horizontal continua bajo el estándar Material 3.',
            child: SizedBox(
              width: double.infinity,
              child: SegmentedButton<int>(
                segments: const [
                  ButtonSegment(value: 0, label: Text('Día')),
                  ButtonSegment(value: 1, label: Text('Semana')),
                  ButtonSegment(value: 2, label: Text('Mes')),
                ],
                selected: _selectedSegment,
                onSelectionChanged: (Set<int> newSelection) {
                  setState(() {
                    _selectedSegment = newSelection;
                  });
                  final labels = ['Día', 'Semana', 'Mes'];
                  _triggerFeedback('Segmento: ${labels[newSelection.first]}');
                },
              ),
            ),
          ),

          // 5. Botón deshabilitado y botón en estado de carga
          CatalogCard(
            title: '5. Botón Deshabilitado y Botón con Estado de Carga',
            description: 'Comunican la falta de disponibilidad de la acción (onPressed: null) o la ejecución activa de una tarea asíncrona mediante un spinner.',
            child: Row(
              children: [
                const Expanded(
                  child: FilledButton(
                    onPressed: null,
                    child: Text('Deshabilitado'),
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: FilledButton.tonal(
                    onPressed: _isLoading
                        ? null
                        : () async {
                            setState(() {
                              _isLoading = true;
                              _feedbackMessage = 'Iniciando proceso asíncrono simulado...';
                            });
                            await Future.delayed(const Duration(seconds: 2));
                            if (mounted) {
                              setState(() {
                                _isLoading = false;
                              });
                              _triggerFeedback('Carga asíncrona completada con éxito');
                            }
                          },
                    child: _isLoading
                        ? const SizedBox(
                            width: 18,
                            height: 18,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Text('Iniciar Carga'),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
