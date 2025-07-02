// File: AVLDemo.java
public class AVLDemo {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        int[] insertValues = {100,200,300,400,500,50,25,350,375,360,355,150,175,120,190};

        System.out.println("=== Inserción de nodos ===");
        for (int x : insertValues) {
            tree.insert(x);
            System.out.printf("Insert %3d: rotaciones=%s%n", x, tree.getLastRotations());
        }

        System.out.println("\nRecorridos tras inserciones:");
        System.out.println("InOrder:   " + tree.inOrder());
        System.out.println("PreOrder:  " + tree.preOrder());
        System.out.println("PostOrder: " + tree.postOrder());

        System.out.println("\n=== Eliminación de nodos ===");
        for (int x : insertValues) {
            tree.remove(x);
            System.out.printf("Remove %3d: rotaciones=%s -> InOrder=%s%n",
                              x, tree.getLastRotations(), tree.inOrder());
        }
    }
}

