import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';
import { ThemeColors } from '../theme/colors';
import { CatalogCard } from '../components/CatalogCard';

interface ButtonsScreenProps {
  colors: ThemeColors;
  onSetAction: (action: string) => void;
}

export const ButtonsScreen: React.FC<ButtonsScreenProps> = ({
  colors,
  onSetAction,
}) => {
  const [feedback, setFeedback] = useState('Respuesta: Pulsa cualquier botón para interactuar');
  const [selectedSegment, setSelectedSegment] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  const triggerFeedback = (name: string) => {
    const msg = `Respuesta visible: Pulsado "${name}"`;
    setFeedback(msg);
    onSetAction(msg);
  };

  return (
    <ScrollView style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.heading, { color: colors.primary }]}>
        Sección 2: Botones y Acciones
      </Text>
      <Text style={[styles.subheading, { color: colors.onSurfaceVariant }]}>
        Elementos de pulsación táctil con respuesta visible instantánea (TouchableOpacity y Pressable).
      </Text>

      {/* Banner de respuesta visible */}
      <View style={[styles.feedbackBanner, { backgroundColor: colors.surfaceVariant }]}>
        <Text style={[styles.feedbackText, { color: colors.primary }]}>
          {feedback}
        </Text>
      </View>

      {/* 1. Jerarquía de botones */}
      <CatalogCard
        title="1. Jerarquía de Énfasis: Relleno, Contorno y Solo Texto"
        description="Representa niveles de importancia: acción primaria (fondo sólido), secundaria (borde perimetral) y terciaria (texto plano)."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.btn, { backgroundColor: colors.primary }]}
            onPress={() => triggerFeedback('Botón Relleno (Primary)')}
          >
            <Text style={{ color: colors.onPrimary, fontWeight: '700' }}>Relleno</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.btn, { borderWidth: 1, borderColor: colors.primary }]}
            onPress={() => triggerFeedback('Botón Contorno (Outlined)')}
          >
            <Text style={{ color: colors.primary, fontWeight: '700' }}>Contorno</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.btn}
            onPress={() => triggerFeedback('Botón Texto (TextButton)')}
          >
            <Text style={{ color: colors.primary, fontWeight: '700' }}>Texto</Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 2. Botones con ícono */}
      <CatalogCard
        title="2. Botones con Ícono (Solo Ícono y Mixto)"
        description="Acompañan la acción con glifos visuales intuitivos para favorecer el reconocimiento semántico rápido."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[
              styles.iconBtn,
              { backgroundColor: colors.primaryContainer, borderColor: colors.primary },
            ]}
            onPress={() => triggerFeedback('Botón Solo Ícono (★)')}
          >
            <Text style={{ fontSize: 20, color: colors.onPrimaryContainer }}>★</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.iconTextBtn, { backgroundColor: colors.primary }]}
            onPress={() => triggerFeedback('Botón Ícono + Texto (★ Favorito)')}
          >
            <Text style={{ color: colors.onPrimary, fontWeight: '700', fontSize: 14 }}>
              ★ Añadir a Favoritos
            </Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 3. FAB normal y extendido */}
      <CatalogCard
        title="3. Botón de Acción Flotante (FAB Estándar y Extendido)"
        description="Componente elevado sobre la interfaz para la acción más representativa de la vista."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.fabCircle, { backgroundColor: colors.primary }]}
            onPress={() => triggerFeedback('FAB Normal Circular (+)')}
          >
            <Text style={{ color: colors.onPrimary, fontSize: 24, fontWeight: '700' }}>+</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.fabExtended, { backgroundColor: colors.primary }]}
            onPress={() => triggerFeedback('FAB Extendido (+ Nuevo Registro)')}
          >
            <Text style={{ color: colors.onPrimary, fontWeight: '700', fontSize: 14 }}>
              + Nuevo Registro
            </Text>
          </TouchableOpacity>
        </View>
      </CatalogCard>

      {/* 4. Selector segmentado */}
      <CatalogCard
        title="4. Botón de Alternancia / Selector Segmentado"
        description="Franja horizontal de segmentos mutuamente excluyentes con retroalimentación del elemento activo."
        colors={colors}
      >
        <View style={[styles.segmentContainer, { borderColor: colors.outlineVariant }]}>
          {['Día', 'Semana', 'Mes'].map((label, index) => {
            const isSelected = selectedSegment === index;
            return (
              <TouchableOpacity
                key={index}
                style={[
                  styles.segmentBtn,
                  isSelected && { backgroundColor: colors.primary },
                ]}
                onPress={() => {
                  setSelectedSegment(index);
                  triggerFeedback(`Segmento: ${label}`);
                }}
              >
                <Text
                  style={[
                    styles.segmentText,
                    { color: isSelected ? colors.onPrimary : colors.onSurface },
                  ]}
                >
                  {label}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>
      </CatalogCard>

      {/* 5. Deshabilitado y en estado de carga */}
      <CatalogCard
        title="5. Botón Deshabilitado y Estado de Carga (Loading)"
        description="Muestra el bloqueo táctil ante condiciones incompletas o la animación continua de una tarea en proceso."
        colors={colors}
      >
        <View style={styles.row}>
          <TouchableOpacity
            style={[styles.btn, { backgroundColor: colors.surfaceVariant, opacity: 0.6 }]}
            disabled={true}
          >
            <Text style={{ color: colors.outline, fontWeight: '700' }}>Deshabilitado</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.btn, { backgroundColor: colors.secondary }]}
            disabled={isLoading}
            onPress={() => {
              setIsLoading(true);
              setFeedback('Ejecutando proceso asíncrono...');
              setTimeout(() => {
                setIsLoading(false);
                triggerFeedback('Carga completada con éxito');
              }, 2000);
            }}
          >
            {isLoading ? (
              <ActivityIndicator color={colors.onPrimary} size="small" />
            ) : (
              <Text style={{ color: colors.onPrimary, fontWeight: '700' }}>Iniciar Carga</Text>
            )}
          </TouchableOpacity>
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
  feedbackBanner: {
    borderRadius: 8,
    padding: 12,
    marginBottom: 8,
  },
  feedbackText: {
    fontWeight: '700',
    fontSize: 13,
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  btn: {
    flex: 1,
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconBtn: {
    width: 48,
    height: 48,
    borderRadius: 24,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconTextBtn: {
    flex: 1,
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
  },
  fabCircle: {
    width: 54,
    height: 54,
    borderRadius: 27,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 4,
    shadowColor: '#000',
    shadowOpacity: 0.2,
    shadowRadius: 4,
  },
  fabExtended: {
    flex: 1,
    paddingVertical: 14,
    borderRadius: 27,
    alignItems: 'center',
    elevation: 4,
    shadowColor: '#000',
    shadowOpacity: 0.2,
    shadowRadius: 4,
  },
  segmentContainer: {
    flexDirection: 'row',
    borderWidth: 1,
    borderRadius: 8,
    overflow: 'hidden',
  },
  segmentBtn: {
    flex: 1,
    paddingVertical: 10,
    alignItems: 'center',
  },
  segmentText: {
    fontWeight: '600',
    fontSize: 13,
  },
});
