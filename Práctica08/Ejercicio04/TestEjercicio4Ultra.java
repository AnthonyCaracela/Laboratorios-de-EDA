public class TestEjercicio4Ultra {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 4: ÁRBOL B - VISUALIZACIÓN ULTRA COMPLETA ===\n");
        
        try {
            Class.forName("org.graphstream.graph.Graph");
            System.out.println("✓ GraphStream detectado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: GraphStream no encontrado");
            return;
        }
        
        BTree<Integer> btree = new BTree<>(5);
        Integer[] datos = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
        
        System.out.println("Insertando datos en el árbol B...");
        for (Integer dato : datos) {
            btree.insert(dato);
            System.out.println("Insertado: " + dato);
        }
        
        System.out.println("\nÁrbol B construido:");
        System.out.println(btree.toString());
        
        BTreeGraphVisualizerUltra<Integer> visualizer = new BTreeGraphVisualizerUltra<>(btree);
        
        System.out.println("\n=== PROBLEMAS DE LA IMAGEN SOLUCIONADOS ===");
        System.out.println("❌ Árbol incompleto (extremos cortados) → ✅ ÁRBOL COMPLETO VISIBLE");
        System.out.println("❌ Números diminutos e ilegibles      → ✅ NÚMEROS EXTRA GRANDES (28-30px)");
        System.out.println("❌ Nodos como puntos pequeños         → ✅ RECTÁNGULOS AMPLIOS (200x70px)");
        System.out.println("❌ Vista mal centrada                 → ✅ CENTRADO AUTOMÁTICO PERFECTO");
        System.out.println("❌ Zoom inadecuado                    → ✅ ZOOM PARA VER TODO");
        
        System.out.println("\n=== MEJORAS ULTRA ESPECÍFICAS ===");
        System.out.println("🔧 Texto: 28-30px (vs 20px anterior)");
        System.out.println("🔧 Nodos: 200x70px (vs 120x40px anterior)");
        System.out.println("🔧 Separación horizontal: 400px (vs 200px anterior)");
        System.out.println("🔧 Separación vertical: 200px (vs 120px anterior)");
        System.out.println("🔧 Ventana: 1700x1100px (vs 1400x900px anterior)");
        System.out.println("🔧 Padding: 120px para ver extremos");
        
        visualizer.visualizeTree();
        
        System.out.println("\n=== CONTROLES ULTRA COMPLETOS ===");
        System.out.println("🔄 Actualizar Completo: Reconstruir vista completa");
        System.out.println("ℹ️ Info Ultra B: Detalles de todas las mejoras");
        System.out.println("🎯 Ver Todo el Árbol: Centrar para vista COMPLETA");
        System.out.println("🔍 Zoom Completo: Ajustar para ver extremos");
        System.out.println("📤 Exportar Ultra DOT: Código optimizado");
        System.out.println("🚪 Cerrar Completamente: Cierre seguro");
        
        System.out.println("\n=== CARACTERÍSTICAS ULTRA VISIBLES ===");
        System.out.println("🟢 Verde: Nodo raíz (números 30px)");
        System.out.println("🔵 Azul: Nodos internos (28px)");
        System.out.println("🟠 Naranja: Nodos hoja (28px)");
        System.out.println("⬜ Fondo blanco con texto negro súper claro");
        
        System.out.println("\nVisualización ultra completa iniciada.");
        System.out.println("¡Ahora puedes ver TODO el árbol con números súper legibles!");
    }
}
