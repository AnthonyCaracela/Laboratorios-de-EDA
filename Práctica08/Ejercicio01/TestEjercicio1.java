public class TestEjercicio1 {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 1: ÁRBOL B DE GRADO 5 ===\n");
        
        BTree<Integer> btree = new BTree<>(5);
        Integer[] insertValues = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
        
        System.out.println("INSERCIÓN PASO A PASO:");
        System.out.println("======================");
        
        for (Integer value : insertValues) {
            btree.insert(value);
            btree.printStepByStep("Insertando", value);
        }
        
        System.out.println("\nÁRBOL FINAL DESPUÉS DE TODAS LAS INSERCIONES:");
        System.out.println("============================================");
        System.out.println(btree.toString());
        
        System.out.println("\nELIMINACIÓN PASO A PASO:");
        System.out.println("========================");
        
        for (Integer value : insertValues) {
            btree.remove(value);
            btree.printStepByStep("Eliminando", value);
        }
        
        System.out.println("\nÁRBOL FINAL DESPUÉS DE TODAS LAS ELIMINACIONES:");
        System.out.println("==============================================");
        System.out.println(btree.toString());
    }
}
