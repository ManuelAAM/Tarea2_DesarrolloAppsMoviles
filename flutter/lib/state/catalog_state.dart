import 'package:flutter/material.dart';
import '../models/catalog_item.dart';

class CatalogState extends ChangeNotifier {
  List<CatalogItem> _items = [];
  String _lastAction = 'Bienvenido al Catálogo Flutter';
  double _progressValue = 45.0;
  String _selectedCategory = 'Todas';
  ThemeMode _themeMode = ThemeMode.system;

  List<CatalogItem> get items => List.unmodifiable(_items);
  String get lastAction => _lastAction;
  double get progressValue => _progressValue;
  String get selectedCategory => _selectedCategory;
  ThemeMode get themeMode => _themeMode;

  CatalogState() {
    resetToDefault();
  }

  void resetToDefault() {
    _items = [
      CatalogItem(
        id: '1',
        title: 'TextField Simple',
        description: 'Campo de texto con InputDecoration y etiqueta flotante animada.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '2',
        title: 'TextFormField con Validación',
        description: 'Gestión declarativa de validaciones mediante la propiedad validator.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '3',
        title: 'Campo de Contraseña',
        description: 'Uso de obscureText con IconButton para alternar visibilidad.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '4',
        title: 'Teclados Especializados',
        description: 'Configuración de TextInputType (número, email, teléfono).',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '5',
        title: 'TextField Multilínea',
        description: 'Parámetros minLines y maxLines para párrafos extensos.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '6',
        title: 'Autocomplete / DropdownMenu',
        description: 'Sugerencias predictivas filtrables flotantes conforme se escribe.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '7',
        title: 'SearchBar de Material 3',
        description: 'Barra de búsqueda con vista previa desplegable y limpieza de texto.',
        category: 'Entrada',
      ),
      CatalogItem(
        id: '8',
        title: 'FilledButton',
        description: 'Botón con alto énfasis visual y color primario.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '9',
        title: 'OutlinedButton',
        description: 'Botón con borde perimetral para acciones secundarias.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '10',
        title: 'TextButton',
        description: 'Botón plano sin contenedor de fondo para acciones terciarias.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '11',
        title: 'IconButton',
        description: 'Disparador táctil circular para glifos e iconos.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '12',
        title: 'FloatingActionButton',
        description: 'FAB normal circular y FloatingActionButton.extended con etiqueta.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '13',
        title: 'SegmentedButton',
        description: 'Selector segmentado de opciones en una sola franja horizontal.',
        category: 'Botones',
      ),
      CatalogItem(
        id: '14',
        title: 'Checkbox Tri-State',
        description: 'Casilla con soporte tristate (true, false, null para indeterminado).',
        category: 'Selección',
      ),
      CatalogItem(
        id: '15',
        title: 'RadioListTile',
        description: 'Opciones de selección única excluyente agrupadas por valor.',
        category: 'Selección',
      ),
      CatalogItem(
        id: '16',
        title: 'Switch',
        description: 'Interruptor deslizante digital para alternancia binaria.',
        category: 'Selección',
      ),
    ];
    _lastAction = 'Lista restaurada con 16 elementos iniciales';
    notifyListeners();
  }

  void addItem(String title, String description, String category) {
    final newItem = CatalogItem(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      title: title,
      description: description,
      category: category,
    );
    _items.insert(0, newItem);
    _lastAction = 'Elemento agregado a Sección 4: $title';
    notifyListeners();
  }

  void removeItem(CatalogItem item) {
    _items.removeWhere((i) => i.id == item.id);
    _lastAction = 'Elemento eliminado: ${item.title}';
    notifyListeners();
  }

  void restoreItem(CatalogItem item, int index) {
    final targetIndex = index.clamp(0, _items.length);
    _items.insert(targetIndex, item);
    _lastAction = 'Elemento restaurado: ${item.title}';
    notifyListeners();
  }

  void setLastAction(String action) {
    _lastAction = action;
    notifyListeners();
  }

  void setProgressValue(double value) {
    _progressValue = value;
    notifyListeners();
  }

  void setSelectedCategory(String category) {
    _selectedCategory = category;
    notifyListeners();
  }

  void toggleTheme() {
    if (_themeMode == ThemeMode.dark) {
      _themeMode = ThemeMode.light;
    } else {
      _themeMode = ThemeMode.dark;
    }
    notifyListeners();
  }
}
