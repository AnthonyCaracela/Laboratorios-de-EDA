import java.util.Scanner;
class Node<T> {
    T data;
    Node<T> left, right;
    public Node(T data) {
        this.data = data;
        this.left = this.right = null;
    }
}
class BST<T extends Comparable<T>> {
    private Node<T> root;
    public BST() {
        root = null;
    }
    public void destroy() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }
    public void insert(T value) {
        root = insertRec(root, value);
    }
    private Node<T> insertRec(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRec(node.left, value);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, value);
        } // si cmp==0: duplicado, lo ignoramos
        return node;
    }
    public boolean remove(T value) {
        if (search(value) == null) return false;
        root = removeRec(root, value);
        return true;
    }
    private Node<T> removeRec(Node<T> node, T value) {
        if (node == null) return null;
        int cmp = value.compareTo(node.data);
        if (cmp < 0) {
            node.left = removeRec(node.left, value);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, value);
        } else {
            // Caso 1: sin hijos o un solo hijo
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // Caso 2: dos hijos → reemplazo por sucesor
            T succValue = min(node.right);
            node.data = succValue;
            node.right = removeRec(node.right, succValue);
        }
        return node;
    }
    public Node<T> search(T value) {
        return searchRec(root, value);
    }
    private Node<T> searchRec(Node<T> node, T value) {
        if (node == null) return null;
        int cmp = value.compareTo(node.data);
        if (cmp == 0) return node;
        return cmp < 0
            ? searchRec(node.left, value)
            : searchRec(node.right, value);
    }
    public T min() {
        return min(root);
    }
    private T min(Node<T> node) {
        if (node == null) return null;
        while (node.left != null) node = node.left;
        return node.data;
    }
    public T max() {
        return max(root);
    }
    private T max(Node<T> node) {
        if (node == null) return null;
        while (node.right != null) node = node.right;
        return node.data;
    }
    public T predecessor(T value) {
        Node<T> current = search(value);
        if (current == null) return null;
        // Si tiene subárbol izquierdo, es el máximo de ese subárbol
        if (current.left != null) return max(current.left);
        // Si no, buscar ancestro más cercano por el que vinimos a la derecha
        Node<T> pred = null;
        Node<T> ancestor = root;
        while (ancestor != null) {
            int cmp = value.compareTo(ancestor.data);
            if (cmp > 0) {
                pred = ancestor;
                ancestor = ancestor.right;
            } else if (cmp < 0) {
                ancestor = ancestor.left;
            } else break;
        }
        return pred != null ? pred.data : null;
    }
    public T successor(T value) {
        Node<T> current = search(value);
        if (current == null) return null;
        if (current.right != null) return min(current.right);
        Node<T> succ = null;
        Node<T> ancestor = root;
        while (ancestor != null) {
            int cmp = value.compareTo(ancestor.data);
            if (cmp < 0) {
                succ = ancestor;
                ancestor = ancestor.left;
            } else if (cmp > 0) {
                ancestor = ancestor.right;
            } else break;
        }
        return succ != null ? succ.data : null;
    }
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }
    private void inOrderRec(Node<T> node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.print(node.data + " ");
        inOrderRec(node.right);
    }
    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }
    private void preOrderRec(Node<T> node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        preOrderRec(node.left);
        preOrderRec(node.right);
    }
    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }
    private void postOrderRec(Node<T> node) {
        if (node == null) return;
        postOrderRec(node.left);
        postOrderRec(node.right);
        System.out.print(node.data + " ");
    }
    // Getter para el nodo raíz (uso interno o gráfico)
    public Node<T> getRoot() {
        return root;
    }
}
public class WordBST {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una palabra: ");
        String palabra = sc.nextLine();
        BST<Integer> bst = new BST<>();
        // insertar cada código ASCII en el árbol
        for (char c : palabra.toCharArray()) {
            bst.insert((int) c);
        }
        System.out.print("Recorrido InOrder (ASCII): ");
        bst.inOrder();  // muestra los códigos ordenados
        System.out.print("Recorrido PreOrder (ASCII): ");
        bst.preOrder();
        System.out.print("Recorrido PostOrder (ASCII): ");
        bst.postOrder();
        sc.close();
    }
}