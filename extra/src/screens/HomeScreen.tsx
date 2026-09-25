import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogItem } from '../types';

interface HomeScreenProps {
  colors: ThemeColors;
  items: CatalogItem[];
  lastAction: string;
  onNavigate: (route: string) => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({
  colors,
  items,
  lastAction,
  onNavigate,
}) => {
  const sections = [
    { title: '1. Entrada de Texto (Campos, Validación, Teclados)', route: 'text_inputs' },
    { title: '2. Botones y Acciones (Filled, Outlined, FAB, Loading)', route: 'buttons' },
    { title: '3. Elementos de Selección (Check, Radio, Sliders, Pickers)', route: 'selection' },
    { title: '4. Listas y Colecciones (FlatList, Grid, Swipe, Tabs)', route: 'lists' },
    { title: '5. Información y Retroalimentación (Progress, Modales)', route: 'feedback' },
    { title: '6. Contenedores y Estructura (Row, Column, Stack, Flex)', route: 'containers' },
  ];

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Banner Institucional */}
      <View
        style={[
          styles.banner,
          {
            backgroundColor: colors.primaryContainer,
            borderColor: colors.primary,
          },
        ]}
      >
        <Text style={[styles.bannerSub, { color: colors.onPrimaryContainer }]}>
          ESCOM - IPN | DAMN
        </Text>
        <Text style={[styles.bannerTitle, { color: colors.onPrimaryContainer }]}>
          Catálogo UI en React Native
        </Text>
        <Text style={[styles.bannerDesc, { color: colors.onPrimaryContainer }]}>
          Cuarta tecnología (TypeScript + React Native Core). Implementación basada en componentes reactivos y motor de diseño Flexbox (Yoga), cubriendo más de 35 elementos interactivos.
        </Text>
        <Text style={[styles.bannerAuthor, { color: colors.onPrimaryContainer }]}>
          Alumno: Aragón Martínez Manuel Alejandro | Boleta: 2023630411 | Grupo: 7CV4
        </Text>
      </View>

      {/* Estado Reactivo Global */}
      <View
        style={[
          styles.stateCard,
          {
            backgroundColor: colors.surface,
            borderColor: colors.outlineVariant,
          },
        ]}
      >
        <Text style={[styles.stateTitle, { color: colors.primary }]}>
          Estado Global de la Aplicación
        </Text>
        <Text style={[styles.stateAction, { color: colors.onSurface }]}>
          Última acción: {lastAction}
        </Text>
        <Text style={[styles.stateCount, { color: colors.secondary }]}>
          Total de elementos en la colección: {items.length}
        </Text>
      </View>

      <Text style={[styles.sectionHeading, { color: colors.onSurface }]}>
        Secciones del Catálogo
      </Text>

      {sections.map((sec, idx) => (
        <TouchableOpacity
          key={idx}
          style={[styles.navButton, { backgroundColor: colors.surfaceVariant }]}
          onPress={() => onNavigate(sec.route)}
        >
          <Text style={[styles.navButtonText, { color: colors.onSurfaceVariant }]}>
            {sec.title}
          </Text>
        </TouchableOpacity>
      ))}

      <View style={{ height: 40 }} />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  banner: {
    borderRadius: 16,
    borderWidth: 1,
    padding: 20,
    marginBottom: 16,
  },
  bannerSub: {
    fontSize: 12,
    fontWeight: '700',
    marginBottom: 4,
  },
  bannerTitle: {
    fontSize: 20,
    fontWeight: '800',
    marginBottom: 8,
  },
  bannerDesc: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 12,
  },
  bannerAuthor: {
    fontSize: 11,
    fontWeight: '600',
  },
  stateCard: {
    borderRadius: 12,
    borderWidth: 1,
    padding: 16,
    marginBottom: 20,
  },
  stateTitle: {
    fontSize: 15,
    fontWeight: '700',
    marginBottom: 4,
  },
  stateAction: {
    fontSize: 13,
    marginBottom: 2,
  },
  stateCount: {
    fontSize: 12,
  },
  sectionHeading: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 12,
  },
  navButton: {
    borderRadius: 10,
    padding: 14,
    marginBottom: 8,
  },
  navButtonText: {
    fontSize: 14,
    fontWeight: '600',
  },
});
