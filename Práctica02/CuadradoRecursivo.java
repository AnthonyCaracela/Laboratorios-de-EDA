import java.util.Scanner;

public class CuadradoRecursivo {
    private static int maxBase;

    public static void iniciar(int b) {
        maxBase = b;
        cuadradoRecursivo(1);
    }

    // Método recursivo que va fila por fila
    private static void cuadradoRecursivo(int fila) {
        if (fila > maxBase) return;
        if (fila == 1 || fila == maxBase) {
            // Primera o última fila: todos asteriscos
            for (int i = 0; i < maxBase; i++) System.out.print("*");
        } else {
            // Filas intermedias: asterisco, espacios, asterisco
            System.out.print("*");
            for (int i = 0; i < maxBase - 2; i++) System.out.print(" ");
            System.out.print("*");
        }
        System.out.println();
        cuadradoRecursivo(fila + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la base del cuadrado: ");
        int b = sc.nextInt();
        iniciar(b);
        sc.close();
    }
}

