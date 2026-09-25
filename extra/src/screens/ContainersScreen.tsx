import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogCard } from '../components/CatalogCard';

interface ContainersScreenProps {
  colors: ThemeColors;
  onSetAction: (action: string) => void;
}

export const ContainersScreen: React.FC<ContainersScreenProps> = ({
  colors,
  onSetAction,
}) => {
  const [navIndex, setNavIndex] = useState(0);

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.heading, { color: colors.primary }]}>
        Sección 6: Contenedores y Estructura
      </Text>
      <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
        Distribución espacial mediante Flexbox (Yoga Engine), filas, columnas, superposiciones absolutas y barras globales.
      </Text>

      {/* 1. Fila, Columna y Superposición */}
      <CatalogCard
        title="1. Distribución en Fila, Columna y Superposición (Stack)"
        description="flexDirection: 'row' organiza horizontalmente, 'column' verticalmente y position: 'absolute' apila capas en el eje Z."
        colors={colors}
      >
        <Text style={[styles.miniLabel, { color: colors.onSurface }]}>Fila Horizontal (Row):</Text>
        <View style={styles.rowLayout}>
          <View style={[styles.boxItem, { backgroundColor: colors.primaryContainer }]}>
            <Text style={{ color: colors.onPrimaryContainer, fontWeight: '700' }}>Fila 1 (50%)</Text>
          </View>
          <View style={[styles.boxItem, { backgroundColor: colors.secondaryContainer }]}>
            <Text style={{ color: colors.onSecondaryContainer, fontWeight: '700' }}>Fila 2 (50%)</Text>
          </View>
        </View>

        <Text style={[styles.miniLabel, { color: colors.onSurface, marginTop: 12 }]}>
          Superposición en Capas (Absolute Stack):
        </Text>
        <View style={[styles.stackBox, { backgroundColor: colors.surfaceVariant }]}>
          <Text style={{ color: colors.onSurfaceVariant }}>Capa Inferior de Fondo</Text>
          <TouchableOpacity
            style={[styles.floatingLayer, { backgroundColor: colors.primary }]}
            onPress={() => onSetAction('Pulsada capa flotante en Stack')}
          >
            <Text style={{ color: colors.onPrimary, fontWeight: '700', fontSize: 12 }}>
              Capa Superior Flotante
            </Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 2. Pesos proporcionales */}
      <CatalogCard
        title="2. Pesos Proporcionales (Flex: 1 : 2 : 1)"
        description="Reparte el ancho disponible mediante factores de crecimiento flex (25% : 50% : 25%)."
        colors={colors}
      >
        <View style={styles.flexRow}>
          <View style={[styles.flexBox, { flex: 1, backgroundColor: colors.primary }]}>
            <Text style={styles.flexText}>Flex 1 (25%)</Text>
          </View>
          <View style={[styles.flexBox, { flex: 2, backgroundColor: colors.secondary }]}>
            <Text style={styles.flexText}>Flex 2 (50%)</Text>
          </View>
          <View style={[styles.flexBox, { flex: 1, backgroundColor: colors.primary }]}>
            <Text style={styles.flexText}>Flex 1 (25%)</Text>
          </View>
        </View>
      </CatalogCard>

      {/* 3. Barra superior e inferior demostrativas */}
      <CatalogCard
        title="3. Barras de Estructura: Superior e Inferior (BottomNav)"
        description="Contenedores globales de identidad de cabecera y barra de alternancia entre destinos clave."
        colors={colors}
      >
        <View style={[styles.demoAppBar, { backgroundColor: colors.primaryContainer }]}>
          <Text style={{ fontSize: 18 }}>☰</Text>
          <Text style={[styles.demoAppTitle, { color: colors.onPrimaryContainer }]}>
            Barra Superior de Muestra
          </Text>
          <Text style={{ fontSize: 18 }}>⋮</Text>
        </View>

        <View style={[styles.bottomNavBar, { backgroundColor: colors.surfaceVariant }]}>
          {['Inicio', 'Colección', 'Ajustes'].map((item, idx) => {
            const isSel = navIndex === idx;
            return (
              <TouchableOpacity
                key={idx}
                style={[styles.navItem, isSel && { borderTopWidth: 2, borderTopColor: colors.primary }]}
                onPress={() => {
                  setNavIndex(idx);
                  onSetAction(`BottomNav seleccionada: ${item}`);
                }}
              >
                <Text style={{ color: isSel ? colors.primary : colors.secondary, fontWeight: isSel ? '800' : '400' }}>
                  {item}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>
      </CatalogCard>

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
  miniLabel: {
    fontSize: 13,
    fontWeight: '700',
    marginBottom: 6,
  },
  rowLayout: {
    flexDirection: 'row',
    gap: 8,
  },
  boxItem: {
    flex: 1,
    height: 44,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  stackBox: {
    height: 90,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    position: 'relative',
  },
  floatingLayer: {
    position: 'absolute',
    bottom: 8,
    right: 8,
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 8,
  },
  flexRow: {
    flexDirection: 'row',
    height: 48,
    borderRadius: 8,
    overflow: 'hidden',
  },
  flexBox: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  flexText: {
    color: '#fff',
    fontSize: 11,
    fontWeight: '700',
  },
  demoAppBar: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 16,
    paddingVertical: 12,
    borderRadius: 8,
    marginBottom: 10,
  },
  demoAppTitle: {
    fontSize: 15,
    fontWeight: '700',
  },
  bottomNavBar: {
    flexDirection: 'row',
    borderRadius: 8,
    overflow: 'hidden',
  },
  navItem: {
    flex: 1,
    paddingVertical: 12,
    alignItems: 'center',
  },
});
