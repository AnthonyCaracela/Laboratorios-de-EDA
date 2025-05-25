import java.util.Scanner;

// Clase genérica sin public para poder estar en el mismo archivo
class Operador<T extends Number> {
    private T valor1, valor2;

    public Operador(T v1, T v2) {
        this.valor1 = v1;
        this.valor2 = v2;
    }

    public T getValor1() { return valor1; }
    public T getValor2() { return valor2; }
}

public class CalculadoraGenerica {
    static <T extends Number> double suma(T v1, T v2) {
        return v1.doubleValue() + v2.doubleValue();
    }
    static <T extends Number> double resta(T v1, T v2) {
        return v1.doubleValue() - v2.doubleValue();
    }
    static <T extends Number> double producto(T v1, T v2) {
        return v1.doubleValue() * v2.doubleValue();
    }
    static <T extends Number> double division(T v1, T v2) {
        return v1.doubleValue() / v2.doubleValue();
    }
    static <T extends Number> double potencia(T v1, T v2) {
        return Math.pow(v1.doubleValue(), v2.doubleValue());
    }
    static <T extends Number> double raizCuadrada(T v, T ignored) {
        return Math.sqrt(v.doubleValue());
    }
    static <T extends Number> double raizCubica(T v, T ignored) {
        return Math.cbrt(v.doubleValue());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n** Calculadora Genérica **");
            System.out.println("1. Suma\n2. Resta\n3. Producto\n4. División\n5. Potencia\n6. Raíz Cuadrada\n7. Raíz Cúbica\n8. Salir");
            System.out.print("Elige opción: ");
            opcion = sc.nextInt();

            if (opcion >= 1 && opcion <= 7) {
                System.out.print("Tipo de dato (1=Integer, 2=Double): ");
                int tipo = sc.nextInt();
                System.out.print("Valor1: ");
                Number v1 = (tipo == 1) ? sc.nextInt() : sc.nextDouble();
                System.out.print("Valor2: ");
                Number v2 = (tipo == 1) ? sc.nextInt() : sc.nextDouble();

                Operador<Number> op = new Operador<Number>(v1, v2);
                double res = 0;
                switch (opcion) {
                    case 1: res = suma(op.getValor1(), op.getValor2()); break;
                    case 2: res = resta(op.getValor1(), op.getValor2()); break;
                    case 3: res = producto(op.getValor1(), op.getValor2()); break;
                    case 4: res = division(op.getValor1(), op.getValor2()); break;
                    case 5: res = potencia(op.getValor1(), op.getValor2()); break;
                    case 6: res = raizCuadrada(op.getValor1(), op.getValor2()); break;
                    case 7: res = raizCubica(op.getValor1(), op.getValor2()); break;
                }
                System.out.println("Resultado: " + res);
            }
        } while (opcion != 8);
        System.out.println("¡Programa finalizado!");
        sc.close();
    }
}