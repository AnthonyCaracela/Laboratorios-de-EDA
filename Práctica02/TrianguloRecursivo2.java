import java.util.Scanner;

public class TrianguloRecursivo2 {
    private static int maxBase;

    /** 
     * Guarda la base original y arranca la recursión 
     */
    public static void iniciar(int b) {
        maxBase = b;
        dibujarTriangulo(1);
    }

    /**
     * Método recursivo que imprime la fila 'nivel' del triángulo:
     *  - (maxBase - nivel) espacios
     *  - nivel asteriscos
     * Luego llama a sí mismo con nivel+1 hasta llegar a maxBase.
     */
    private static void dibujarTriangulo(int nivel) {
        if (nivel > maxBase) return;

        // Imprime espacios para alinear a la derecha
        for (int i = 0; i < maxBase - nivel; i++) {
            System.out.print(" ");
        }
        // Imprime 'nivel' asteriscos
        for (int j = 0; j < nivel; j++) {
            System.out.print("*");
        }
        System.out.println();

        // Llamada recursiva al siguiente nivel
        dibujarTriangulo(nivel + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la base del triángulo: ");
        int b = sc.nextInt();
        iniciar(b);
        sc.close();
    }
}


