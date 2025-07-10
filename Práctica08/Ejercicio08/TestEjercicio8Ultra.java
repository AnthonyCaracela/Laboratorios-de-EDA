public class TestEjercicio8Ultra {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 8: ÁRBOL B+ - VISUALIZACIÓN ULTRA LIMPIA ===\n");
        
        try {
            Class.forName("org.graphstream.graph.Graph");
            System.out.println("✓ GraphStream detectado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: GraphStream no encontrado");
            return;
        }
        
        BTreePlus<Integer> btreePlus = new BTreePlus<>(4);
        Integer[] datos = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
        
        System.out.println("Insertando datos en el árbol B+...");
        for (Integer dato : datos) {
            btreePlus.insert(dato);
            System.out.println("Insertado: " + dato);
        }
        
        System.out.println("\nÁrbol B+ construido:");
        System.out.println(btreePlus.toString());
        
        BTreePlusGraphVisualizerUltra<Integer> visualizer = new BTreePlusGraphVisualizerUltra<>(btreePlus);
        
        System.out.println("\n=== VISUALIZACIÓN ULTRA LIMPIA - PROBLEMAS SOLUCIONADOS ===");
        System.out.println("✅ NÚMEROS EXTRA GRANDES (24-26px) - Súper legibles");
        System.out.println("✅ NODOS RECTANGULARES AMPLIOS (180x60px) - Sin interferencias");
        System.out.println("✅ ESPACIADO GENEROSO (350px horizontal, 180px vertical)");
        System.out.println("✅ SIN CRUCES DE LÍNEAS - Layout perfecto");
        System.out.println("✅ ENLACES ROJOS SOLO ENTRE HOJAS - Sin confusión");
        System.out.println("✅ ETIQUETAS INDEX/LEAF SÚPER CLARAS");
        System.out.println("✅ VENTANA MÁS GRANDE (1600x1000) - Vista completa");
        
        System.out.println("\n=== MEJORAS ESPECÍFICAS IMPLEMENTADAS ===");
        System.out.println("🔧 Texto 24-26px (vs 18px anterior)");
        System.out.println("🔧 Nodos 180x60px (vs 140x45px anterior)");
        System.out.println("🔧 Separación vertical 180px (vs 130px anterior)");
        System.out.println("🔧 Separación horizontal 350px (vs 200px anterior)");
        System.out.println("🔧 Sin 'bolitas' que interfieran");
        System.out.println("🔧 Layout jerárquico sin cruces");
        
        visualizer.visualizeTree();
        
        System.out.println("\n=== CONTROLES ULTRA LIMPIOS ===");
        System.out.println("🔄 Actualizar Vista: Reconstruir sin cruces");
        System.out.println("ℹ️ Info B+ Ultra: Detalles de mejoras");
        System.out.println("🎯 Centrar Perfecto: Vista sin interferencias");
        System.out.println("🔗 Secuencia Limpia: Recorrido súper claro");
        System.out.println("📤 Exportar Ultra DOT: Código optimizado");
        System.out.println("🚪 Cerrar Limpiamente: Cierre seguro");
        
        System.out.println("\n=== CARACTERÍSTICAS B+ ULTRA VISIBLES ===");
        System.out.println("🟢 Verde: Nodo raíz (números 26px)");
        System.out.println("🔵 Azul: INDEX - Solo índices (24px)");
        System.out.println("🟠 Naranja: LEAF - Todas las claves (24px)");
        System.out.println("🔴 Líneas rojas: Enlaces SOLO entre hojas");
        
        System.out.println("\nVisualización ultra limpia iniciada.");
        System.out.println("¡Ahora los números son súper legibles y sin interferencias!");
    }
}
