import java.util.Arrays;

public class ER1 {
    public static int[] invertirArray(int[] A) {
        int n = A.length;
        int[] Asalida = new int[n];
        for (int i = 0; i < n; i++) {
            Asalida[i] = A[n - 1 - i];
        }
        return Asalida;
    }

    public static void main(String[] args) {
        int[] A = {1, 2, 3};
        System.out.println("Arreglo original: " + Arrays.toString(A));
        int[] resultado = invertirArray(A);
        System.out.println("Arreglo invertido: " + Arrays.toString(resultado));
    }
}
