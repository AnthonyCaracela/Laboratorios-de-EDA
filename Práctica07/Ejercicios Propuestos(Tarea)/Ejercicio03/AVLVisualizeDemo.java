// File: AVLVisualizeDemo.java
public class AVLVisualizeDemo {
    public static void main(String[] args) {
        // Crea un árbol e inserta unos valores de prueba
        AVLTree<Integer> tree = new AVLTree<>();
        int[] values = {100, 200, 300, 400, 250, 150};
        for (int v : values) {
            tree.insert(v);
        }

        // Llama al visualizador
        AVLVisualizer.visualize(tree);
    }
}