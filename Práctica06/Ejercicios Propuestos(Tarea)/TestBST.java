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
public class TestBST {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();

        System.out.println("¿Está vacío? " + bst.isEmpty());
        int[] valores = {7, 3, 9, 1, 5, 8, 10, 5}; // 5 repetido
        for (int v : valores) {
            bst.insert(v);
        }

        System.out.print("InOrder:   "); bst.inOrder();   // 1 3 5 7 8 9 10
        System.out.print("PreOrder:  "); bst.preOrder();  // 7 3 1 5 9 8 10
        System.out.print("PostOrder: "); bst.postOrder(); // 1 5 3 8 10 9 7

        System.out.println("Mínimo: " + bst.min());
        System.out.println("Máximo: " + bst.max());
        System.out.println("Buscar 5: " + (bst.search(5) != null));
        System.out.println("Predecesor de 7: " + bst.predecessor(7));
        System.out.println("Sucesor de 7:   " + bst.successor(7));

        System.out.println("Eliminar 3: " + bst.remove(3));
        System.out.print("InOrder tras remove(3): "); bst.inOrder();

        System.out.println("¿Destruir árbol?");
        bst.destroy();
        System.out.println("  ¿Está vacío? " + bst.isEmpty());
    }
}