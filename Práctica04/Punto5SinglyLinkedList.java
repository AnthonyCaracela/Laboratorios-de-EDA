// ------------------------------------------------------------
// Punto 5: Lista enlazada simple genérica con métodos y menú
// ------------------------------------------------------------
import java.util.Scanner;

public class Punto5SinglyLinkedList<E> {
    static class Node<E> {
        E data;
        Node<E> next;
        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> head;

    // 1) Inserta al final
    public void addLast(E data) {
        if (head == null) {
            head = new Node<>(data);
        } else {
            Node<E> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node<>(data);
        }
    }

    // 2) Inserta al inicio
    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
    }

    // 3) Elimina por clave (primera ocurrencia) y devuelve true/false
    public boolean deleteByKey(E key) {
        if (head == null) return false;
        if (head.data.equals(key)) {
            head = head.next;
            return true;
        }
        Node<E> curr = head;
        while (curr.next != null && !curr.next.data.equals(key)) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
            return true;
        }
        return false;
    }

    // 4) Elimina en posición idx (0-based) y devuelve true/false
    public boolean deleteAtPosition(int idx) {
        if (idx < 0 || head == null) return false;
        if (idx == 0) {
            head = head.next;
            return true;
        }
        Node<E> curr = head;
        int i = 0;
        while (curr.next != null && i < idx - 1) {
            curr = curr.next;
            i++;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
            return true;
        }
        return false;
    }

    // 5) Devuelve el tamaño de la lista
    public int size() {
        int count = 0;
        Node<E> curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // 6) Elimina y retorna el primer elemento (o null si está vacía)
    public E removeFirst() {
        if (head == null) return null;
        E val = head.data;
        head = head.next;
        return val;
    }

    // 7) Elimina y retorna el último elemento (o null si está vacía)
    public E removeLast() {
        if (head == null) return null;
        if (head.next == null) {
            E val = head.data;
            head = null;
            return val;
        }
        Node<E> curr = head;
        while (curr.next.next != null) {
            curr = curr.next;
        }
        E val = curr.next.data;
        curr.next = null;
        return val;
    }

    // 8) Imprime todos los elementos
    public void printList() {
        System.out.print("List: ");
        Node<E> curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Creamos la lista de Integer y el Scanner para el menú
        Punto5SinglyLinkedList<Integer> sl = new Punto5SinglyLinkedList<>();
        Scanner sc = new Scanner(System.in);

        // Inicializamos con los enteros del 1 al 10
        for (int i = 1; i <= 10; i++) {
            sl.addLast(i);
        }

        int op;
        do {
            System.out.println("\n1.AddFirst  2.AddLast  3.DeleteByKey  4.DeleteAtPosition");
            System.out.println("5.RemoveFirst  6.RemoveLast  7.Size  8.Print  0.Exit");
            System.out.print("Select option: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Value? ");
                    sl.addFirst(sc.nextInt());
                    break;
                case 2:
                    System.out.print("Value? ");
                    sl.addLast(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Key? ");
                    boolean removed = sl.deleteByKey(sc.nextInt());
                    System.out.println("Removed: " + removed);
                    break;
                case 4:
                    System.out.print("Index? ");
                    boolean removedAt = sl.deleteAtPosition(sc.nextInt());
                    System.out.println("Removed at position: " + removedAt);
                    break;
                case 5:
                    Integer first = sl.removeFirst();
                    System.out.println("Removed first: " + first);
                    break;
                case 6:
                    Integer last = sl.removeLast();
                    System.out.println("Removed last: " + last);
                    break;
                case 7:
                    System.out.println("Size: " + sl.size());
                    break;
                case 8:
                    sl.printList();
                    break;
                case 0:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (op != 0);

        sc.close();
    }
}

