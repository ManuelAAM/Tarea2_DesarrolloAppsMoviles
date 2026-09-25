import React, { useState } from 'react';
import {
  View,
  Text,
  FlatList,
  SectionList,
  StyleSheet,
  TouchableOpacity,
  RefreshControl,
  ScrollView,
  Alert,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogItem } from '../types';

interface ListsScreenProps {
  colors: ThemeColors;
  items: CatalogItem[];
  onRemoveItem: (item: CatalogItem) => void;
  onResetItems: () => void;
  onSetAction: (action: string) => void;
}

export const ListsScreen: React.FC<ListsScreenProps> = ({
  colors,
  items,
  onRemoveItem,
  onResetItems,
  onSetAction,
}) => {
  const [selectedTab, setSelectedTab] = useState(0);
  const [refreshing, setRefreshing] = useState(false);

  const onRefresh = () => {
    setRefreshing(true);
    setTimeout(() => {
      setRefreshing(false);
      onSetAction('Lista actualizada con RefreshControl');
    }, 1200);
  };

  const showDetail = (item: CatalogItem) => {
    onSetAction(`Detalle abierto: ${item.title}`);
    Alert.alert(
      item.title,
      `ID: ${item.id}\nCategoría: ${item.category}\n\nDescripción:\n${item.description}`
    );
  };

  // Agrupamiento para SectionList
  const groupedSections = [
    {
      title: 'CONTROLES DE TEXTO',
      data: items.filter((i) => i.category === 'Entrada'),
    },
    {
      title: 'BOTONES Y DISPARADORES',
      data: items.filter((i) => i.category === 'Botones'),
    },
    {
      title: 'ELEMENTOS DE SELECCIÓN',
      data: items.filter((i) => i.category === 'Selección'),
    },
  ];

  return (
    <View style={[styles.container, { backgroundColor: colors.background }]}>
      <View style={styles.header}>
        <Text style={[styles.heading, { color: colors.primary }]}>
          Sección 4: Listas y Colecciones
        </Text>
        <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
          Listas optimizadas en React Native con FlatList, SectionList, RefreshControl, swipe/delete y estado vacío.
        </Text>

        <View style={styles.tabRow}>
          {['Vertical', 'Cuadrícula', 'Secciones', 'Páginas'].map((tabName, idx) => (
            <TouchableOpacity
              key={idx}
              style={[
                styles.tabBtn,
                selectedTab === idx && { borderBottomColor: colors.primary, borderBottomWidth: 3 },
              ]}
              onPress={() => setSelectedTab(idx)}
            >
              <Text
                style={{
                  color: selectedTab === idx ? colors.primary : colors.secondary,
                  fontWeight: selectedTab === idx ? '700' : '400',
                  fontSize: 12,
                }}
              >
                {tabName}
              </Text>
            </TouchableOpacity>
          ))}
        </View>
      </View>

      {/* Tab 0: FlatList Vertical */}
      {selectedTab === 0 && (
        <FlatList
          data={items}
          keyExtractor={(item) => item.id}
          refreshControl={
            <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
          }
          ListEmptyComponent={
            <View style={styles.emptyContainer}>
              <Text style={{ fontSize: 48 }}>📦</Text>
              <Text style={[styles.emptyTitle, { color: colors.onSurface }]}>
                Colección Vacía
              </Text>
              <Text style={[styles.emptyDesc, { color: colors.onSurfaceVariant }]}>
                No hay elementos disponibles. Puedes agregar nuevos desde la Sección 1.
              </Text>
              <TouchableOpacity
                style={[styles.resetBtn, { backgroundColor: colors.primary }]}
                onPress={onResetItems}
              >
                <Text style={{ color: colors.onPrimary, fontWeight: '700' }}>
                  Restaurar Elementos Iniciales
                </Text>
              </TouchableOpacity>
            </View>
          }
          renderItem={({ item }) => (
            <View
              style={[
                styles.itemCard,
                { backgroundColor: colors.surface, borderColor: colors.outlineVariant },
              ]}
            >
              <TouchableOpacity
                style={{ flex: 1 }}
                onPress={() => showDetail(item)}
              >
                <Text style={[styles.itemTitle, { color: colors.onSurface }]}>
                  {item.title}
                </Text>
                <Text
                  style={[styles.itemDesc, { color: colors.onSurfaceVariant }]}
                  numberOfLines={2}
                >
                  {item.description}
                </Text>
                <View style={[styles.chipBadge, { backgroundColor: colors.surfaceVariant }]}>
                  <Text style={{ fontSize: 10, color: colors.primary, fontWeight: '700' }}>
                    {item.category}
                  </Text>
                </View>
              </TouchableOpacity>
              <TouchableOpacity
                style={[styles.deleteBtn, { backgroundColor: colors.surfaceVariant }]}
                onPress={() => {
                  onRemoveItem(item);
                  Alert.alert('Eliminado', `Se eliminó "${item.title}".`);
                }}
              >
                <Text style={{ color: colors.error, fontWeight: '700' }}>🗑</Text>
              </TouchableOpacity>
            </View>
          )}
        />
      )}

      {/* Tab 1: Cuadrícula */}
      {selectedTab === 1 && (
        <FlatList
          data={items}
          key={'grid-view-2-cols'}
          numColumns={2}
          keyExtractor={(item) => item.id}
          contentContainerStyle={{ padding: 8 }}
          renderItem={({ item }) => (
            <TouchableOpacity
              style={[
                styles.gridCard,
                { backgroundColor: colors.surface, borderColor: colors.outlineVariant },
              ]}
              onPress={() => showDetail(item)}
            >
              <Text style={{ fontSize: 24, marginBottom: 4 }}>🗂</Text>
              <Text style={[styles.gridTitle, { color: colors.onSurface }]} numberOfLines={2}>
                {item.title}
              </Text>
              <Text style={[styles.gridCategory, { color: colors.secondary }]}>
                {item.category}
              </Text>
            </TouchableOpacity>
          )}
        />
      )}

      {/* Tab 2: SectionList con encabezados */}
      {selectedTab === 2 && (
        <SectionList
          sections={groupedSections}
          keyExtractor={(item) => item.id}
          renderSectionHeader={({ section: { title } }) => (
            <View style={[styles.sectionHeader, { backgroundColor: colors.primaryContainer }]}>
              <Text style={[styles.sectionHeaderText, { color: colors.onPrimaryContainer }]}>
                {title}
              </Text>
            </View>
          )}
          renderItem={({ item }) => (
            <TouchableOpacity
              style={[
                styles.itemCard,
                { backgroundColor: colors.surface, borderColor: colors.outlineVariant },
              ]}
              onPress={() => showDetail(item)}
            >
              <View>
                <Text style={[styles.itemTitle, { color: colors.onSurface }]}>
                  {item.title}
                </Text>
                <Text style={[styles.itemDesc, { color: colors.onSurfaceVariant }]}>
                  {item.description}
                </Text>
              </View>
            </TouchableOpacity>
          )}
        />
      )}

      {/* Tab 3: Pestañas con contenido deslizables */}
      {selectedTab === 3 && (
        <ScrollView horizontal pagingEnabled style={{ flex: 1 }}>
          {[
            { title: 'Pestaña 1: Virtual DOM y Puente', text: 'React Native serializa llamadas hacia los componentes nativos de la plataforma mediante el nuevo motor Bridgeless / TurboModules.' },
            { title: 'Pestaña 2: Flexbox Multiplataforma', text: 'El motor Yoga compila estilos estándar flexbox de CSS a geometría nativa exacta en Android e iOS.' },
            { title: 'Pestaña 3: Reutilización de Código', text: 'Permite compartir hasta el 95% de la lógica de interfaz de usuario entre sistemas operativos móviles sin sacrificar rendimiento.' },
          ].map((page, idx) => (
            <View
              key={idx}
              style={[styles.pageSlide, { backgroundColor: colors.secondaryContainer }]}
            >
              <Text style={[styles.pageTitle, { color: colors.onSecondaryContainer }]}>
                {page.title}
              </Text>
              <Text style={[styles.pageText, { color: colors.onSecondaryContainer }]}>
                {page.text}
              </Text>
              <Text style={[styles.pageHint, { color: colors.primary }]}>
                Desliza horizontalmente (Página {idx + 1} de 3)
              </Text>
            </View>
          ))}
        </ScrollView>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  header: {
    paddingHorizontal: 16,
    paddingTop: 12,
  },
  heading: {
    fontSize: 22,
    fontWeight: '800',
    marginBottom: 4,
  },
  subheading: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 8,
  },
  tabRow: {
    flexDirection: 'row',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  tabBtn: {
    flex: 1,
    paddingVertical: 10,
    alignItems: 'center',
  },
  itemCard: {
    flexDirection: 'row',
    alignItems: 'center',
    borderRadius: 10,
    borderWidth: 1,
    marginHorizontal: 16,
    marginVertical: 4,
    padding: 12,
  },
  itemTitle: {
    fontSize: 15,
    fontWeight: '700',
    marginBottom: 2,
  },
  itemDesc: {
    fontSize: 12,
    marginBottom: 6,
  },
  chipBadge: {
    alignSelf: 'flex-start',
    paddingHorizontal: 8,
    paddingVertical: 2,
    borderRadius: 12,
  },
  deleteBtn: {
    padding: 10,
    borderRadius: 8,
    marginLeft: 8,
  },
  gridCard: {
    flex: 1,
    margin: 6,
    padding: 16,
    borderRadius: 12,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  gridTitle: {
    fontSize: 13,
    fontWeight: '700',
    textAlign: 'center',
  },
  gridCategory: {
    fontSize: 11,
    marginTop: 4,
  },
  sectionHeader: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    marginVertical: 4,
  },
  sectionHeaderText: {
    fontSize: 13,
    fontWeight: '800',
  },
  emptyContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    padding: 32,
    marginTop: 40,
  },
  emptyTitle: {
    fontSize: 18,
    fontWeight: '700',
    marginTop: 12,
  },
  emptyDesc: {
    fontSize: 13,
    textAlign: 'center',
    marginTop: 6,
    marginBottom: 16,
  },
  resetBtn: {
    paddingHorizontal: 16,
    paddingVertical: 10,
    borderRadius: 8,
  },
  pageSlide: {
    width: 340,
    height: 220,
    margin: 16,
    padding: 20,
    borderRadius: 16,
    justifyContent: 'center',
  },
  pageTitle: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 8,
  },
  pageText: {
    fontSize: 13,
    lineHeight: 18,
  },
  pageHint: {
    fontSize: 11,
    fontWeight: '700',
    marginTop: 12,
  },
});
