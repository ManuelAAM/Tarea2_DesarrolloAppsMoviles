import 'package:flutter/material.dart';
import '../state/catalog_state.dart';
import '../widgets/catalog_card.dart';

class FeedbackScreen extends StatelessWidget {
  final CatalogState state;

  const FeedbackScreen({super.key, required this.state});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return ListenableBuilder(
      listenable: state,
      builder: (context, _) {
        final progressFraction = (state.progressValue / 100.0).clamp(0.0, 1.0);

        return SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Sección 5: Información y Retroalimentación',
                style: theme.textTheme.headlineMedium?.copyWith(
                  color: theme.colorScheme.primary,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Text(
                'Mecanismos para notificar al usuario, presentar imágenes locales/remotas, barras de progreso y diálogos modales.',
                style: theme.textTheme.bodyMedium?.copyWith(
                  color: theme.colorScheme.onSurfaceVariant,
                ),
              ),
              const SizedBox(height: 12),

              // 1. Textos y estilos tipográficos
              CatalogCard(
                title: '1. Jerarquía Tipográfica y Énfasis',
                description: 'Estructura la información mediante diferentes escalas tipográficas de Material 3 con combinaciones de negrita, cursiva y colores primarios.',
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Headline Medium (24sp, Negrita)',
                      style: theme.textTheme.headlineSmall?.copyWith(fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Title Medium con énfasis en color primario',
                      style: theme.textTheme.titleMedium?.copyWith(color: theme.colorScheme.primary),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Cuerpo de texto en cursiva para anotaciones secundarias',
                      style: theme.textTheme.bodyMedium?.copyWith(fontStyle: FontStyle.italic),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Pie de foto o etiqueta en tamaño pequeño (LabelSmall)',
                      style: theme.textTheme.labelSmall?.copyWith(color: theme.colorScheme.secondary),
                    ),
                  ],
                ),
              ),

              // 2. Imagen local y remota
              CatalogCard(
                title: '2. Imágenes (Local y Remota con Modos de Escalado)',
                description: 'Renderizado de recursos locales y descarga asíncrona mediante Image.network, demostrando BoxFit.cover y BoxFit.contain.',
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        Container(
                          width: 100,
                          height: 100,
                          decoration: BoxDecoration(
                            color: theme.colorScheme.surfaceContainerHighest,
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: Icon(Icons.image, size: 48, color: theme.colorScheme.primary),
                        ),
                        const SizedBox(height: 4),
                        const Text('Local (Contain)', style: TextStyle(fontSize: 11)),
                      ],
                    ),
                    Column(
                      children: [
                        ClipRRect(
                          borderRadius: BorderRadius.circular(8),
                          child: Image.network(
                            'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png',
                            width: 100,
                            height: 100,
                            fit: BoxFit.cover,
                            errorBuilder: (context, error, stackTrace) {
                              return Container(
                                width: 100,
                                height: 100,
                                color: theme.colorScheme.surfaceContainerHighest,
                                child: const Icon(Icons.broken_image),
                              );
                            },
                          ),
                        ),
                        const SizedBox(height: 4),
                        const Text('Remota (Cover)', style: TextStyle(fontSize: 11)),
                      ],
                    ),
                  ],
                ),
              ),

              // 3. Indicadores de progreso (Sincronizados con Sección 3)
              CatalogCard(
                title: '3. Indicadores de Progreso (Determinado e Indeterminado)',
                description: 'El indicador determinado refleja en tiempo real el valor ajustado en el Slider de la Sección 3 (${state.progressValue.toInt()}%).',
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Progreso Lineal Determinado (${state.progressValue.toInt()}% desde S3):',
                      style: theme.textTheme.labelMedium,
                    ),
                    const SizedBox(height: 6),
                    LinearProgressIndicator(value: progressFraction),
                    const SizedBox(height: 12),
                    Text(
                      'Progreso Lineal Indeterminado (Continuo):',
                      style: theme.textTheme.labelMedium,
                    ),
                    const SizedBox(height: 6),
                    LinearProgressIndicator(),
                    const SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Column(
                          children: [
                            CircularProgressIndicator(value: progressFraction),
                            const SizedBox(height: 6),
                            Text('Circular Fijo (${state.progressValue.toInt()}%)', style: const TextStyle(fontSize: 11)),
                          ],
                        ),
                        const Column(
                          children: [
                            CircularProgressIndicator(),
                            SizedBox(height: 6),
                            Text('Circular Continuo', style: TextStyle(fontSize: 11)),
                          ],
                        ),
                      ],
                    ),
                  ],
                ),
              ),

              // 4. Toast y Snackbar
              CatalogCard(
                title: '4. Notificaciones Breves: Mensaje Efímero y Snackbar con Acción',
                description: 'El mensaje emergente alerta de forma no intrusiva; el SnackBar proporciona una acción interactiva de Deshacer.',
                child: Row(
                  children: [
                    Expanded(
                      child: OutlinedButton(
                        onPressed: () {
                          state.setLastAction('Mensaje breve mostrado');
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text('Mensaje breve emergente (Toast-like)'),
                              duration: Duration(seconds: 1),
                              behavior: SnackBarBehavior.floating,
                            ),
                          );
                        },
                        child: const Text('Mensaje Breve'),
                      ),
                    ),
                    const SizedBox(width: 8),
                    Expanded(
                      child: FilledButton(
                        onPressed: () {
                          state.setLastAction('Snackbar con acción desplegado');
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: const Text('Elemento archivado'),
                              behavior: SnackBarBehavior.floating,
                              action: SnackBarAction(
                                label: 'Deshacer',
                                onPressed: () {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    const SnackBar(content: Text('Acción deshecha con éxito')),
                                  );
                                },
                              ),
                            ),
                          );
                        },
                        child: const Text('Snackbar Acción'),
                      ),
                    ),
                  ],
                ),
              ),

              // 5. Diálogo de confirmación y Modal BottomSheet
              CatalogCard(
                title: '5. Diálogo Modal de Confirmación y Hoja Inferior (BottomSheet)',
                description: 'Interrumpen el flujo para requerir una decisión explícita (showDialog) o presentar una bandeja deslizante de acciones (showModalBottomSheet).',
                child: Row(
                  children: [
                    Expanded(
                      child: FilledButton.tonal(
                        onPressed: () {
                          showDialog(
                            context: context,
                            builder: (context) {
                              return AlertDialog(
                                title: const Text('Confirmar Operación'),
                                content: const Text('¿Deseas sincronizar los registros del catálogo con la nube?'),
                                actions: [
                                  TextButton(
                                    onPressed: () {
                                      Navigator.pop(context);
                                      state.setLastAction('Diálogo: Operación cancelada');
                                    },
                                    child: const Text('Cancelar'),
                                  ),
                                  FilledButton(
                                    onPressed: () {
                                      Navigator.pop(context);
                                      state.setLastAction('Diálogo: Operación confirmada');
                                      ScaffoldMessenger.of(context).showSnackBar(
                                        const SnackBar(content: Text('Operación confirmada')),
                                      );
                                    },
                                    child: const Text('Confirmar'),
                                  ),
                                ],
                              );
                            },
                          );
                        },
                        child: const Text('Abrir Diálogo'),
                      ),
                    ),
                    const SizedBox(width: 8),
                    Expanded(
                      child: FilledButton.tonal(
                        onPressed: () {
                          state.setLastAction('BottomSheet modal abierto');
                          showModalBottomSheet(
                            context: context,
                            builder: (context) {
                              return Padding(
                                padding: const EdgeInsets.all(24.0),
                                child: Column(
                                  mainAxisSize: MainAxisSize.min,
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      'Hoja Inferior Modal (BottomSheet)',
                                      style: theme.textTheme.titleLarge,
                                    ),
                                    const SizedBox(height: 8),
                                    const Text(
                                      'showModalBottomSheet despliega una superficie desde la parte inferior para mostrar acciones contextuales o configuraciones rápidas.',
                                    ),
                                    const SizedBox(height: 16),
                                    SizedBox(
                                      width: double.infinity,
                                      child: FilledButton(
                                        onPressed: () => Navigator.pop(context),
                                        child: const Text('Cerrar Hoja'),
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            },
                          );
                        },
                        child: const Text('Abrir BottomSheet'),
                      ),
                    ),
                  ],
                ),
              ),

              // 6. Tarjeta, Separador y Distintivo Numérico (Badge)
              CatalogCard(
                title: '6. Tarjeta (Card), Separador (Divider) y Badge',
                description: 'Estructuración visual en superficies con elevación, demarcadores lineales y distintivos de conteo dinámico.',
                child: Card(
                  color: theme.colorScheme.surfaceContainerHighest,
                  child: Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              'Notificaciones Activas',
                              style: theme.textTheme.titleMedium,
                            ),
                            Badge(
                              label: Text('${state.items.length}'),
                              child: const Icon(Icons.notifications),
                            ),
                          ],
                        ),
                        const Divider(height: 20),
                        Text(
                          'El distintivo Badge muestra la cantidad viva de elementos registrados en la colección (${state.items.length}).',
                          style: theme.textTheme.bodySmall,
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
