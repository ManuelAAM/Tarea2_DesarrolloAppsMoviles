import React, { useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Modal,
  ScrollView,
  StatusBar,
  useColorScheme,
  Alert,
} from 'react-native';
import { lightTheme, darkTheme } from './src/theme/colors';
import { CatalogItem } from './src/types';
import { HomeScreen } from './src/screens/HomeScreen';
import { TextInputsScreen } from './src/screens/TextInputsScreen';
import { ButtonsScreen } from './src/screens/ButtonsScreen';
import { SelectionScreen } from './src/screens/SelectionScreen';
import { ListsScreen } from './src/screens/ListsScreen';
import { FeedbackScreen } from './src/screens/FeedbackScreen';
import { ContainersScreen } from './src/screens/ContainersScreen';

const initialItems: CatalogItem[] = [
  { id: '1', title: 'TextInput Simple', description: 'Campo para captura alfanumérica estándar.', category: 'Entrada' },
  { id: '2', title: 'TextInput con Validación', description: 'Evaluación de cadenas con mensaje de error en color rojo.', category: 'Entrada' },
  { id: '3', title: 'Campo Contraseña', description: 'Ocultamiento de texto con secureTextEntry.', category: 'Entrada' },
  { id: '4', title: 'Teclados Especializados', description: 'Configuraciones de keyboardType numérico, email y teléfono.', category: 'Entrada' },
  { id: '5', title: 'TextInput Multilínea', description: 'Edición libre de párrafos mediante multiline={true}.', category: 'Entrada' },
  { id: '6', title: 'Sugerencias Automáticas', description: 'Filtrado flotante interactivo de opciones conforme se escribe.', category: 'Entrada' },
  { id: '7', title: 'Barra de Búsqueda', description: 'Consulta activa con disparadores y botón de limpieza rápida.', category: 'Entrada' },
  { id: '8', title: 'Botón Relleno', description: 'TouchableOpacity con color primario para acción principal.', category: 'Botones' },
  { id: '9', title: 'Botón Contorno', description: 'Acción secundaria con borde perimetral.', category: 'Botones' },
  { id: '10', title: 'Botón Solo Texto', description: 'Acción discreta sin caja de fondo.', category: 'Botones' },
  { id: '11', title: 'Botón con Ícono', description: 'Glifo vectorial interactivo de favoritos.', category: 'Botones' },
  { id: '12', title: 'FAB Circular y Extendido', description: 'Botón circular elevado sobre la superficie.', category: 'Botones' },
  { id: '13', title: 'Selector Segmentado', description: 'Franja continua de opciones mutuamente excluyentes.', category: 'Botones' },
  { id: '14', title: 'Casilla Tri-State', description: 'Checkbox con estado indeterminado compuesto.', category: 'Selección' },
  { id: '15', title: 'Botones de Opción Radio', description: 'Exclusión mutua garantizada para selección única.', category: 'Selección' },
  { id: '16', title: 'Switch Conmutador', description: 'Interruptor binario analógico digital.', category: 'Selección' },
];

