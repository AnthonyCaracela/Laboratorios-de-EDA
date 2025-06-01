// -----------------------------------------------------------------
// Punto 3: Lista doblemente enlazada usando java.util.LinkedList<E>
// -----------------------------------------------------------------
import java.util.LinkedList;
import java.util.Iterator;

public class Punto3JavaDoublyLinkedList {

    public static void main(String[] args) {
        // Creamos una LinkedList<Integer> e insertamos del 1 al 10
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        // Impresión directa
        System.out.println("List: " + list);
        // Recorremos en orden inverso usando descendingIterator()
        System.out.print("Reverse: ");
        Iterator<Integer> desc = list.descendingIterator();
        while (desc.hasNext()) {
            System.out.print(desc.next() + " ");
        }
        System.out.println();
    }
}

