// -----------------------------------------------------------------
// Punto 4: Lista circular simulada usando java.util.LinkedList<E>
// -----------------------------------------------------------------
import java.util.LinkedList;

public class Punto4JavaCircularLinkedList {

    public static void main(String[] args) {
        // Creamos una LinkedList<Integer> e insertamos del 1 al 12
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }

        // Simulamos un recorrido circular mostrando exactamente 12 elementos
        System.out.print("Circular traversal: ");
        int size = list.size();
        for (int i = 0; i < 12; i++) {
            System.out.print(list.get(i % size) + " ");
        }
        System.out.println();
    }
}

