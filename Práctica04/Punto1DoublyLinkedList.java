// ------------------------------------------------------------
// Punto 1: Lista doblemente enlazada genérica con nodos propios
// ------------------------------------------------------------
public class Punto1DoublyLinkedList<E> {

    static class Node<E> {
        E data;
        Node<E> prev, next;

        Node(E data) {
            this.data = data;
            this.prev = this.next = null;
        }
    }

    private Node<E> head, tail;

    // Inserta al final de la lista
    public void insert(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Recorre de adelante hacia atrás
    public void printForward() {
        System.out.print("Forward: ");
        Node<E> curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    // Recorre de atrás hacia adelante
    public void printBackward() {
        System.out.print("Backward: ");
        Node<E> curr = tail;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.prev;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Aquí probamos la lista con Integer (elementos 1 al 10)
        Punto1DoublyLinkedList<Integer> dll = new Punto1DoublyLinkedList<>();
        for (int i = 1; i <= 10; i++) {
            dll.insert(i);
        }
        dll.printForward();   // Debe imprimir: Forward: 1 2 3 4 5 6 7 8 9 10
        dll.printBackward();  // Debe imprimir: Backward: 10 9 8 7 6 5 4 3 2 1
    }
}
