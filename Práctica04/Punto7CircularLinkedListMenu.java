// -----------------------------------------------------------------------------------------
// Punto 7: Lista circular genérica con nodos propios, métodos vistos y menú de opciones
// -----------------------------------------------------------------------------------------
import java.util.Scanner;

public class Punto7CircularLinkedListMenu<E> {

    static class Node<E> {
        E data;
        Node<E> next;
        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> head = null;

    // 1) Inserta al final
    public void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            newNode.next = head;
            return;
        }
        Node<E> curr = head;
        while (curr.next != head) {
            curr = curr.next;
        }
        curr.next = newNode;
        newNode.next = head;
    }

    // 2) Inserta al inicio
    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            Node<E> curr = head;
            while (curr.next != head) {
                curr = curr.next;
            }
            curr.next = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    // 3) Inserta en posición idx (0-based)
    public void insertAt(int idx, E data) {
        if (head == null || idx <= 0) {
            addFirst(data);
            return;
        }
        int count = 0;
        Node<E> curr = head;
        while (curr.next != head && count < idx - 1) {
            curr = curr.next;
            count++;
        }
        // Si llegamos al último o la lista tenía un solo nodo
        if (curr.next == head) {
            curr.next = new Node<>(data);
            curr.next.next = head;
        } else {
            Node<E> newNode = new Node<>(data);
            newNode.next = curr.next;
            curr.next = newNode;
        }
    }

    // 4) Elimina la primera ocurrencia por clave
    public boolean deleteByKey(E key) {
        if (head == null) return false;
        // Caso cabeza
        if (head.data.equals(key)) {
            if (head.next == head) { // solo un nodo
                head = null;
            } else {
                Node<E> curr = head;
                // buscamos el último para reajustar puntero
                while (curr.next != head) {
                    curr = curr.next;
                }
                curr.next = head.next;
                head = head.next;
            }
            return true;
        }
        Node<E> curr = head;
        while (curr.next != head && !curr.next.data.equals(key)) {
            curr = curr.next;
        }
        if (curr.next.data.equals(key)) {
            curr.next = curr.next.next;
            return true;
        }
        return false;
    }

    // 5) Elimina en posición idx (0-based)
    public boolean deleteAtPosition(int idx) {
        if (head == null || idx < 0) return false;
        if (idx == 0) {
            if (head.next == head) {
                head = null;
            } else {
                Node<E> curr = head;
                while (curr.next != head) {
                    curr = curr.next;
                }
                curr.next = head.next;
                head = head.next;
            }
            return true;
        }
        Node<E> curr = head;
        int count = 0;
        while (curr.next != head && count < idx - 1) {
            curr = curr.next;
            count++;
        }
        if (curr.next == head) {
            // índice fuera de rango
            return false;
        }
        curr.next = curr.next.next;
        return true;
    }

    // 6) Retorna el tamaño (número de nodos)
    public int size() {
        if (head == null) return 0;
        int count = 1;
        Node<E> curr = head.next;
        while (curr != head) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // 7) Elimina y retorna el primer elemento
    public E removeFirst() {
        if (head == null) return null;
        E val = head.data;
        if (head.next == head) { // un solo nodo
            head = null;
        } else {
            Node<E> curr = head;
            while (curr.next != head) {
                curr = curr.next;
            }
            curr.next = head.next;
            head = head.next;
        }
        return val;
    }

    // 8) Elimina y retorna el último elemento
    public E removeLast() {
        if (head == null) return null;
        if (head.next == head) {
            E val = head.data;
            head = null;
            return val;
        }
        Node<E> curr = head;
        while (curr.next.next != head) {
            curr = curr.next;
        }
        E val = curr.next.data;
        curr.next = head;
        return val;
    }

    // 9) Imprime todos los elementos una sola vuelta
    public void printList() {
        if (head == null) {
            System.out.println("Circular: ");
            return;
        }
        System.out.print("Circular: ");
        Node<E> curr = head;
        do {
            System.out.print(curr.data + " ");
            curr = curr.next;
        } while (curr != head);
        System.out.println();
    }

    public static void main(String[] args) {
        Punto7CircularLinkedListMenu<Integer> cll = new Punto7CircularLinkedListMenu<>();
        Scanner sc = new Scanner(System.in);

        // Inicializar con enteros 1 al 12
        for (int i = 1; i <= 12; i++) {
            cll.addLast(i);
        }

        int op;
        do {
            System.out.println("\n1.AddFirst  2.AddLast  3.InsertAt  4.DeleteByKey  5.DeleteAtPosition");
            System.out.println("6.Size  7.RemoveFirst  8.RemoveLast  9.Print  0.Exit");
            System.out.print("Select option: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Value? ");
                    cll.addFirst(sc.nextInt());
                    break;
                case 2:
                    System.out.print("Value? ");
                    cll.addLast(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Index? ");
                    int idx = sc.nextInt();
                    System.out.print("Value? ");
                    cll.insertAt(idx, sc.nextInt());
                    break;
                case 4:
                    System.out.print("Key? ");
                    boolean removed = cll.deleteByKey(sc.nextInt());
                    System.out.println("Removed: " + removed);
                    break;
                case 5:
                    System.out.print("Index? ");
                    boolean removedAt = cll.deleteAtPosition(sc.nextInt());
                    System.out.println("Removed at position: " + removedAt);
                    break;
                case 6:
                    System.out.println("Size: " + cll.size());
                    break;
                case 7:
                    Integer first = cll.removeFirst();
                    System.out.println("Removed first: " + first);
                    break;
                case 8:
                    Integer last = cll.removeLast();
                    System.out.println("Removed last: " + last);
                    break;
                case 9:
                    cll.printList();
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

