import React, { useState } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
  Modal,
  ActivityIndicator,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogCard } from '../components/CatalogCard';

interface FeedbackScreenProps {
  colors: ThemeColors;
  progressValue: number;
  totalItems: number;
  onSetAction: (action: string) => void;
}

export const FeedbackScreen: React.FC<FeedbackScreenProps> = ({
  colors,
  progressValue,
  totalItems,
  onSetAction,
}) => {
  const [showBottomSheet, setShowBottomSheet] = useState(false);
  const [snackbarVisible, setSnackbarVisible] = useState(false);

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.heading, { color: colors.primary }]}>
        Sección 5: Información y Retroalimentación
      </Text>
      <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
        Mecanismos de notificación, imágenes con redimensionamiento, modales, hojas inferiores e indicadores de progreso sincronizados.
      </Text>

      {/* 1. Jerarquía tipográfica */}
      <CatalogCard
        title="1. Jerarquía Tipográfica y Énfasis"
        description="Escalas tipográficas compuestas con distintos pesos de fuente, cursiva y colores semánticos."
        colors={colors}
      >
        <Text style={[styles.typeHeadline, { color: colors.onSurface }]}>
          Headline Large (22sp, Negrita 800)
        </Text>
        <Text style={[styles.typeTitle, { color: colors.primary }]}>
          Title Medium con énfasis primario
        </Text>
        <Text style={[styles.typeBody, { color: colors.onSurfaceVariant }]}>
          Cuerpo de texto en estilo itálico / cursiva para notas secundarias.
        </Text>
        <Text style={[styles.typeCaption, { color: colors.secondary }]}>
          Pie de foto o etiqueta en tamaño reducido (11sp).
        </Text>
      </CatalogCard>

      {/* 2. Imágenes */}
      <CatalogCard
        title="2. Imágenes (Local y Remota con Modos de Escalado)"
        description="Descarga asíncrona de recursos por URL comparando resizeMode 'contain' y 'cover'."
        colors={colors}
      >
        <View style={styles.imageRow}>
          <View style={styles.imageCol}>
            <View style={[styles.imageBox, { backgroundColor: colors.surfaceVariant }]}>
              <Text style={{ fontSize: 40 }}>🖼️</Text>
            </View>
            <Text style={[styles.imageCaption, { color: colors.secondary }]}>Local (Contain)</Text>
          </View>
          <View style={styles.imageCol}>
            <Image
              source={{
                uri: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png',
              }}
              style={[styles.imageBox, { resizeMode: 'cover', backgroundColor: colors.surfaceVariant }]}
            />
            <Text style={[styles.imageCaption, { color: colors.secondary }]}>Remota (Cover)</Text>
          </View>
        </View>
      </CatalogCard>

      {/* 3. Indicadores de progreso (Sincronizado con Sección 3) */}
      <CatalogCard
        title="3. Indicadores de Progreso (Determinado e Indeterminado)"
        description={`Progreso lineal y circular determinado reflejando el valor ajustado en el Slider de la Sección 3 (${progressValue}%).`}
        colors={colors}
      >
        <Text style={[styles.progressLabel, { color: colors.onSurface }]}>
          Progreso Determinado ({progressValue}% desde S3):
        </Text>
        <View style={[styles.progressBarBg, { backgroundColor: colors.surfaceVariant }]}>
          <View
            style={[
              styles.progressBarFill,
              { width: `${progressValue}%`, backgroundColor: colors.primary },
            ]}
          />
        </View>

        <Text style={[styles.progressLabel, { color: colors.onSurface, marginTop: 12 }]}>
          Progreso Indeterminado (Carga continua):
        </Text>
        <ActivityIndicator size="small" color={colors.primary} style={{ alignSelf: 'flex-start', marginTop: 4 }} />
      </CatalogCard>

      {/* 4. Notificaciones: Toast y Snackbar */}
      <CatalogCard
        title="4. Notificaciones Breves: Toast / Alert y Snackbar con Acción"
        description="Alertas efímeras no invasivas frente a mensajes persistentes con opción de 'Deshacer'."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.actionBtn, { borderColor: colors.primary, borderWidth: 1 }]}
            onPress={() => {
              onSetAction('Toast / Alerta rápida disparada');
              Alert.alert('Notificación Breve', 'Mensaje emergente informativo estilo Toast.');
            }}
          >
            <Text style={{ color: colors.primary, fontWeight: '700' }}>Alerta Breve</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.actionBtn, { backgroundColor: colors.primary }]}
            onPress={() => {
              onSetAction('Snackbar desplegado');
              setSnackbarVisible(true);
            }}
          >
            <Text style={{ color: colors.onPrimary, fontWeight: '700' }}>Snackbar Acción</Text>
          </TouchableOpacity>
        </View>

        {snackbarVisible && (
          <View style={[styles.snackbar, { backgroundColor: colors.onSurface }]}>
            <Text style={{ color: colors.surface, flex: 1, fontSize: 13 }}>
              Elemento archivado temporalmente
            </Text>
            <TouchableOpacity
              onPress={() => {
                setSnackbarVisible(false);
                onSetAction('Acción deshecha desde Snackbar');
                Alert.alert('Deshecho', 'Acción deshecha con éxito.');
              }}
            >
              <Text style={{ color: colors.primary, fontWeight: '700', marginLeft: 8 }}>
                DESHACER
              </Text>
            </TouchableOpacity>
          </View>
        )}
      </CatalogCard>

      {/* 5. Diálogo de confirmación y Hoja Inferior */}
      <CatalogCard
        title="5. Diálogo Modal de Confirmación y Hoja Inferior (BottomSheet)"
        description="Pausa la ejecución para solicitar confirmación explícita o despliega una bandeja de acciones inferior."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.actionBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => {
              Alert.alert(
                'Confirmar Operación',
                '¿Deseas sincronizar los registros del catálogo con el servidor?',
                [
                  { text: 'Cancelar', onPress: () => onSetAction('Diálogo: Cancelado') },
                  { text: 'Confirmar', onPress: () => onSetAction('Diálogo: Confirmado') },
                ]
              );
            }}
          >
            <Text style={{ color: colors.onSurfaceVariant, fontWeight: '700' }}>
              Abrir Diálogo
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.actionBtn, { backgroundColor: colors.surfaceVariant }]}
            onPress={() => {
              setShowBottomSheet(true);
              onSetAction('BottomSheet abierto');
            }}
          >
            <Text style={{ color: colors.onSurfaceVariant, fontWeight: '700' }}>
              Abrir BottomSheet
            </Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 6. Tarjeta, Separador y Badge */}
      <CatalogCard
        title="6. Tarjeta (Card), Separador (Divider) y Badge Numérico"
        description="Estructuración visual en superficies con elevación y distintivos numéricos dinámicos."
        colors={colors}
      >
        <View style={[styles.innerCard, { backgroundColor: colors.surfaceVariant }]}>
          <View style={styles.cardHeaderRow}>
            <Text style={{ fontWeight: '700', fontSize: 15, color: colors.onSurface }}>
              Notificaciones de la Colección
            </Text>
            <View style={[styles.badgePill, { backgroundColor: colors.primary }]}>
              <Text style={{ color: colors.onPrimary, fontSize: 11, fontWeight: '800' }}>
                {totalItems}
              </Text>
            </View>
          </View>
          <View style={[styles.divider, { backgroundColor: colors.outlineVariant }]} />
          <Text style={{ fontSize: 12, color: colors.onSurfaceVariant }}>
            El distintivo numérico refleja la cantidad viva de registros en la colección ({totalItems}).
          </Text>
        </View>
      </CatalogCard>

      {/* Modal BottomSheet */}
      <Modal visible={showBottomSheet} transparent={true} animationType="slide">
        <View style={styles.sheetOverlay}>
          <View style={[styles.sheetContent, { backgroundColor: colors.surface }]}>
            <View style={[styles.dragHandle, { backgroundColor: colors.outlineVariant }]} />
            <Text style={[styles.sheetTitle, { color: colors.primary }]}>
              Hoja Inferior Modal (BottomSheet)
            </Text>
            <Text style={[styles.sheetDesc, { color: colors.onSurfaceVariant }]}>
              Deslizable desde el borde inferior para presentar opciones complementarias sin perder el contexto principal.
            </Text>
            <TouchableOpacity
              style={[styles.closeSheetBtn, { backgroundColor: colors.primary }]}
              onPress={() => setShowBottomSheet(false)}
            >
              <Text style={{ color: colors.onPrimary, fontWeight: '700' }}>Cerrar Hoja</Text>
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
  typeHeadline: {
    fontSize: 20,
    fontWeight: '800',
    marginBottom: 2,
  },
  typeTitle: {
    fontSize: 15,
    fontWeight: '700',
    marginBottom: 2,
  },
  typeBody: {
    fontSize: 13,
    fontStyle: 'italic',
    marginBottom: 2,
  },
  typeCaption: {
    fontSize: 11,
  },
  imageRow: {
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  imageCol: {
    alignItems: 'center',
  },
  imageBox: {
    width: 100,
    height: 100,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  imageCaption: {
    fontSize: 11,
    marginTop: 4,
  },
  progressLabel: {
    fontSize: 13,
    fontWeight: '600',
    marginBottom: 6,
  },
  progressBarBg: {
    height: 10,
    borderRadius: 5,
    overflow: 'hidden',
  },
  progressBarFill: {
    height: '100%',
  },
  row: {
    flexDirection: 'row',
    gap: 8,
  },
  actionBtn: {
    flex: 1,
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
  },
  snackbar: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 14,
    borderRadius: 8,
    marginTop: 10,
  },
  innerCard: {
    padding: 14,
    borderRadius: 10,
  },
  cardHeaderRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  badgePill: {
    paddingHorizontal: 8,
    paddingVertical: 2,
    borderRadius: 12,
  },
  divider: {
    height: 1,
    marginVertical: 10,
  },
  sheetOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.4)',
    justifyContent: 'flex-end',
  },
  sheetContent: {
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    padding: 24,
  },
  dragHandle: {
    width: 40,
    height: 4,
    borderRadius: 2,
    alignSelf: 'center',
    marginBottom: 16,
  },
  sheetTitle: {
    fontSize: 18,
    fontWeight: '800',
    marginBottom: 8,
  },
  sheetDesc: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 20,
  },
  closeSheetBtn: {
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
  },
});
