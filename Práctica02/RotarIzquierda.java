import java.util.Arrays;
import java.util.Scanner;

public class RotarIzquierda {
    // Método que rota un arreglo a la izquierda d posiciones
    public static int[] rotarIzquierdaArray(int[] A, int d) {
        int n = A.length;
        int[] salida = new int[n];
        // Normalizar d en caso sea mayor que n
        d = ((d % n) + n) % n;
        for (int i = 0; i < n; i++) {
            salida[i] = A[(i + d) % n];
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
        System.out.print("Ingrese d (número de posiciones a rotar): ");
        int d = sc.nextInt();
        int[] resultado = rotarIzquierdaArray(A, d);
        System.out.println("Arreglo rotado a la izquierda: " + Arrays.toString(resultado));
        sc.close();
    }
}

