import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogCard } from '../components/CatalogCard';

interface TextInputsScreenProps {
  colors: ThemeColors;
  onAddItem: (title: string, desc: string, category: string) => void;
  onSetAction: (action: string) => void;
}

export const TextInputsScreen: React.FC<TextInputsScreenProps> = ({
  colors,
  onAddItem,
  onSetAction,
}) => {
  const [simpleText, setSimpleText] = useState('');
  const [validationText, setValidationText] = useState('');
  const [passwordText, setPasswordText] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const [numericText, setNumericText] = useState('');
  const [emailText, setEmailText] = useState('');
  const [phoneText, setPhoneText] = useState('');
  const [multilineText, setMultilineText] = useState('');

  const [searchQuery, setSearchQuery] = useState('');
  const [searchStatus, setSearchStatus] = useState('Consulta: (Ninguna)');

  const suggestions = [
    'Desarrollo Móvil Nativo',
    'Sistemas Distribuidos',
    'Arquitectura de Software',
    'Inteligencia Artificial',
  ];
  const [filteredSuggestions, setFilteredSuggestions] = useState<string[]>([]);

  const isError = validationText.length > 0 && validationText.length < 5;

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.heading, { color: colors.primary }]}>
        Sección 1: Entrada de Texto
      </Text>
      <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
        Componentes TextInput de React Native configurados con propiedades semánticas de teclado, ocultamiento de caracteres y validación en línea.
      </Text>

      {/* 1. Campo simple */}
      <CatalogCard
        title="1. Campo Simple con Etiqueta / Placeholder"
        description="Captura texto alfanumérico estándar en una línea con texto de guía interactivo."
        colors={colors}
      >
        <TextInput
          style={[styles.input, { borderColor: colors.outlineVariant, color: colors.onSurface }]}
          placeholder="Nombre del Componente"
          placeholderTextColor={colors.outline}
          value={simpleText}
          onChangeText={(val) => {
            setSimpleText(val);
            onSetAction(`Texto simple React Native: ${val}`);
          }}
        />
      </CatalogCard>

      {/* 2. Campo con validación */}
      <CatalogCard
        title="2. Campo con Validación y Mensaje de Error Visible"
        description="Supervisa dinámicamente la entrada y alerta en color rojo ante cadenas menores a 5 caracteres."
        colors={colors}
      >
        <TextInput
          style={[
            styles.input,
            {
              borderColor: isError ? colors.error : colors.outlineVariant,
              color: colors.onSurface,
            },
          ]}
          placeholder="Código de Boleta (Mínimo 5 caracteres)"
          placeholderTextColor={colors.outline}
          value={validationText}
          onChangeText={setValidationText}
        />
        {isError && (
          <Text style={[styles.errorText, { color: colors.error }]}>
            Error: Longitud insuficiente ({validationText.length}/5 caracteres)
          </Text>
        )}
      </CatalogCard>

      {/* 3. Campo de contraseña */}
      <CatalogCard
        title="3. Campo de Contraseña con Toggle de Visibilidad"
        description="Utiliza secureTextEntry={!showPassword} y un botón para conmutar la visibilidad."
        colors={colors}
      >
        <View style={styles.passwordContainer}>
          <TextInput
            style={[
              styles.input,
              { flex: 1, borderColor: colors.outlineVariant, color: colors.onSurface },
            ]}
            placeholder="Contraseña de acceso"
            placeholderTextColor={colors.outline}
            secureTextEntry={!showPassword}
            value={passwordText}
            onChangeText={setPasswordText}
          />
          <TouchableOpacity
            style={[styles.toggleBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => setShowPassword(!showPassword)}
          >
            <Text style={{ color: colors.onSurfaceVariant, fontWeight: '700' }}>
              {showPassword ? 'Ocultar' : 'Ver'}
            </Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 4. Tipos de teclado */}
      <CatalogCard
        title="4. Campos con Distintos Tipos de Teclado (keyboardType)"
        description="Optimizan la entrada mediante teclados numéricos, de correo electrónico y telefónicos."
        colors={colors}
      >
        <TextInput
          style={[styles.input, { borderColor: colors.outlineVariant, color: colors.onSurface, marginBottom: 8 }]}
          placeholder="Teclado Numérico (keyboardType='numeric')"
          placeholderTextColor={colors.outline}
          keyboardType="numeric"
          value={numericText}
          onChangeText={setNumericText}
        />
        <TextInput
          style={[styles.input, { borderColor: colors.outlineVariant, color: colors.onSurface, marginBottom: 8 }]}
          placeholder="Teclado Correo (keyboardType='email-address')"
          placeholderTextColor={colors.outline}
          keyboardType="email-address"
          value={emailText}
          onChangeText={setEmailText}
        />
        <TextInput
          style={[styles.input, { borderColor: colors.outlineVariant, color: colors.onSurface }]}
          placeholder="Teclado Teléfono (keyboardType='phone-pad')"
          placeholderTextColor={colors.outline}
          keyboardType="phone-pad"
          value={phoneText}
          onChangeText={setPhoneText}
        />
      </CatalogCard>

      {/* 5. Multilínea */}
      <CatalogCard
        title="5. Campo de Texto Multilínea (multiline={true})"
        description="Habilita la redacción de notas extensas con múltiples renglones."
        colors={colors}
      >
        <TextInput
          style={[
            styles.input,
            {
              height: 80,
              textAlignVertical: 'top',
              borderColor: colors.outlineVariant,
              color: colors.onSurface,
            },
          ]}
          placeholder="Observaciones y notas del alumno..."
          placeholderTextColor={colors.outline}
          multiline={true}
          numberOfLines={4}
          value={multilineText}
          onChangeText={setMultilineText}
        />
      </CatalogCard>

      {/* 6. Sugerencias automáticas */}
      <CatalogCard
        title="6. Campo con Sugerencias Automáticas"
        description="Filtra en tiempo real un conjunto de sugerencias predefinidas conforme el usuario escribe."
        colors={colors}
      >
        <TextInput
          style={[styles.input, { borderColor: colors.outlineVariant, color: colors.onSurface }]}
          placeholder="Escribe: Sistemas, Móvil, Redes..."
          placeholderTextColor={colors.outline}
          onChangeText={(val) => {
            if (val.trim().length > 0) {
              setFilteredSuggestions(
                suggestions.filter((s) => s.toLowerCase().includes(val.toLowerCase()))
              );
            } else {
              setFilteredSuggestions([]);
            }
          }}
        />
        {filteredSuggestions.map((item, idx) => (
          <TouchableOpacity
            key={idx}
            style={[styles.suggestionItem, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => {
              onSetAction(`Sugerencia seleccionada: ${item}`);
              setFilteredSuggestions([]);
            }}
          >
            <Text style={{ color: colors.onSurfaceVariant }}>{item}</Text>
          </TouchableOpacity>
        ))}
      </CatalogCard>

      {/* 7. Barra de búsqueda */}
      <CatalogCard
        title="7. Barra de Búsqueda Interactiva"
        description="Entrada con botón de limpieza y consulta reactiva activa."
        colors={colors}
      >
        <View style={styles.searchRow}>
          <TextInput
            style={[
              styles.input,
              { flex: 1, borderColor: colors.outlineVariant, color: colors.onSurface },
            ]}
            placeholder="Buscar componentes..."
            placeholderTextColor={colors.outline}
            value={searchQuery}
            onChangeText={(val) => {
              setSearchQuery(val);
              setSearchStatus(val ? `Buscando: "${val}"` : 'Consulta: (Ninguna)');
            }}
          />
          {searchQuery.length > 0 && (
            <TouchableOpacity
              style={[styles.clearBtn, { backgroundColor: colors.surfaceVariant }]}
              onPress={() => {
                setSearchQuery('');
                setSearchStatus('Consulta limpiada');
              }}
            >
              <Text style={{ color: colors.onSurfaceVariant }}>✕</Text>
            </TouchableOpacity>
          )}
        </View>
        <Text style={[styles.statusText, { color: colors.secondary }]}>
          {searchStatus}
        </Text>
      </CatalogCard>

      {/* Conexión con Sección 4 */}
      <View
        style={[
          styles.connectCard,
          {
            backgroundColor: colors.primaryContainer,
            borderColor: colors.primary,
          },
        ]}
      >
        <Text style={[styles.connectTitle, { color: colors.onPrimaryContainer }]}>
          Conexión Interactiva: Enviar a Sección 4
        </Text>
        <Text style={[styles.connectDesc, { color: colors.onPrimaryContainer }]}>
          Captura el título en el primer campo y presiona el botón inferior para ingresarlo a la lista de la Sección 4.
        </Text>
        <TouchableOpacity
          style={[styles.submitBtn, { backgroundColor: colors.primary }]}
          onPress={() => {
            const title = simpleText.trim() || 'Elemento desde React Native S1';
            const desc = multilineText.trim() || 'Agregado desde formulario React Native';
            onAddItem(title, desc, 'Formulario S1');
            setSimpleText('');
            setMultilineText('');
            Alert.alert('Éxito', `Se agregó "${title}" a la lista de la Sección 4.`);
          }}
        >
          <Text style={[styles.submitBtnText, { color: colors.onPrimary }]}>
            + Agregar a Lista de Sección 4
          </Text>
        </TouchableOpacity>
      </View>

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
  input: {
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    fontSize: 14,
  },
  errorText: {
    fontSize: 12,
    marginTop: 4,
  },
  passwordContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  toggleBtn: {
    marginLeft: 8,
    paddingHorizontal: 12,
    paddingVertical: 12,
    borderRadius: 8,
  },
  suggestionItem: {
    padding: 10,
    borderRadius: 6,
    marginTop: 4,
  },
  searchRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  clearBtn: {
    marginLeft: 8,
    paddingHorizontal: 12,
    paddingVertical: 12,
    borderRadius: 8,
  },
  statusText: {
    fontSize: 12,
    marginTop: 6,
  },
  connectCard: {
    borderRadius: 12,
    borderWidth: 1,
    padding: 16,
    marginVertical: 16,
  },
  connectTitle: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 4,
  },
  connectDesc: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 12,
  },
  submitBtn: {
    borderRadius: 8,
    paddingVertical: 12,
    alignItems: 'center',
  },
  submitBtnText: {
    fontWeight: '700',
    fontSize: 14,
  },
});
