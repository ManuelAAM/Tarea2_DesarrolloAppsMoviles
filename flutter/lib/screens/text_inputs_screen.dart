import 'package:flutter/material.dart';
import '../state/catalog_state.dart';
import '../widgets/catalog_card.dart';

class TextInputsScreen extends StatefulWidget {
  final CatalogState state;

  const TextInputsScreen({super.key, required this.state});

  @override
  State<TextInputsScreen> createState() => _TextInputsScreenState();
}

class _TextInputsScreenState extends State<TextInputsScreen> {
  final _simpleController = TextEditingController();
  final _validationController = TextEditingController();
  final _passwordController = TextEditingController();
  final _multilineController = TextEditingController();

  bool _obscurePassword = true;
  String? _validationError;
  String _searchResult = 'Consulta: (Ninguna)';

  final List<String> _suggestions = [
    'Desarrollo de Aplicaciones Móviles Nativas',
    'Sistemas Distribuidos',
    'Arquitectura de Computadoras',
    'Ingeniería de Software',
    'Inteligencia Artificial',
  ];

  @override
  void dispose() {
    _simpleController.dispose();
    _validationController.dispose();
    _passwordController.dispose();
    _multilineController.dispose();
    super.dispose();
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
            'Sección 1: Entrada de Texto',
            style: theme.textTheme.headlineMedium?.copyWith(
              color: theme.colorScheme.primary,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            'Captura de datos tipográficos mediante TextField y TextFormField, control de teclado IME, expresiones regulares y autocompletado.',
            style: theme.textTheme.bodyMedium?.copyWith(
              color: theme.colorScheme.onSurfaceVariant,
            ),
          ),
          const SizedBox(height: 12),

          // 1. Campo de texto simple
          CatalogCard(
            title: '1. Campo Simple con Etiqueta Flotante',
            description: 'Permite la captura básica de caracteres con una etiqueta flotante (labelText) y un texto guía (hintText) intuitivo.',
            child: TextField(
              controller: _simpleController,
              decoration: const InputDecoration(
                labelText: 'Nombre del Componente',
                hintText: 'Ej. Botón primario',
                border: OutlineInputBorder(),
              ),
              onChanged: (val) {
                widget.state.setLastAction('Texto simple en Flutter: $val');
              },
            ),
          ),

          // 2. Campo con validación
          CatalogCard(
            title: '2. Campo con Validación y Mensaje de Error Visible',
            description: 'Supervisa dinámicamente la entrada en tiempo real y despliega un mensaje en color de error si la longitud es menor a 5 caracteres.',
            child: TextField(
              controller: _validationController,
              decoration: InputDecoration(
                labelText: 'Código de Boleta (Mínimo 5 caracteres)',
                errorText: _validationError,
                border: const OutlineInputBorder(),
              ),
              onChanged: (val) {
                setState(() {
                  if (val.isNotEmpty && val.length < 5) {
                    _validationError = 'Error: Muy corto (${val.length}/5 caracteres)';
                  } else {
                    _validationError = null;
                  }
                });
              },
            ),
          ),

          // 3. Campo de contraseña
          CatalogCard(
            title: '3. Campo de Contraseña con Toggle de Visibilidad',
            description: 'Aplica la propiedad obscureText para ocultar caracteres sensibles con un botón sufijo que conmuta la visibilidad.',
            child: TextField(
              controller: _passwordController,
              obscureText: _obscurePassword,
              decoration: InputDecoration(
                labelText: 'Contraseña de Acceso',
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: Icon(_obscurePassword ? Icons.visibility : Icons.visibility_off),
                  onPressed: () {
                    setState(() {
                      _obscurePassword = !_obscurePassword;
                    });
                  },
                ),
              ),
            ),
          ),

