class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
/**
 * Implementación genérica de Cola (FIFO) usando lista enlazada.
 */
class QueueList<T> {
    private Nodo<T> frente;   // Primer elemento de la cola
    private Nodo<T> atras;    // Último elemento de la cola

    public QueueList() {
        this.frente = null;
        this.atras = null;
    }

    /** Verifica si la cola está vacía */
    public boolean isEmpty() {
        return frente == null;
    }

    /** Inserta (enqueue) un elemento al final de la cola */
    public void enqueue(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (isEmpty()) {
            // Si la cola está vacía, frente y atras apuntan al nuevo nodo
            frente = atras = nuevo;
        } else {
            // Enlazar el nodo actual 'atras' al nuevo y actualizar 'atras'
            atras.setSiguiente(nuevo);
            atras = nuevo;
        }
    }

    /** Elimina (dequeue) el elemento al frente y lo devuelve */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        T valor = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            // Si después de retirar queda vacía, 'atras' también debe ser null
            atras = null;
        }
        return valor;
    }

    /** Retorna el elemento al frente sin quitarlo */
    public T front() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return frente.getDato();
    }

    /** Retorna el último elemento (back) sin quitarlo */
    public T back() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return atras.getDato();
    }

    /** Vacía toda la cola */
    public void destroyQueue() {
        frente = atras = null;
    }

    /**
     * Imprime el contenido actual de la cola desde el frente hasta el final.
     * (No modifica la cola: recorre temporalmente)
     */
    public void printQueue() {
        System.out.print("Cola [frente → fin]: ");
        Nodo<T> actual = frente;
        while (actual != null) {
            System.out.print(actual.getDato() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }
}
public class TestQueueList {
    public static void main(String[] args) {
        QueueList<Integer> cola = new QueueList<>();

        // Insertar (enqueue) los elementos 1,2,3,4,5,6,7,8
        for (int i = 1; i <= 8; i++) {
            cola.enqueue(i);
            System.out.println("enqueue(" + i + ")");
        }

        // Mostrar la cola sin modificarla
        System.out.println();
        cola.printQueue();
        // Ejemplo de salida esperada: Cola [frente → fin]: 1 2 3 4 5 6 7 8

        // Remover todos los elementos uno por uno (dequeue) para demostrar FIFO
        System.out.println("\nDesencolando todos los elementos:");
        while (!cola.isEmpty()) {
            int valor = cola.dequeue();
            System.out.println("dequeue() → " + valor);
        }

        // Intentar front() o dequeue() ahora lanzaría excepción porque la cola está vacía
    }
}

