import java.util.Scanner;

// Nodo genérico
class Node<E> {
    E data; Node<E> next;
    Node(E data) { this.data = data; this.next = null; }
}

// Stack genérico completo
class Stack<E> {
    private Node<E> top;
    public Stack() { top = null; }

    public boolean isEmpty() { return top == null; }
    public boolean isFull() { return false; }  // nunca está “llena”
    public void push(E value) {
        Node<E> n = new Node<>(value);
        n.next = top;
        top = n;
    }
    public E pop() {
        if (isEmpty()) throw new IllegalStateException("Stack vacía");
        E v = top.data;
        top = top.next;
        return v;
    }
    public E top() {
        if (isEmpty()) throw new IllegalStateException("Stack vacía");
        return top.data;
    }
    public void destroyStack() { top = null; }
    public void printStack() {
        System.out.print("Stack [top→bot]: ");
        for (Node<E> p = top; p != null; p = p.next) {
            System.out.print(p.data + " ");
        }
        System.out.println();
    }
}

// Clase principal con menú
public class PrincipalStack {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        // Precarga 1–10
        for (int i = 1; i <= 10; i++) s.push(i);
        Scanner sc = new Scanner(System.in);
        int opc, val;
        do {
            System.out.println("\n1) Push  2) Pop  3) Top  4) Print  5) isEmpty  6) isFull  7) Destroy  8) Salir");
            System.out.print("Opción: ");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    System.out.print("Valor: "); val = sc.nextInt();
                    s.push(val);
                    break;
                case 2:
                    System.out.println("pop() → " + s.pop());
                    break;
                case 3:
                    System.out.println("top() → " + s.top());
                    break;
                case 4:
                    s.printStack();
                    break;
                case 5:
                    System.out.println("isEmpty() → " + s.isEmpty());
                    break;
                case 6:
                    System.out.println("isFull() → " + s.isFull());
                    break;
                case 7:
                    s.destroyStack();
                    System.out.println("Stack vaciada");
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opc != 8);
        sc.close();
    }
}
