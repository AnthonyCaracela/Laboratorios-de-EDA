import java.util.Scanner;

public class TrianguloRecursivo3 {
    private static int maxBase;

    /** Método inicial que guarda la base y arranca la recursión */
    public static void iniciar(int b) {
        maxBase = b;
        piramideRecursiva(1);
    }

    /**
     * Método recursivo que, para cada nivel ‘nivel’ desde 1 hasta maxBase,
     * imprime:
     *  - (maxBase - nivel) espacios
     *  - (2 * nivel - 1) asteriscos
     * y luego llama a sí mismo con nivel+1.
     */
    private static void piramideRecursiva(int nivel) {
        if (nivel > maxBase) {
            return;
        }
        // espacios antes de la pirámide
        for (int i = 0; i < maxBase - nivel; i++) {
            System.out.print(" ");
        }
        // asteriscos de la pirámide
        for (int j = 0; j < 2 * nivel - 1; j++) {
            System.out.print("*");
        }
        System.out.println();
        // recursión para el siguiente nivel
        piramideRecursiva(nivel + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la base de la pirámide: ");
        int b = sc.nextInt();
        iniciar(b);
        sc.close();
    }
}

