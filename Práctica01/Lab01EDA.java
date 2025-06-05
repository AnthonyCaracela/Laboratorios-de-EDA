import java.util.*;

public class LaboratoriosEDA {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Elige ejercicio: 1-Calificaciones  2-Criba  3-Inserción");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1 -> gestionCalificaciones(sc);
            case 2 -> cribaEratostenes(sc);
            case 3 -> insertionDemo();
            default -> System.out.println("Opción inválida");
        }
        sc.close();
    }

    // 1. Gestión de calificaciones
    static void gestionCalificaciones(Scanner sc) {
        System.out.print("¿Cuántos estudiantes? ");
        int N = sc.nextInt();
        List<Double> cal = new ArrayList<>();
        System.out.println("Introduce las calificaciones:");
        for (int i = 0; i < N; i++) {
            cal.add(sc.nextDouble());
        }
        Collections.sort(cal);
        // Mediana
        double med = (N % 2 == 1)
            ? cal.get(N/2)
            : (cal.get(N/2 - 1) + cal.get(N/2)) / 2.0;
        // Moda
        Map<Double,Integer> f = new HashMap<>();
        for (double c: cal) f.put(c, f.getOrDefault(c,0)+1);
        int maxF = Collections.max(f.values());
        List<Double> modas = new ArrayList<>();
        for (var e: f.entrySet()) if (e.getValue()==maxF) modas.add(e.getKey());
        // Desviación estándar
        double suma = cal.stream().mapToDouble(Double::doubleValue).sum();
        double media = suma / N;
        double var = cal.stream().mapToDouble(x -> (x-media)*(x-media)).sum() / N;
        double desv = Math.sqrt(var);

        System.out.printf("Mediana: %.2f%nModa: %s%nDesv. estándar: %.2f%n",
                          med, modas, desv);
    }

    // 2. Criba de Eratóstenes
    static void cribaEratostenes(Scanner sc) {
        System.out.print("Límite inferior: ");
        int a = sc.nextInt();
        System.out.print("Límite superior: ");
        int b = sc.nextInt();
        boolean[] esPrimo = new boolean[b+1];
        Arrays.fill(esPrimo, true);
        if (b>=0) esPrimo[0]=false;
        if (b>=1) esPrimo[1]=false;
        for (int i = 2; i*i <= b; i++) {
            if (esPrimo[i]) {
                for (int j = i*i; j <= b; j += i) {
                    esPrimo[j] = false;
                }
            }
        }
        System.out.print("Primos: ");
        for (int i = Math.max(a,2); i <= b; i++) {
            if (esPrimo[i]) System.out.print(i + " ");
        }
        System.out.println();
    }

    // 3. Demo de Insertion Sort
    static void insertionDemo() {
        int[] data = {5,2,9,1,5,6};
        System.out.println("Original: " + Arrays.toString(data));
        for (int i = 1; i < data.length; i++) {
            int key = data[i], j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j+1] = data[j];
                j--;
            }
            data[j+1] = key;
        }
        System.out.println("Ordenado: " + Arrays.toString(data));
    }
}
