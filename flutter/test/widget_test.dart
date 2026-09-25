import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_catalog/main.dart';

void main() {
  testWidgets('App smoke test', (WidgetTester tester) async {
    await tester.pumpWidget(const CatalogApp());
    expect(find.text('Catálogo UI (Inicio)'), findsOneWidget);
  });
}