export default function App() {
  const systemScheme = useColorScheme();
  const [isDarkMode, setIsDarkMode] = useState(systemScheme === 'dark');
  const colors = isDarkMode ? darkTheme : lightTheme;

  const [items, setItems] = useState<CatalogItem[]>(initialItems);
  const [lastAction, setLastAction] = useState('Bienvenido al Catálogo React Native');
  const [progressValue, setProgressValue] = useState(45);
  const [currentRoute, setCurrentRoute] = useState('home');
  const [drawerOpen, setDrawerOpen] = useState(false);

  const navRoutes = [
    { label: 'Inicio', route: 'home', icon: '🏠' },
    { label: '1. Entrada de Texto', route: 'text_inputs', icon: '📝' },
    { label: '2. Botones y Acciones', route: 'buttons', icon: '🔘' },
    { label: '3. Selección', route: 'selection', icon: '☑️' },
    { label: '4. Listas y Colecciones', route: 'lists', icon: '📋' },
    { label: '5. Retroalimentación', route: 'feedback', icon: '🔔' },
    { label: '6. Contenedores', route: 'containers', icon: '📐' },
  ];

  const currentTitle = navRoutes.find((r) => r.route === currentRoute)?.label || 'Catálogo UI';

  const addItem = (title: string, description: string, category: string) => {
    const newItem: CatalogItem = {
      id: Date.now().toString(),
      title,
      description,
      category,
    };
    setItems([newItem, ...items]);
    setLastAction(`Elemento agregado a Sección 4: ${title}`);
  };

  const removeItem = (item: CatalogItem) => {
    setItems(items.filter((i) => i.id !== item.id));
    setLastAction(`Elemento eliminado: ${item.title}`);
  };

  const resetItems = () => {
    setItems(initialItems);
    setLastAction('Lista restablecida con 16 elementos iniciales');
  };

  const showAbout = () => {
    Alert.alert(
      'Información Institucional',
      'Instituto Politécnico Nacional\n' +
      'Escuela Superior de Cómputo (ESCOM)\n\n' +
      'Materia: Desarrollo de Aplicaciones Móviles Nativas\n' +
      'Semestre: 2027-1\n' +
      'Tarea 2: Catálogo de Elementos Básicos de Interfaz de Usuario\n\n' +
      'Alumno: Aragón Martínez Manuel Alejandro\n' +
      'Boleta: 2023630411\n' +
      'Grupo: 7CV4\n' +
      'Profesor: Gabriel Hurtado Avilés\n' +
      'Cuarta Tecnología: React Native (TypeScript)'
    );
  };

  return (
    <SafeAreaView style={[styles.root, { backgroundColor: colors.background }]}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={colors.primaryContainer}
      />

      {/* Barra de Aplicación Superior (AppBar) */}
      <View style={[styles.appBar, { backgroundColor: colors.primaryContainer }]}>
        <TouchableOpacity
          style={styles.menuIconBtn}
          onPress={() => setDrawerOpen(true)}
        >
          <Text style={{ fontSize: 20, color: colors.onPrimaryContainer }}>☰</Text>
        </TouchableOpacity>

        <Text
          style={[styles.appBarTitle, { color: colors.onPrimaryContainer }]}
          numberOfLines={1}
        >
          {currentTitle}
        </Text>

        <View style={styles.appBarActions}>
          <TouchableOpacity
            style={styles.actionIconBtn}
            onPress={() => setIsDarkMode(!isDarkMode)}
          >
            <Text style={{ fontSize: 18 }}>{isDarkMode ? '☀️' : '🌙'}</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.actionIconBtn}
            onPress={showAbout}
          >
            <Text style={{ fontSize: 18, color: colors.onPrimaryContainer }}>ℹ️</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Cuerpo Principal */}
      <View style={{ flex: 1 }}>
        {currentRoute === 'home' && (
          <HomeScreen
            colors={colors}
            items={items}
            lastAction={lastAction}
            onNavigate={setCurrentRoute}
          />
        )}
        {currentRoute === 'text_inputs' && (
          <TextInputsScreen
            colors={colors}
            onAddItem={addItem}
            onSetAction={setLastAction}
          />
        )}
        {currentRoute === 'buttons' && (
          <ButtonsScreen
            colors={colors}
            onSetAction={setLastAction}
          />
        )}
        {currentRoute === 'selection' && (
          <SelectionScreen
            colors={colors}
            progressValue={progressValue}
            onSetProgress={setProgressValue}
            onSetAction={setLastAction}
          />
        )}
        {currentRoute === 'lists' && (
          <ListsScreen
            colors={colors}
            items={items}
            onRemoveItem={removeItem}
            onResetItems={resetItems}
            onSetAction={setLastAction}
          />
        )}
        {currentRoute === 'feedback' && (
          <FeedbackScreen
            colors={colors}
            progressValue={progressValue}
            totalItems={items.length}
            onSetAction={setLastAction}
          />
        )}
        {currentRoute === 'containers' && (
          <ContainersScreen
            colors={colors}
            onSetAction={setLastAction}
          />
        )}
      </View>

      {/* Menú Lateral Drawer (Modal) */}
      <Modal visible={drawerOpen} transparent={true} animationType="slide">
        <View style={styles.drawerOverlay}>
          <View style={[styles.drawerContent, { backgroundColor: colors.surface }]}>
            <View style={[styles.drawerHeader, { backgroundColor: colors.primary }]}>
              <Text style={styles.drawerHeaderTitle}>Catálogo UI React Native</Text>
              <Text style={styles.drawerHeaderSub}>ESCOM - DAMN Tarea 2</Text>
            </View>

            <ScrollView style={{ padding: 12 }}>
              {navRoutes.map((r, idx) => {
                const isSelected = currentRoute === r.route;
                return (
                  <TouchableOpacity
                    key={idx}
                    style={[
                      styles.drawerItem,
                      isSelected && { backgroundColor: colors.primaryContainer },
                    ]}
                    onPress={() => {
                      setCurrentRoute(r.route);
                      setDrawerOpen(false);
                    }}
                  >
                    <Text style={{ fontSize: 18, marginRight: 12 }}>{r.icon}</Text>
                    <Text
                      style={{
                        fontSize: 14,
                        fontWeight: isSelected ? '700' : '500',
                        color: isSelected ? colors.onPrimaryContainer : colors.onSurface,
                      }}
                    >
                      {r.label}
                    </Text>
                  </TouchableOpacity>
                );
              })}
            </ScrollView>

            <TouchableOpacity
              style={[styles.closeDrawerBtn, { backgroundColor: colors.surfaceVariant }]}
              onPress={() => setDrawerOpen(false)}
            >
              <Text style={{ color: colors.onSurfaceVariant, fontWeight: '700' }}>
                Cerrar Menú
              </Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  appBar: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
    elevation: 3,
  },
  menuIconBtn: {
    padding: 6,
    marginRight: 10,
  },
  appBarTitle: {
    flex: 1,
    fontSize: 17,
    fontWeight: '700',
  },
  appBarActions: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  actionIconBtn: {
    padding: 6,
  },
  drawerOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.5)',
    flexDirection: 'row',
  },
  drawerContent: {
    width: '78%',
    height: '100%',
    elevation: 16,
  },
  drawerHeader: {
    padding: 24,
    paddingTop: 40,
  },
  drawerHeaderTitle: {
    color: '#fff',
    fontSize: 18,
    fontWeight: '800',
  },
  drawerHeaderSub: {
    color: 'rgba(255,255,255,0.8)',
    fontSize: 12,
    marginTop: 4,
  },
  drawerItem: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 12,
    paddingHorizontal: 12,
    borderRadius: 8,
    marginBottom: 4,
  },
  closeDrawerBtn: {
    padding: 16,
    alignItems: 'center',
    margin: 12,
    borderRadius: 8,
  },
});
