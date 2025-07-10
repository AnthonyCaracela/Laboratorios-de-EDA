import java.util.Scanner;

public class TestEjercicio7 {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 7: IMPLEMENTACIÓN COMPLETA ÁRBOL B+ ===\n");
        
        Scanner scanner = new Scanner(System.in);
        BTreePlus<Integer> btreePlus = null;
        
        while (true) {
            System.out.println("\n=== MENÚ ÁRBOL B+ ===");
            System.out.println("1. Crear árbol B+ (especificar grado)");
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
            System.out.println("12. Probar con datos del Ejercicio 5");
            System.out.println("13. Probar con datos del Ejercicio 6");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el grado del árbol B+: ");
                    int grado = scanner.nextInt();
                    btreePlus = new BTreePlus<>(grado);
                    System.out.println("Árbol B+ de grado " + grado + " creado.");
                    break;
                    
                case 2:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a insertar: ");
                    int elemento = scanner.nextInt();
                    btreePlus.insert(elemento);
                    System.out.println("Elemento " + elemento + " insertado.");
                    break;
                    
                case 3:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a eliminar: ");
                    int elementoElim = scanner.nextInt();
                    btreePlus.remove(elementoElim);
                    System.out.println("Elemento " + elementoElim + " eliminado.");
                    break;
                    
                case 4:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.print("Ingrese el elemento a buscar: ");
                    int elementoBuscar = scanner.nextInt();
                    boolean encontrado = btreePlus.search(elementoBuscar);
                    System.out.println("Elemento " + elementoBuscar + 
                                     (encontrado ? " encontrado." : " no encontrado."));
                    break;
                    
                case 5:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.println("Árbol B+:");
                    System.out.println(btreePlus.toString());
                    break;
                    
                case 6:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    Integer min = btreePlus.min();
                    System.out.println("Mínimo: " + (min != null ? min : "Árbol vacío"));
                    break;
                    
                case 7:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    Integer max = btreePlus.max();
                    System.out.println("Máximo: " + (max != null ? max : "Árbol vacío"));
                    break;
                    
                case 8:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.print("Ingrese el elemento para obtener su predecesor: ");
                    int elemPred = scanner.nextInt();
                    Integer pred = btreePlus.predecessor(elemPred);
                    System.out.println("Predecesor de " + elemPred + ": " + 
                                     (pred != null ? pred : "No existe"));
                    break;
                    
                case 9:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.print("Ingrese el elemento para obtener su sucesor: ");
                    int elemSuc = scanner.nextInt();
                    Integer suc = btreePlus.successor(elemSuc);
                    System.out.println("Sucesor de " + elemSuc + ": " + 
                                     (suc != null ? suc : "No existe"));
                    break;
                    
                case 10:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    System.out.println("¿Está vacío? " + btreePlus.isEmpty());
                    break;
                    
                case 11:
                    if (btreePlus == null) {
                        System.out.println("Primero debe crear un árbol B+.");
                        break;
                    }
                    btreePlus.destroy();
                    System.out.println("Árbol destruido.");
                    break;
                    
                case 12:
                    btreePlus = new BTreePlus<>(5);
                    Integer[] datos1 = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
                    System.out.println("Insertando datos del Ejercicio 5...");
                    for (Integer dato : datos1) {
                        btreePlus.insert(dato);
                    }
                    System.out.println("Datos insertados. Árbol B+ resultante:");
                    System.out.println(btreePlus.toString());
                    break;
                    
                case 13:
                    btreePlus = new BTreePlus<>(4);
                    Integer[] datos2 = {100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190};
                    System.out.println("Insertando datos del Ejercicio 6...");
                    for (Integer dato : datos2) {
                        btreePlus.insert(dato);
                    }
                    System.out.println("Datos insertados. Árbol B+ resultante:");
                    System.out.println(btreePlus.toString());
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
