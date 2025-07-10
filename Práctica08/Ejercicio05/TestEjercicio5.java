public class TestEjercicio5 {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 5: ÁRBOL B+ DE GRADO 5 ===\n");
        
        BTreePlus<Integer> btreePlus = new BTreePlus<>(5);
        Integer[] insertValues = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
        
        System.out.println("INSERCIÓN PASO A PASO:");
        System.out.println("======================");
        
        for (Integer value : insertValues) {
            btreePlus.insert(value);
            btreePlus.printStepByStep("Insertando", value);
        }
        
        System.out.println("\nÁRBOL B+ FINAL DESPUÉS DE TODAS LAS INSERCIONES:");
        System.out.println("===============================================");
        System.out.println(btreePlus.toString());
        
        System.out.println("\nELIMINACIÓN PASO A PASO:");
        System.out.println("========================");
        
        for (Integer value : insertValues) {
            btreePlus.remove(value);
            btreePlus.printStepByStep("Eliminando", value);
        }
        
        System.out.println("\nÁRBOL B+ FINAL DESPUÉS DE TODAS LAS ELIMINACIONES:");
        System.out.println("=================================================");
        System.out.println(btreePlus.toString());
    }
}