          // 4. Campos con tipos de teclado
          CatalogCard(
            title: '4. Campos con Distintos Tipos de Teclado (keyboardType)',
            description: 'Configura la distribución del teclado en pantalla para optimizar entradas numéricas, correos electrónicos con @ y teléfonos.',
            child: Column(
              children: [
                TextField(
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                    labelText: 'Teclado Numérico (ej. Edad)',
                    prefixIcon: Icon(Icons.pin),
                    border: OutlineInputBorder(),
                  ),
                ),
                const SizedBox(height: 8),
                TextField(
                  keyboardType: TextInputType.emailAddress,
                  decoration: const InputDecoration(
                    labelText: 'Teclado Correo (ej. alumno@ipn.mx)',
                    prefixIcon: Icon(Icons.email),
                    border: OutlineInputBorder(),
                  ),
                ),
                const SizedBox(height: 8),
                TextField(
                  keyboardType: TextInputType.phone,
                  decoration: const InputDecoration(
                    labelText: 'Teclado Telefónico (ej. 5512345678)',
                    prefixIcon: Icon(Icons.phone),
                    border: OutlineInputBorder(),
                  ),
                ),
              ],
            ),
          ),

          // 5. Campo multilínea
          CatalogCard(
            title: '5. Campo de Texto Multilínea (minLines / maxLines)',
            description: 'Adecuado para descripciones, notas extensas o redacción de párrafos con saltos de línea automáticos.',
            child: TextField(
              controller: _multilineController,
              minLines: 3,
              maxLines: 5,
              decoration: const InputDecoration(
                labelText: 'Notas u Observaciones del Alumno',
                alignLabelWithHint: true,
                border: OutlineInputBorder(),
              ),
            ),
          ),

          // 6. Campo con sugerencias automáticas (Autocomplete)
          CatalogCard(
            title: '6. Campo con Sugerencias Automáticas (Autocomplete)',
            description: 'Despliega una lista flotante reactiva que filtra opciones conformes el usuario introduce caracteres.',
            child: Autocomplete<String>(
              optionsBuilder: (TextEditingValue textEditingValue) {
                if (textEditingValue.text.isEmpty) {
                  return const Iterable<String>.empty();
                }
                return _suggestions.where((option) {
                  return option.toLowerCase().contains(textEditingValue.text.toLowerCase());
                });
              },
              onSelected: (String selection) {
                widget.state.setLastAction('Sugerencia elegida: $selection');
              },
              fieldViewBuilder: (context, controller, focusNode, onEditingComplete) {
                return TextField(
                  controller: controller,
                  focusNode: focusNode,
                  onEditingComplete: onEditingComplete,
                  decoration: const InputDecoration(
                    labelText: 'Escribe una materia (ej. Sistemas, Móviles)',
                    border: OutlineInputBorder(),
                  ),
                );
              },
            ),
          ),

          // 7. Barra de búsqueda interactiva
          CatalogCard(
            title: '7. Barra de Búsqueda Interactiva (SearchBar M3)',
            description: 'Permite búsquedas inmediatas en catálogos y dispone de botón de limpieza para reiniciar la consulta.',
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SearchBar(
                  leading: const Icon(Icons.search),
                  hintText: 'Buscar en el catálogo...',
                  onChanged: (val) {
                    setState(() {
                      _searchResult = val.isEmpty ? 'Consulta: (Ninguna)' : 'Buscando: "$val"';
                    });
                  },
                ),
                const SizedBox(height: 6),
                Text(
                  _searchResult,
                  style: theme.textTheme.labelSmall?.copyWith(color: theme.colorScheme.secondary),
                ),
              ],
            ),
          ),

          // Conexión entre Secciones: Agregar a Colección de Sección 4
          Card(
            color: theme.colorScheme.primaryContainer,
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Conexión Interactiva: Enviar a Sección 4',
                    style: theme.textTheme.titleMedium?.copyWith(
                      color: theme.colorScheme.onPrimaryContainer,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    'Los valores capturados en el Campo Simple se añadirán a la lista de la Sección 4 al pulsar el botón.',
                    style: theme.textTheme.bodyMedium?.copyWith(
                      color: theme.colorScheme.onPrimaryContainer,
                    ),
                  ),
                  const SizedBox(height: 12),
                  SizedBox(
                    width: double.infinity,
                    child: FilledButton.icon(
                      onPressed: () {
                        final title = _simpleController.text.trim();
                        final desc = _multilineController.text.trim();
                        final finalTitle = title.isEmpty ? 'Elemento desde Flutter S1' : title;
                        final finalDesc = desc.isEmpty ? 'Agregado desde el formulario de la Sección 1' : desc;

                        widget.state.addItem(finalTitle, finalDesc, 'Formulario S1');
                        _simpleController.clear();
                        _multilineController.clear();

                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: Text('¡Se agregó "$finalTitle" a la Sección 4!'),
                            behavior: SnackBarBehavior.floating,
                          ),
                        );
                      },
                      icon: const Icon(Icons.add),
                      label: const Text('Agregar Registro a Sección 4'),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
