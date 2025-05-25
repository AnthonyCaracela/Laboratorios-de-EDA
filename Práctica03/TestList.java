// Node.java
class Node<T> {
    T data;
    Node<T> nextNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
    }
}

// MyList.java
class MyList<T> {
    private Node<T> root;
    private int length;

    public MyList() {
        this.root = null;
        this.length = 0;
    }

    // Añade un elemento al final
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (root == null) {
            root = newNode;
        } else {
            Node<T> curr = root;
            while (curr.nextNode != null) {
                curr = curr.nextNode;
            }
            curr.nextNode = newNode;
        }
        length++;
    }

    // Obtiene el elemento en índice
    public T get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        Node<T> curr = root;
        for (int i = 0; i < index; i++) {
            curr = curr.nextNode;
        }
        return curr.data;
    }

    // Elimina la primera aparición de value
    public boolean remove(T value) {
        if (root == null) return false;
        if (root.data.equals(value)) {
            root = root.nextNode;
            length--;
            return true;
        }
        Node<T> prev = root, curr = root.nextNode;
        while (curr != null) {
            if (curr.data.equals(value)) {
                prev.nextNode = curr.nextNode;
                length--;
                return true;
            }
            prev = curr;
            curr = curr.nextNode;
        }
        return false;
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = root;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.nextNode != null) sb.append(", ");
            curr = curr.nextNode;
        }
        sb.append("]");
        return sb.toString();
    }
}

// TestList.java (main de prueba)
public class TestList {
    public static void main(String[] args) {
        MyList<String> lista = new MyList<>();
        System.out.println("¿Vacía? " + lista.isEmpty());          // true
        lista.add("MARIA");
        lista.add("DIEGO");
        lista.add("RENE");
        System.out.println("Contenido: " + lista);                // [MARIA, DIEGO, RENE]
        System.out.println("Tamaño: " + lista.size());            // 3
        System.out.println("Elemento 1: " + lista.get(1));        // DIEGO
        lista.remove("DIEGO");
        System.out.println("Después remove: " + lista);           // [MARIA, RENE]
        System.out.println("Tamaño: " + lista.size());            // 2
    }
}