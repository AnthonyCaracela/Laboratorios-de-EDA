import java.util.Scanner;

public class TestEjercicio3 {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 3: IMPLEMENTACIÓN COMPLETA ÁRBOL B ===\n");
        
        Scanner scanner = new Scanner(System.in);
        BTree<Integer> btree = null;
        
        while (true) {
            System.out.println("\n=== MENÚ ÁRBOL B ===");
            System.out.println("1. Crear árbol B (especificar grado)");
            System.out.println("2. Insertar elemento");
            System.out.println("3. Eliminar elemento");
            System.out.println("4. Buscar elemento");
            System.out.println("5. Mostrar árbol");
            System.out.println("6. Obtener mínimo");
            System.out.println("7. Obtener máximo");
            System.out.println("8. Obtener predecesor");
            System.out.println("9. Obtener sucesor");
            System.out.println("10. Verificar si está vacío");
            System.out.println("11. Destruir árbol");
            System.out.println("12. Probar con datos del Ejercicio 1");
            System.out.println("13. Probar con datos del Ejercicio 2");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el grado del árbol B: ");
                    int grado = scanner.nextInt();
                    btree = new BTree<>(grado);
                    System.out.println("Árbol B de grado " + grado + " creado.");
                    break;
                    
                case 2:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a insertar: ");
                    int elemento = scanner.nextInt();
                    btree.insert(elemento);
                    System.out.println("Elemento " + elemento + " insertado.");
                    break;
                    
                case 3:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a eliminar: ");
                    int elementoElim = scanner.nextInt();
                    btree.remove(elementoElim);
                    System.out.println("Elemento " + elementoElim + " eliminado.");
                    break;
                    
                case 4:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a buscar: ");
                    int elementoBuscar = scanner.nextInt();
                    boolean encontrado = btree.search(elementoBuscar);
                    System.out.println("Elemento " + elementoBuscar + 
                                     (encontrado ? " encontrado." : " no encontrado."));
                    break;
                    
                case 5:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.println("Árbol B:");
                    System.out.println(btree.toString());
                    break;
                    
                case 6:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    Integer min = btree.min();
                    System.out.println("Mínimo: " + (min != null ? min : "Árbol vacío"));
                    break;
                    
                case 7:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    Integer max = btree.max();
                    System.out.println("Máximo: " + (max != null ? max : "Árbol vacío"));
                    break;
                    
                case 8:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.print("Ingrese el elemento para obtener su predecesor: ");
                    int elemPred = scanner.nextInt();
                    Integer pred = btree.predecessor(elemPred);
                    System.out.println("Predecesor de " + elemPred + ": " + 
                                     (pred != null ? pred : "No existe"));
                    break;
                    
                case 9:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.print("Ingrese el elemento para obtener su sucesor: ");
                    int elemSuc = scanner.nextInt();
                    Integer suc = btree.successor(elemSuc);
                    System.out.println("Sucesor de " + elemSuc + ": " + 
                                     (suc != null ? suc : "No existe"));
                    break;
                    
                case 10:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    System.out.println("¿Está vacío? " + btree.isEmpty());
                    break;
                    
                case 11:
                    if (btree == null) {
                        System.out.println("Primero debe crear un árbol B.");
                        break;
                    }
                    btree.destroy();
                    System.out.println("Árbol destruido.");
                    break;
                    
                case 12:
                    btree = new BTree<>(5);
                    Integer[] datos1 = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
                    System.out.println("Insertando datos del Ejercicio 1...");
                    for (Integer dato : datos1) {
                        btree.insert(dato);
                    }
                    System.out.println("Datos insertados. Árbol resultante:");
                    System.out.println(btree.toString());
                    break;
                    
                case 13:
                    btree = new BTree<>(4);
                    Integer[] datos2 = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
                    System.out.println("Insertando datos del Ejercicio 2...");
                    for (Integer dato : datos2) {
                        btree.insert(dato);
                    }
                    System.out.println("Datos insertados. Árbol resultante:");
                    System.out.println(btree.toString());
                    break;
                    
                case 0:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
