import java.util.Scanner;

public class TrianguloRecursivo1 {
    // Método recursivo que imprime filas crecientes de asteriscos
    public static void trianguloRecursivo1(int base) {
        if (base <= 0) {
            return;
        }
        trianguloRecursivo1(base - 1);
        // Al volver de la llamada, imprimimos la fila de longitud 'base'
        for (int i = 0; i < base; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la base del triángulo: ");
        int b = sc.nextInt();
        trianguloRecursivo1(b);
        sc.close();
    }
}

