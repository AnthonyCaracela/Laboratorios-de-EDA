
//  Nodo genérico para estructuras basadas en listas enlazadas.

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

//  Implementación genérica de Pila (LIFO) usando lla ista enlazada.

class StackList<T> {
    private Nodo<T> cima;  // Referencia al nodo que está en la cima de la pila

    public StackList() {
        this.cima = null;
    }

//     Verifica si la pila está vacía 
    public boolean isEmpty() {
        return cima == null;
    }

    /* Devuelve el elemento de la cima sin retirarlo (top) */
    public T top() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return cima.getDato();
    }

    /** Inserta (push) un elemento en la cima de la pila */
    public void push(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.setSiguiente(cima);
        cima = nuevo;
    }

    /** Elimina (pop) el elemento de la cima y lo devuelve */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        T valor = cima.getDato();
        cima = cima.getSiguiente();
        return valor;
    }

    /** Vacía toda la pila */
    public void destroyStack() {
        cima = null;
    }

    /*
      Imprime el contenido actual de la pila desde la cima hacia abajo.
      (No modifica la pila: recorre temporalmente)
     */
    public void printStack() {
        System.out.print("Pila [cima → fondo]: ");
        Nodo<T> actual = cima;
        while (actual != null) {
            System.out.print(actual.getDato() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }
}
public class TestStackList {
    public static void main(String[] args) {
        StackList<Integer> pila = new StackList<>();

        // Insertar los elementos 1 al 8 en la pila
        for (int i = 1; i <= 8; i++) {
            pila.push(i);
            System.out.println("push(" + i + ")");
        }

        // Mostrar la pila sin modificarla
        System.out.println();
        pila.printStack();  
        // Ejemplo de salida esperada: Pila [cima → fondo]: 8 7 6 5 4 3 2 1
        System.out.println();
        System.out.println("¿La pila esta vacia? "+pila.isEmpty());
        // Comprobación si la tabla esta vacia
        // Remover todos los elementos uno por uno (pop) para demostrar LIFO
        System.out.println("\nDesapilando todos los elementos:");
        while (!pila.isEmpty()) {
            int valor = pila.pop();
            System.out.println("pop() → " + valor);
        }

        // Intentar top() o pop() ahora lanzaría excepción porque la pila está vacía
    }
}
