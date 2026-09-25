import React, { useState } from 'react';
import {
  View,
  Text,
  Switch,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Modal,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogCard } from '../components/CatalogCard';

interface SelectionScreenProps {
  colors: ThemeColors;
  progressValue: number;
  onSetProgress: (val: number) => void;
  onSetAction: (action: string) => void;
}

export const SelectionScreen: React.FC<SelectionScreenProps> = ({
  colors,
  progressValue,
  onSetProgress,
  onSetAction,
}) => {
  // 1. Checkboxes
  const [simpleChecked, setSimpleChecked] = useState(false);
  const [triState, setTriState] = useState<'on' | 'off' | 'indeterminate'>('indeterminate');

  // 2. Radio
  const [selectedRadio, setSelectedRadio] = useState('Básico');

  // 3. Switch
  const [switchValue, setSwitchValue] = useState(true);

  // 4. Sliders (control numérico continuo)
  const [rangeStart, setRangeStart] = useState(20);
  const [rangeEnd, setRangeEnd] = useState(80);

  // 5. Dropdown
  const categories = ['Todas las categorías', 'Básicos', 'Entrada', 'Estructura'];
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const [showDropdownModal, setShowDropdownModal] = useState(false);

  // 6. Pickers
  const [selectedDate, setSelectedDate] = useState('25/09/2026');
  const [selectedTime, setSelectedTime] = useState('14:30 hrs');
  const [showPickerModal, setShowPickerModal] = useState(false);

  // 7. Chips de filtro
  const chips = ['Android Views', 'Jetpack Compose', 'Flutter', 'React Native'];
  const [selectedChips, setSelectedChips] = useState<string[]>(['React Native']);

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.heading, { color: colors.primary }]}>
        Sección 3: Elementos de Selección
      </Text>
      <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
        Controles de estado booleano, selección única por exclusión mutua, rangos y pickers modales.
      </Text>

      {/* 1. Casillas de verificación */}
      <CatalogCard
        title="1. Casillas de Verificación (Simple y Tri-State)"
        description="Representa selección binaria y el estado indeterminado para selecciones compuestas incompletas."
        colors={colors}
      >
        <TouchableOpacity
          style={styles.checkRow}
          onPress={() => {
            setSimpleChecked(!simpleChecked);
            onSetAction(`Checkbox simple: ${!simpleChecked ? 'Marcado' : 'Desmarcado'}`);
          }}
        >
          <View
            style={[
              styles.checkboxBox,
              { borderColor: colors.primary },
              simpleChecked && { backgroundColor: colors.primary },
            ]}
          >
            {simpleChecked && <Text style={{ color: colors.onPrimary, fontWeight: '900' }}>✓</Text>}
          </View>
          <Text style={[styles.checkLabel, { color: colors.onSurface }]}>
            Acepto términos y condiciones del sistema
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[styles.checkRow, { marginTop: 8 }]}
          onPress={() => {
            const next = triState === 'indeterminate' ? 'on' : triState === 'on' ? 'off' : 'indeterminate';
            setTriState(next);
            onSetAction(`Tri-State cambiado a: ${next}`);
          }}
        >
          <View
            style={[
              styles.checkboxBox,
              { borderColor: colors.primary },
              triState !== 'off' && { backgroundColor: colors.primary },
            ]}
          >
            {triState === 'on' && <Text style={{ color: colors.onPrimary, fontWeight: '900' }}>✓</Text>}
            {triState === 'indeterminate' && <Text style={{ color: colors.onPrimary, fontWeight: '900' }}>—</Text>}
          </View>
          <Text style={[styles.checkLabel, { color: colors.onSurface }]}>
            Selección Tri-State: {triState}
          </Text>
        </TouchableOpacity>
      </CatalogCard>

      {/* 2. Radio buttons */}
      <CatalogCard
        title="2. Botones de Opción Excluyentes (Radio Group)"
        description="Selección exclusiva de una única alternativa entre varias opciones posibles."
        colors={colors}
      >
        {['Básico (Recomendado)', 'Intermedio', 'Avanzado'].map((level, idx) => {
          const isSelected = selectedRadio === level;
          return (
            <TouchableOpacity
              key={idx}
              style={styles.radioRow}
              onPress={() => {
                setSelectedRadio(level);
                onSetAction(`Nivel seleccionado: ${level}`);
              }}
            >
              <View style={[styles.radioCircle, { borderColor: colors.primary }]}>
                {isSelected && (
                  <View style={[styles.radioDot, { backgroundColor: colors.primary }]} />
                )}
              </View>
              <Text style={[styles.checkLabel, { color: colors.onSurface }]}>{level}</Text>
            </TouchableOpacity>
          );
        })}
      </CatalogCard>

      {/* 3. Switch */}
      <CatalogCard
        title="3. Interruptor Conmutador (Switch)"
        description="Alternancia digital de encendido/apagado para propiedades inmediatas."
        colors={colors}
      >
        <View style={styles.switchRow}>
          <Text style={[styles.checkLabel, { color: colors.onSurface }]}>
            Habilitar notificaciones en tiempo real
          </Text>
          <Switch
            value={switchValue}
            onValueChange={(val) => {
              setSwitchValue(val);
              onSetAction(`Switch notificaciones: ${val ? 'Activado' : 'Desactivado'}`);
            }}
          />
        </View>
      </CatalogCard>

      {/* 4. Sliders (Sincronizado con Sección 5) */}
      <CatalogCard
        title="4. Deslizador Simple y Deslizador de Rango"
        description={`El valor del slider continuo modula en tiempo real el progreso de la Sección 5 (${progressValue}%).`}
        colors={colors}
      >
        <Text style={[styles.sliderLabel, { color: colors.onSurface }]}>
          Slider Único: {progressValue}% (Sincronizado con S5)
        </Text>
        <View style={styles.sliderTrackRow}>
          <TouchableOpacity
            style={[styles.stepBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => {
              const next = Math.max(0, progressValue - 10);
              onSetProgress(next);
              onSetAction(`Progreso en S3: ${next}%`);
            }}
          >
            <Text style={{ fontWeight: '700' }}>-10%</Text>
          </TouchableOpacity>

          <View style={[styles.trackBar, { backgroundColor: colors.surfaceVariant }]}>
            <View
              style={[
                styles.trackFill,
                { width: `${progressValue}%`, backgroundColor: colors.primary },
              ]}
            />
          </View>

          <TouchableOpacity
            style={[styles.stepBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => {
              const next = Math.min(100, progressValue + 10);
              onSetProgress(next);
              onSetAction(`Progreso en S3: ${next}%`);
            }}
          >
            <Text style={{ fontWeight: '700' }}>+10%</Text>
          </TouchableOpacity>
        </View>

        <Text style={[styles.sliderLabel, { color: colors.onSurface, marginTop: 12 }]}>
          Rango Acotado: {rangeStart} a {rangeEnd}
        </Text>
        <View style={styles.rangeRow}>
          <TouchableOpacity
            style={[styles.chipTag, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => setRangeStart(Math.max(0, rangeStart - 5))}
          >
            <Text>Inicio: {rangeStart}</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.chipTag, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => setRangeEnd(Math.min(100, rangeEnd + 5))}
          >
            <Text>Fin: {rangeEnd}</Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 5. Dropdown */}
      <CatalogCard
        title="5. Lista Desplegable de Selección (Dropdown / Picker)"
        description="Ventana modal emergente con selección de categorías."
        colors={colors}
      >
        <TouchableOpacity
          style={[
            styles.dropdownBtn,
            { backgroundColor: colors.surfaceVariant, borderColor: colors.outlineVariant },
          ]}
          onPress={() => setShowDropdownModal(true)}
        >
          <Text style={{ color: colors.onSurface }}>{selectedCategory}</Text>
          <Text style={{ color: colors.secondary }}>▼</Text>
        </TouchableOpacity>
      </CatalogCard>

      {/* 6. Pickers de fecha y hora */}
      <CatalogCard
        title="6. Selectores Modales de Fecha y Hora (Pickers)"
        description="Diálogos interactivos para seleccionar fechas de calendario y tiempos horarios."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.pickerBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => setShowPickerModal(true)}
          >
            <Text style={{ color: colors.primary, fontWeight: '700' }}>📅 {selectedDate}</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.pickerBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => setShowPickerModal(true)}
          >
            <Text style={{ color: colors.primary, fontWeight: '700' }}>⏰ {selectedTime}</Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 7. Chips de filtro */}
      <CatalogCard
        title="7. Chips de Filtro Seleccionables"
        description="Etiquetas compactas con retroalimentación de estado seleccionado."
        colors={colors}
      >
        <View style={styles.chipsContainer}>
          {chips.map((chip, idx) => {
            const isSelected = selectedChips.includes(chip);
            return (
              <TouchableOpacity
                key={idx}
                style={[
                  styles.filterChip,
                  {
                    backgroundColor: isSelected ? colors.primaryContainer : colors.surfaceVariant,
                    borderColor: isSelected ? colors.primary : colors.outlineVariant,
                  },
                ]}
                onPress={() => {
                  if (isSelected) {
                    setSelectedChips(selectedChips.filter((c) => c !== chip));
                  } else {
                    setSelectedChips([...selectedChips, chip]);
                  }
                  onSetAction(`Filtro chip: ${chip}`);
                }}
              >
                <Text
                  style={{
                    color: isSelected ? colors.onPrimaryContainer : colors.onSurface,
                    fontWeight: isSelected ? '700' : '400',
                    fontSize: 12,
                  }}
                >
                  {isSelected ? `✓ ${chip}` : chip}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>
      </CatalogCard>

      {/* Modales */}
      <Modal visible={showDropdownModal} transparent={true} animationType="fade">
        <View style={styles.modalOverlay}>
          <View style={[styles.modalCard, { backgroundColor: colors.surface }]}>
            <Text style={[styles.modalTitle, { color: colors.primary }]}>
              Selecciona una Categoría
            </Text>
            {categories.map((cat, idx) => (
              <TouchableOpacity
                key={idx}
                style={styles.modalOption}
                onPress={() => {
                  setSelectedCategory(cat);
                  setShowDropdownModal(false);
                  onSetAction(`Categoría elegida: ${cat}`);
                }}
              >
                <Text style={{ color: colors.onSurface, fontSize: 14 }}>{cat}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      </Modal>

      <Modal visible={showPickerModal} transparent={true} animationType="fade">
        <View style={styles.modalOverlay}>
          <View style={[styles.modalCard, { backgroundColor: colors.surface }]}>
            <Text style={[styles.modalTitle, { color: colors.primary }]}>
              Modificar Fecha y Hora
            </Text>
            <TouchableOpacity
              style={styles.modalOption}
              onPress={() => {
                setSelectedDate('26/09/2026');
                setSelectedTime('16:00 hrs');
                setShowPickerModal(false);
                onSetAction('Fecha/hora actualizadas en modal');
              }}
            >
              <Text style={{ color: colors.onSurface }}>Elegir 26/09/2026 a las 16:00 hrs</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.modalOption}
              onPress={() => setShowPickerModal(false)}
            >
              <Text style={{ color: colors.error }}>Cerrar Selector</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>

      <View style={{ height: 40 }} />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  heading: {
    fontSize: 22,
    fontWeight: '800',
    marginBottom: 4,
  },
  subheading: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 12,
  },
  checkRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 4,
  },
  checkboxBox: {
    width: 22,
    height: 22,
    borderRadius: 4,
    borderWidth: 2,
    alignItems: 'center',
    justifyContent: 'center',
  },
  checkLabel: {
    marginLeft: 10,
    fontSize: 13,
  },
  radioRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 4,
  },
  radioCircle: {
    width: 20,
    height: 20,
    borderRadius: 10,
    borderWidth: 2,
    alignItems: 'center',
    justifyContent: 'center',
  },
  radioDot: {
    width: 10,
    height: 10,
    borderRadius: 5,
  },
  switchRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  sliderLabel: {
    fontSize: 13,
    fontWeight: '600',
    marginBottom: 6,
  },
  sliderTrackRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  trackBar: {
    flex: 1,
    height: 8,
    borderRadius: 4,
    overflow: 'hidden',
  },
  trackFill: {
    height: '100%',
  },
  stepBtn: {
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 6,
  },
  rangeRow: {
    flexDirection: 'row',
    gap: 8,
    marginTop: 6,
  },
  chipTag: {
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 6,
  },
  dropdownBtn: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 12,
    borderRadius: 8,
    borderWidth: 1,
  },
  row: {
    flexDirection: 'row',
    gap: 8,
  },
  pickerBtn: {
    flex: 1,
    padding: 12,
    borderRadius: 8,
    alignItems: 'center',
  },
  chipsContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 6,
  },
  filterChip: {
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 20,
    borderWidth: 1,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.4)',
    justifyContent: 'center',
    padding: 24,
  },
  modalCard: {
    borderRadius: 16,
    padding: 20,
  },
  modalTitle: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 12,
  },
  modalOption: {
    paddingVertical: 12,
    borderBottomWidth: StyleSheet.hairlineWidth,
    borderBottomColor: '#ddd',
  },
});
