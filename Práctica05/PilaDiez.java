// Nodo genérico
class Node<E> {
    private E data;
    private Node<E> next;
    public Node(E data) { this.data = data; this.next = null; }
    public E getData() { return data; }
    public Node<E> getNext() { return next; }
    public void setNext(Node<E> next) { this.next = next; }
}

// Stack genérico mínimo
class Stack<E> {
    private Node<E> top;

    public Stack() { top = null; }

    public void push(E value) {
        Node<E> n = new Node<>(value);
        n.setNext(top);
        top = n;
    }

    public E pop() {
        if (isEmpty()) throw new IllegalStateException("Stack vacía");
        E v = top.getData();
        top = top.getNext();
        return v;
    }

    public boolean isEmpty() {
        return top == null;
    }

    // Recorre sin modificarla
    public void printStack() {
        System.out.print("Stack [top→bot]: ");
        for (Node<E> p = top; p != null; p = p.getNext()) {
            System.out.print(p.getData() + " ");
        }
        System.out.println();
    }
}

// Clase principal de prueba
public class PilaDiez {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        for (int i = 1; i <= 10; i++) s.push(i);
        s.printStack();
        System.out.print("Pop all: ");
        while (!s.isEmpty()) {
            System.out.print(s.pop() + " ");
        }
    }
}

