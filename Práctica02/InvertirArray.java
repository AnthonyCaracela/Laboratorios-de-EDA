import java.util.Arrays;
import java.util.Scanner;

public class InvertirArray {
    // Método que invierte un arreglo de forma general
    public static int[] invertirArray(int[] A) {
        int n = A.length;
        int[] salida = new int[n];
        for (int i = 0; i < n; i++) {
            salida[i] = A[n - 1 - i];
        }
        return salida;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el tamaño del arreglo: ");
        int n = sc.nextInt();
        int[] A = new int[n];
        System.out.println("Ingrese los elementos del arreglo:");
        for (int i = 0; i < n; i++) {
            A[i] = sc.nextInt();
        }
        int[] resultado = invertirArray(A);
        System.out.println("Arreglo invertido: " + Arrays.toString(resultado));
        sc.close();
    }
}
