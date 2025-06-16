import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import java.util.concurrent.atomic.AtomicInteger;
// Nodo genérico del BST
class Node<T> {
    T data;
    Node<T> left, right;
    public Node(T data) {
        this.data  = data;
        this.left  = null;
        this.right = null;
    }
}
// Árbol binario de búsqueda con in-, pre- y post-orden
class BST<T extends Comparable<T>> {
    private Node<T> root;
    public BST() {
        root = null;
    }
    public void insert(T value) {
        root = insertRec(root, value);
    }
    private Node<T> insertRec(Node<T> n, T v) {
        if (n == null) return new Node<>(v);
        int cmp = v.compareTo(n.data);
        if      (cmp < 0) n.left  = insertRec(n.left,  v);
        else if (cmp > 0) n.right = insertRec(n.right, v);
        // duplicados ignorados
        return n;
    }
    public Node<T> getRoot() {
        return root;
    }
    // IN-ORDER
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }
    private void inOrderRec(Node<T> n) {
        if (n == null) return;
        inOrderRec(n.left);
        System.out.print(n.data + " ");
        inOrderRec(n.right);
    }
    // PRE-ORDER
    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }
    private void preOrderRec(Node<T> n) {
        if (n == null) return;
        System.out.print(n.data + " ");
        preOrderRec(n.left);
        preOrderRec(n.right);
    }
    // POST-ORDER
    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }
    private void postOrderRec(Node<T> n) {
        if (n == null) return;
        postOrderRec(n.left);
        postOrderRec(n.right);
        System.out.print(n.data + " ");
    }
}
// Clase principal que grafica el BST con layout jerárquico
public class GraphBST<T extends Comparable<T>> {
    private final BST<T> tree;
    static {
        System.setProperty("org.graphstream.ui", "swing");
    }
    public GraphBST(BST<T> tree) {
        this.tree = tree;
    }
    public void displayHierarchical() {
        Graph g = new SingleGraph("BST");
        g.setAttribute("ui.stylesheet",
            "node { fill-color: lightblue; size: 30px, 30px; text-size: 14; }" +
            "edge { arrow-shape: arrow; }"
        );
        addNode(tree.getRoot(), g);
        Viewer viewer = g.display(false);
        viewer.disableAutoLayout();
        AtomicInteger xCnt = new AtomicInteger(0);
        assignPositions(tree.getRoot(), 0, xCnt, g);
    }
    private void assignPositions(Node<T> n, int depth,
                                 AtomicInteger xCnt, Graph g) {
        if (n == null) return;
        assignPositions(n.left,  depth+1, xCnt, g);
        String id = n.data.toString();
        org.graphstream.graph.Node gn = g.getNode(id);
        double x = xCnt.getAndIncrement() * 2.0;
        double y = -depth * 2.0;
        gn.setAttribute("x", x);
        gn.setAttribute("y", y);
        assignPositions(n.right, depth+1, xCnt, g);
    }
    private void addNode(Node<T> n, Graph g) {
        if (n == null) return;
        String id = n.data.toString();
        if (g.getNode(id) == null) {
            g.addNode(id).setAttribute("ui.label", id);
        }
        if (n.left != null) {
            String l = n.left.data.toString();
            if (g.getNode(l) == null) {
                g.addNode(l).setAttribute("ui.label", l);
            }
            String eL = id + "_L_" + l;
            if (g.getEdge(eL) == null) {
                g.addEdge(eL, id, l, true);
            }
            addNode(n.left, g);
        }
        if (n.right != null) {
            String r = n.right.data.toString();
            if (g.getNode(r) == null) {
                g.addNode(r).setAttribute("ui.label", r);
            }
            String eR = id + "_R_" + r;
            if (g.getEdge(eR) == null) {
                g.addEdge(eR, id, r, true);
            }
            addNode(n.right, g);
        }
    }
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] vals = {7, 3, 9, 1, 5, 8, 10};
        for (int v : vals) bst.insert(v);
        // Imprime los tres recorridos
        System.out.print("InOrder:   ");   bst.inOrder();
        System.out.print("PreOrder:  ");   bst.preOrder();
        System.out.print("PostOrder: ");   bst.postOrder();
        // Muestra el BST gráficamente
        new GraphBST<>(bst).displayHierarchical();
    }
}