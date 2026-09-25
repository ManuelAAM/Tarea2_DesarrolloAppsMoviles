import React, { ReactNode } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { ThemeColors } from '../theme/colors';

interface CatalogCardProps {
  title: string;
  description: string;
  colors: ThemeColors;
  children?: ReactNode;
}

export const CatalogCard: React.FC<CatalogCardProps> = ({
  title,
  description,
  colors,
  children,
}) => {
  return (
    <View
      style={[
        styles.card,
        {
          backgroundColor: colors.surface,
          borderColor: colors.outlineVariant,
        },
      ]}
    >
      <Text style={[styles.title, { color: colors.primary }]}>{title}</Text>
      <Text style={[styles.description, { color: colors.onSurfaceVariant }]}>
        {description}
      </Text>
      <View style={styles.content}>{children}</View>
    </View>
  );
};

const styles = StyleSheet.create({
  card: {
    borderRadius: 12,
    borderWidth: 1,
    padding: 16,
    marginVertical: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 1,
  },
  title: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 4,
  },
  description: {
    fontSize: 13,
    lineHeight: 18,
    marginBottom: 12,
  },
  content: {
    width: '100%',
  },
});
