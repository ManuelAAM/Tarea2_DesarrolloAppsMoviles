import 'package:flutter/material.dart';
import '../state/catalog_state.dart';
import '../widgets/catalog_card.dart';

class SelectionScreen extends StatefulWidget {
  final CatalogState state;

  const SelectionScreen({super.key, required this.state});

  @override
  State<SelectionScreen> createState() => _SelectionScreenState();
}

class _SelectionScreenState extends State<SelectionScreen> {
  // 1. Checkboxes
  bool _simpleChecked = false;
  bool? _triState;

  // 2. Radio
  int _selectedRadio = 1;

  // 3. Switch
  bool _switchValue = true;

  // 4. RangeSlider
  RangeValues _rangeValues = const RangeValues(20, 80);

  // 5. Dropdown
  final List<String> _categories = [
    'Todas las categorías',
    'Componentes Básicos',
    'Entradas y Formularios',
    'Navegación y Estructura',
  ];
  late String _selectedCategory;

  // 6. Pickers
  String _selectedDateText = 'Fecha no seleccionada';
  String _selectedTimeText = 'Hora no seleccionada';

  // 7. FilterChips
  final Set<String> _selectedChips = {'Flutter'};

  @override
  void initState() {
    super.initState();
    _selectedCategory = _categories[0];
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return ListenableBuilder(
      listenable: widget.state,
      builder: (context, _) {
        return SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Sección 3: Elementos de Selección',
                style: theme.textTheme.headlineMedium?.copyWith(
                  color: theme.colorScheme.primary,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Text(
                'Widgets de selección booleana, exclusión mutua, rangos analógicos en rieles continuos y selectores modales.',
                style: theme.textTheme.bodyMedium?.copyWith(
                  color: theme.colorScheme.onSurfaceVariant,
                ),
              ),
              const SizedBox(height: 12),

              // 1. Casillas de verificación (Simple y Tristate)
              CatalogCard(
                title: '1. Casillas de Verificación (Simple y Tristate)',
                description: 'Permiten selección binaria o ternaria. El parámetro tristate: true habilita el estado null para selecciones indeterminadas.',
                child: Column(
                  children: [
                    CheckboxListTile(
                      title: const Text('Acepto términos y condiciones del sistema'),
                      value: _simpleChecked,
                      onChanged: (val) {
                        setState(() => _simpleChecked = val ?? false);
                        widget.state.setLastAction('Checkbox simple: ${_simpleChecked ? "Marcado" : "Desmarcado"}');
                      },
                      controlAffinity: ListTileControlAffinity.leading,
                      contentPadding: EdgeInsets.zero,
                    ),
                    CheckboxListTile(
                      title: Text(
                        'Selección de subelementos (Tristate: ${_triState == null ? "Indeterminado" : (_triState! ? "Marcado" : "Desmarcado")})',
                      ),
                      tristate: true,
                      value: _triState,
                      onChanged: (val) {
                        setState(() => _triState = val);
                        widget.state.setLastAction('Tristate cambiado a: $_triState');
                      },
                      controlAffinity: ListTileControlAffinity.leading,
                      contentPadding: EdgeInsets.zero,
                    ),
                  ],
                ),
              ),

              // 2. Botones de opción (Radio)
              CatalogCard(
                title: '2. Botones de Opción Excluyentes (RadioGroup)',
                description: 'Organizan alternativas mutuamente excluyentes en donde seleccionar un ítem desactiva automáticamente a los otros.',
                child: RadioGroup<int>(
                  groupValue: _selectedRadio,
                  onChanged: (val) {
                    if (val != null) {
                      setState(() => _selectedRadio = val);
                      final levels = {1: 'Básico', 2: 'Intermedio', 3: 'Avanzado'};
                      widget.state.setLastAction('Nivel elegido: ${levels[val]}');
                    }
                  },
                  child: const Column(
                    children: [
                      RadioListTile<int>(
                        title: Text('Nivel Básico (Recomendado)'),
                        value: 1,
                        contentPadding: EdgeInsets.zero,
                      ),
                      RadioListTile<int>(
                        title: Text('Nivel Intermedio'),
                        value: 2,
                        contentPadding: EdgeInsets.zero,
                      ),
                      RadioListTile<int>(
                        title: Text('Nivel Avanzado'),
                        value: 3,
                        contentPadding: EdgeInsets.zero,
                      ),
                    ],
                  ),
                ),
              ),

              // 3. Switch
              CatalogCard(
                title: '3. Interruptor Conmutador (Switch)',
                description: 'Dispositivo deslizante digital para alternar propiedades booleanas sin necesidad de confirmación adicional.',
                child: SwitchListTile(
                  title: const Text('Habilitar notificaciones en tiempo real'),
                  value: _switchValue,
                  onChanged: (val) {
                    setState(() => _switchValue = val);
                    widget.state.setLastAction('Switch notificaciones: ${val ? "Activado" : "Desactivado"}');
                  },
                  contentPadding: EdgeInsets.zero,
                ),
              ),

              // 4. Sliders (Sincronizado con Sección 5)
              CatalogCard(
                title: '4. Deslizador Simple y Deslizador de Rango (RangeSlider)',
                description: 'El valor del Slider simple se propaga en tiempo real a la Sección 5 (${widget.state.progressValue.toInt()}%). El RangeSlider acota un intervalo.',
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Slider Único: ${widget.state.progressValue.toInt()}% (Afecta Sección 5)',
                      style: theme.textTheme.labelMedium,
                    ),
                    Slider(
                      value: widget.state.progressValue,
                      min: 0,
                      max: 100,
                      divisions: 100,
                      label: '${widget.state.progressValue.toInt()}%',
                      onChanged: (val) {
                        widget.state.setProgressValue(val);
                        widget.state.setLastAction('Progreso modificado en S3: ${val.toInt()}%');
                      },
                    ),
                    const SizedBox(height: 8),
                    Text(
                      'RangeSlider: ${_rangeValues.start.toInt()} a ${_rangeValues.end.toInt()}',
                      style: theme.textTheme.labelMedium,
                    ),
                    RangeSlider(
                      values: _rangeValues,
                      min: 0,
                      max: 100,
                      divisions: 100,
                      labels: RangeLabels(
                        '${_rangeValues.start.toInt()}',
                        '${_rangeValues.end.toInt()}',
                      ),
                      onChanged: (values) {
                        setState(() => _rangeValues = values);
                      },
                    ),
                  ],
                ),
              ),

              // 5. Dropdown
              CatalogCard(
                title: '5. Lista Desplegable de Selección (DropdownButtonFormField)',
                description: 'Comprime las opciones disponibles en un menú emergente elegante integrado en un formulario con validación.',
                child: DropdownButtonFormField<String>(
                  initialValue: _selectedCategory,
                  decoration: const InputDecoration(
                    labelText: 'Categoría Seleccionada',
                    border: OutlineInputBorder(),
                  ),
                  items: _categories.map((cat) {
                    return DropdownMenuItem(value: cat, child: Text(cat));
                  }).toList(),
                  onChanged: (val) {
                    if (val != null) {
                      setState(() => _selectedCategory = val);
                      widget.state.setSelectedCategory(val);
                      widget.state.setLastAction('Categoría seleccionada en S3: $val');
                    }
                  },
                ),
              ),

              // 6. Selectores de Fecha y Hora (Pickers)
              CatalogCard(
                title: '6. Selectores Modales de Fecha y Hora (Pickers)',
                description: 'Diálogos nativos de Material 3 para seleccionar días en calendarios y tiempos horarios con reloj interactivo.',
                child: Column(
                  children: [
                    Row(
                      children: [
                        Expanded(
                          child: OutlinedButton.icon(
                            onPressed: () async {
                              final pickedDate = await showDatePicker(
                                context: context,
                                initialDate: DateTime.now(),
                                firstDate: DateTime(2020),
                                lastDate: DateTime(2030),
                              );
                              if (pickedDate != null) {
                                final formatted = '${pickedDate.day.toString().padLeft(2, '0')}/${pickedDate.month.toString().padLeft(2, '0')}/${pickedDate.year}';
                                setState(() => _selectedDateText = 'Fecha: $formatted');
                                widget.state.setLastAction('Fecha elegida en S3: $formatted');
                              }
                            },
                            icon: const Icon(Icons.calendar_month),
                            label: const Text('Elegir Fecha'),
                          ),
                        ),
                        const SizedBox(width: 8),
                        Expanded(
                          child: OutlinedButton.icon(
                            onPressed: () async {
                              final pickedTime = await showTimePicker(
                                context: context,
                                initialTime: TimeOfDay.now(),
                              );
                              if (pickedTime != null) {
                                final formatted = '${pickedTime.hour.toString().padLeft(2, '0')}:${pickedTime.minute.toString().padLeft(2, '0')} hrs';
                                setState(() => _selectedTimeText = 'Hora: $formatted');
                                widget.state.setLastAction('Hora elegida en S3: $formatted');
                              }
                            },
                            icon: const Icon(Icons.schedule),
                            label: const Text('Elegir Hora'),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Text(
                      '$_selectedDateText | $_selectedTimeText',
                      style: theme.textTheme.labelSmall?.copyWith(color: theme.colorScheme.secondary),
                    ),
                  ],
                ),
              ),

              // 7. FilterChips
              CatalogCard(
                title: '7. Chips de Filtro Seleccionables (FilterChip)',
                description: 'Pastillas compactas con retroalimentación visual de selección activa mediante marca de verificación.',
                child: Wrap(
                  spacing: 8.0,
                  children: ['Android Views', 'Jetpack Compose', 'Flutter', 'React Native'].map((tech) {
                    final isSelected = _selectedChips.contains(tech);
                    return FilterChip(
                      label: Text(tech),
                      selected: isSelected,
                      onSelected: (selected) {
                        setState(() {
                          if (selected) {
                            _selectedChips.add(tech);
                          } else {
                            _selectedChips.remove(tech);
                          }
                        });
                        widget.state.setLastAction('Filtro chip: $tech (${selected ? "Activo" : "Inactivo"})');
                      },
                    );
                  }).toList(),
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
