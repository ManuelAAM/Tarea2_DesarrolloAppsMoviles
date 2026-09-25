// Type stubs so TypeScript compiler validates without node_modules
declare namespace React {
  export type FC<P = {}> = (props: P) => any;
  export type ReactNode = any;
}

declare module 'react' {
  export function useState<T = any>(initial: T | (() => T)): [T, (val: T | ((prev: T) => T)) => void];
  export function useEffect(effect: () => void | (() => void), deps?: any[]): void;
  export function useRef<T = any>(initial?: T): { current: T };
  export function createContext<T = any>(defaultValue: T): any;
  export function useContext<T = any>(context: any): T;
  export type FC<P = {}> = (props: P) => any;
  export type ReactNode = any;
  const React: any;
  export default React;
}

declare module 'react-native' {
  export const View: any;
  export const Text: any;
  export const TextInput: any;
  export const TouchableOpacity: any;
  export const ScrollView: any;
  export const FlatList: any;
  export const SectionList: any;
  export const StyleSheet: any;
  export const SafeAreaView: any;
  export const StatusBar: any;
  export const Switch: any;
  export const Image: any;
  export const Modal: any;
  export const Alert: any;
  export const ActivityIndicator: any;
  export const RefreshControl: any;
  export const Dimensions: any;
  export const Platform: any;
  export function useColorScheme(): 'light' | 'dark' | null | undefined;
}
