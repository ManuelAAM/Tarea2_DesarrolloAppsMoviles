import 'package:flutter/material.dart';
import '../models/catalog_item.dart';
import '../state/catalog_state.dart';

class ListsScreen extends StatefulWidget {
  final CatalogState state;

  const ListsScreen({super.key, required this.state});

  @override
  State<ListsScreen> createState() => _ListsScreenState();
}

class _ListsScreenState extends State<ListsScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 4, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  void _showDetailDialog(CatalogItem item) {
    widget.state.setLastAction('Detalle abierto: ${item.title}');
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text(item.title),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('ID Único: ${item.id}', style: Theme.of(context).textTheme.labelSmall),
              Text('Categoría: ${item.category}', style: Theme.of(context).textTheme.labelMedium),
              const SizedBox(height: 8),
              Text(item.description),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cerrar'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return ListenableBuilder(
      listenable: widget.state,
      builder: (context, _) {
        final items = widget.state.items;

        return Column(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(16, 12, 16, 4),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Sección 4: Listas y Colecciones',
                    style: theme.textTheme.headlineMedium?.copyWith(
                      color: theme.colorScheme.primary,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    'Estructuras reciclables con ListView.builder, GridView, Dismissible (swipe to delete), RefreshIndicator y TabBarView.',
                    style: theme.textTheme.bodySmall?.copyWith(
                      color: theme.colorScheme.onSurfaceVariant,
                    ),
                  ),
                  const SizedBox(height: 8),
                  TabBar(
                    controller: _tabController,
                    isScrollable: true,
                    tabAlignment: TabAlignment.start,
                    tabs: const [
                      Tab(text: 'Lista Vertical'),
                      Tab(text: 'Cuadrícula (Grid)'),
                      Tab(text: 'Con Encabezados'),
                      Tab(text: 'Deslizable (Tabs)'),
                    ],
                  ),
                ],
              ),
            ),

            Expanded(
              child: TabBarView(
                controller: _tabController,
                children: [
                  // Tab 1: Lista Vertical con Pull-to-Refresh y Dismissible
                  RefreshIndicator(
                    onRefresh: () async {
                      final messenger = ScaffoldMessenger.of(context);
                      await Future.delayed(const Duration(milliseconds: 1200));
                      widget.state.setLastAction('Lista actualizada mediante RefreshIndicator');
                      if (mounted) {
                        messenger.showSnackBar(
                          const SnackBar(
                            content: Text('Colección recargada con éxito'),
                            duration: Duration(seconds: 1),
                          ),
                        );
                      }
                    },
                    child: items.isEmpty
                        ? Center(
                            child: SingleChildScrollView(
                              physics: const AlwaysScrollableScrollPhysics(),
                              padding: const EdgeInsets.all(24.0),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Icon(
                                    Icons.inbox,
                                    size: 72,
                                    color: theme.colorScheme.secondary,
                                  ),
                                  const SizedBox(height: 12),
                                  Text(
                                    'Colección Vacía',
                                    style: theme.textTheme.titleMedium,
                                  ),
                                  const SizedBox(height: 4),
                                  Text(
                                    'No hay elementos. Agrega uno nuevo desde la Sección 1 o pulsa el botón.',
                                    textAlign: TextAlign.center,
                                    style: theme.textTheme.bodyMedium?.copyWith(
                                      color: theme.colorScheme.onSurfaceVariant,
                                    ),
                                  ),
                                  const SizedBox(height: 16),
                                  FilledButton.icon(
                                    onPressed: () => widget.state.resetToDefault(),
                                    icon: const Icon(Icons.refresh),
                                    label: const Text('Restaurar Elementos Iniciales'),
                                  ),
                                ],
                              ),
                            ),
                          )
                        : ListView.builder(
                            padding: const EdgeInsets.all(12),
                            itemCount: items.length,
                            itemBuilder: (context, index) {
                              final item = items[index];
                              return Dismissible(
                                key: Key(item.id),
                                direction: DismissDirection.horizontal,
                                background: Container(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  decoration: BoxDecoration(
                                    color: theme.colorScheme.errorContainer,
                                    borderRadius: BorderRadius.circular(12),
                                  ),
                                  alignment: Alignment.centerLeft,
                                  padding: const EdgeInsets.symmetric(horizontal: 20),
                                  child: Icon(Icons.delete, color: theme.colorScheme.onErrorContainer),
                                ),
                                secondaryBackground: Container(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  decoration: BoxDecoration(
                                    color: theme.colorScheme.errorContainer,
                                    borderRadius: BorderRadius.circular(12),
                                  ),
                                  alignment: Alignment.centerRight,
                                  padding: const EdgeInsets.symmetric(horizontal: 20),
                                  child: Icon(Icons.delete, color: theme.colorScheme.onErrorContainer),
                                ),
                                onDismissed: (direction) {
                                  final deletedItem = item;
                                  final deletedIndex = index;
                                  widget.state.removeItem(deletedItem);

                                  ScaffoldMessenger.of(context).showSnackBar(
                                    SnackBar(
                                      content: Text('Se eliminó "${deletedItem.title}"'),
                                      action: SnackBarAction(
                                        label: 'Deshacer',
                                        onPressed: () {
                                          widget.state.restoreItem(deletedItem, deletedIndex);
                                        },
                                      ),
                                    ),
                                  );
                                },
                                child: Card(
                                  margin: const EdgeInsets.symmetric(vertical: 4),
                                  child: ListTile(
                                    leading: CircleAvatar(
                                      backgroundColor: theme.colorScheme.primaryContainer,
                                      child: Icon(Icons.list, color: theme.colorScheme.onPrimaryContainer),
                                    ),
                                    title: Text(item.title, style: const TextStyle(fontWeight: FontWeight.w600)),
                                    subtitle: Text(item.description, maxLines: 2, overflow: TextOverflow.ellipsis),
                                    trailing: Chip(
                                      label: Text(item.category, style: const TextStyle(fontSize: 10)),
                                      padding: EdgeInsets.zero,
                                    ),
                                    onTap: () => _showDetailDialog(item),
                                  ),
                                ),
                              );
                            },
                          ),
                  ),

                  // Tab 2: Cuadrícula (Grid)
                  GridView.builder(
                    padding: const EdgeInsets.all(12),
                    gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      crossAxisSpacing: 8,
                      mainAxisSpacing: 8,
                      childAspectRatio: 1.1,
                    ),
                    itemCount: items.length,
                    itemBuilder: (context, index) {
                      final item = items[index];
                      return Card(
                        elevation: 1,
                        child: InkWell(
                          borderRadius: BorderRadius.circular(12),
                          onTap: () => _showDetailDialog(item),
                          child: Padding(
                            padding: const EdgeInsets.all(12.0),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Icon(Icons.grid_view, size: 36, color: theme.colorScheme.primary),
                                const SizedBox(height: 8),
                                Text(
                                  item.title,
                                  textAlign: TextAlign.center,
                                  maxLines: 2,
                                  overflow: TextOverflow.ellipsis,
                                  style: theme.textTheme.titleSmall,
                                ),
                                const SizedBox(height: 4),
                                Text(
                                  item.category,
                                  style: theme.textTheme.labelSmall?.copyWith(color: theme.colorScheme.secondary),
                                ),
                              ],
                            ),
                          ),
                        ),
                      );
                    },
                  ),

                  // Tab 3: Lista con encabezados de sección (2 tipos de widgets)
                  _buildSectionList(context, items),

                  // Tab 4: Pestañas con contenido deslizable
                  _buildSwipeableTabs(context),
                ],
              ),
            ),
          ],
        );
      },
    );
  }

  Widget _buildSectionList(BuildContext context, List<CatalogItem> items) {
    final theme = Theme.of(context);
    final Map<String, List<CatalogItem>> grouped = {};
    for (var item in items) {
      grouped.putIfAbsent(item.category, () => []).add(item);
    }

    final List<dynamic> sectionElements = [];
    grouped.forEach((category, list) {
      sectionElements.add(category); // Encabezado (Tipo 1)
      sectionElements.addAll(list);  // Elementos (Tipo 2)
    });

    return ListView.builder(
      padding: const EdgeInsets.all(12),
      itemCount: sectionElements.length,
      itemBuilder: (context, index) {
        final element = sectionElements[index];

        if (element is String) {
          // Tipo 1: Encabezado de sección
          return Container(
            margin: const EdgeInsets.only(top: 12, bottom: 6),
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
            decoration: BoxDecoration(
              color: theme.colorScheme.primaryContainer,
              borderRadius: BorderRadius.circular(8),
            ),
            child: Text(
              'CATEGORÍA: ${element.toUpperCase()}',
              style: theme.textTheme.labelLarge?.copyWith(
                color: theme.colorScheme.onPrimaryContainer,
                fontWeight: FontWeight.bold,
              ),
            ),
          );
        } else if (element is CatalogItem) {
          // Tipo 2: Elemento de datos
          return Card(
            margin: const EdgeInsets.symmetric(vertical: 3),
            child: ListTile(
              title: Text(element.title),
              subtitle: Text(element.description),
              trailing: const Icon(Icons.arrow_forward_ios, size: 14),
              onTap: () => _showDetailDialog(element),
            ),
          );
        }
        return const SizedBox.shrink();
      },
    );
  }

  Widget _buildSwipeableTabs(BuildContext context) {
    final theme = Theme.of(context);

    final pages = [
      {'title': 'Pestaña 1: Arquitectura de Widgets', 'desc': 'En Flutter, todo es un Widget. El árbol se reconstruye eficientemente gracias al desacoplamiento entre Widget, Element y RenderObject.'},
      {'title': 'Pestaña 2: Listas Reciclables', 'desc': 'ListView.builder construye dinámicamente los ítems a medida que entran al viewport, conservando recursos en listas masivas.'},
      {'title': 'Pestaña 3: Gestos Fluidos', 'desc': 'Dismissible y TabBarView integran física de amortiguación (Bouncing/ClampingScrollPhysics) idéntica a las interfaces nativas.'},
    ];

    return PageView.builder(
      itemCount: pages.length,
      itemBuilder: (context, index) {
        final page = pages[index];
        return Padding(
          padding: const EdgeInsets.all(24.0),
          child: Card(
            color: theme.colorScheme.secondaryContainer,
            child: Padding(
              padding: const EdgeInsets.all(20.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    page['title']!,
                    textAlign: TextAlign.center,
                    style: theme.textTheme.titleMedium?.copyWith(
                      color: theme.colorScheme.onSecondaryContainer,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 12),
                  Text(
                    page['desc']!,
                    textAlign: TextAlign.center,
                    style: theme.textTheme.bodyMedium?.copyWith(
                      color: theme.colorScheme.onSecondaryContainer,
                    ),
                  ),
                  const SizedBox(height: 16),
                  Text(
                    'Desliza horizontalmente (Página ${index + 1} de 3)',
                    style: theme.textTheme.labelSmall?.copyWith(color: theme.colorScheme.primary),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
