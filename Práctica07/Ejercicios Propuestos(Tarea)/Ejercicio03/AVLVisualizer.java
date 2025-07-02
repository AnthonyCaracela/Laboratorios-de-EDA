// File: AVLVisualizer.java
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class AVLVisualizer {
    /**
     * Dibuja el árbol AVL con un layout jerárquico y estilo resaltado.
     */
    public static <T extends Comparable<T>> void visualize(AVLTree<T> tree) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph g = new SingleGraph("AVL");
        g.setStrict(false);
        g.setAutoCreate(true);

        // CSS para nodos azul oscuro y texto blanco en negrita
        String css =
          "node {" +
          "   shape: circle;" +
          "   size: 50px, 50px;" +
          "   fill-color: #1f77b4;" +        // azul oscuro
          "   stroke-mode: plain;" +
          "   stroke-color: black;" +
          "   text-size: 20px;" +
          "   text-color: white;" +
          "   text-style: bold;" +
          "}" +
          "edge {" +
          "   fill-color: #888888;" +
          "   size: 2px;" +
          "}";

        g.setAttribute("ui.stylesheet", css);

        buildGraph(tree.getRoot(), g);

        // Mostrar sin auto‑layout
        Viewer viewer = g.display(false);

        // Asignar coordenadas manualmente
        AtomicInteger xCounter = new AtomicInteger(0);
        assignCoordinates(tree.getRoot(), g, 0, xCounter);
    }

    private static <T extends Comparable<T>> void buildGraph(NodeAVL<T> node, Graph g) {
        if (node == null) return;
        String id = node.data.toString();
        if (g.getNode(id) == null) {
            Node n = g.addNode(id);
            n.setAttribute("ui.label", id);
        }
        if (node.left != null) {
            String lid = node.left.data.toString();
            if (g.getNode(lid) == null) {
                Node l = g.addNode(lid);
                l.setAttribute("ui.label", lid);
            }
            g.addEdge(id + "-" + lid, id, lid);
            buildGraph(node.left, g);
        }
        if (node.right != null) {
            String rid = node.right.data.toString();
            if (g.getNode(rid) == null) {
                Node r = g.addNode(rid);
                r.setAttribute("ui.label", rid);
            }
            g.addEdge(id + "-" + rid, id, rid);
            buildGraph(node.right, g);
        }
    }

    private static <T extends Comparable<T>> void assignCoordinates(
            NodeAVL<T> node, Graph g, int depth, AtomicInteger xCounter) {
        if (node == null) return;
        assignCoordinates(node.left, g, depth + 1, xCounter);
        Node n = g.getNode(node.data.toString());
        double x = xCounter.getAndIncrement() * 2.5;  // separa un poco más
        double y = -depth * 2.5;                     // separación vertical
        n.setAttribute("xy", x, y);
        assignCoordinates(node.right, g, depth + 1, xCounter);
    }
}