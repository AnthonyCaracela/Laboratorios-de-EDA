// ------------------------------------------------------------
// Punto 2: Lista circular simple genérica con nodos propios
// ------------------------------------------------------------
public class Punto2CircularLinkedList<E> {

    static class Node<E> {
        E data;
        Node<E> next;
        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> head = null;

    // Inserta al final de la lista circular
    public void insert(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            Node<E> curr = head;
            // avanzar hasta el último (el que apunta a head)
            while (curr.next != head) {
                curr = curr.next;
            }
            curr.next = newNode;
            newNode.next = head;
        }
    }

    // Imprime exactamente 'count' elementos desde head (para no entrar en bucle infinito)
    public void print(int count) {
        if (head == null) {
            System.out.println("Circular: ");
            return;
        }
        System.out.print("Circular: ");
        Node<E> curr = head;
        for (int i = 0; i < count; i++) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Probamos con Integer (elementos 1 al 12)
        Punto2CircularLinkedList<Integer> cll = new Punto2CircularLinkedList<>();
        for (int i = 1; i <= 12; i++) {
            cll.insert(i);
        }
        cll.print(12);  // Debe imprimir: Circular: 1 2 3 4 5 6 7 8 9 10 11 12
    }
}
