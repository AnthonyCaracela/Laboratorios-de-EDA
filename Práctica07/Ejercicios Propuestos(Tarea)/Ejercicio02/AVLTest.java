// File: AVLTest.java
import java.util.List;
import java.util.Scanner;
public class AVLTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTree<Integer> tree = new AVLTree<>();

        while (true) {
            System.out.println("\n===== MENÚ COMPLETO DE OPERACIONES AVL =====");
            System.out.println("1) destruir()      2) estaVacio()   3) insertar(x)   4) eliminar(x)");
            System.out.println("5) buscar(x)       6) min()         7) max()         8) predecesor(x)");
            System.out.println("9) sucesor(x)     10) inOrder()     11) preOrder()    12) postOrder()");
            System.out.println("13) balancearIzquierda()   14) balancearDerecha()");
            System.out.println("15) rotacionSimpleIzquierda()   16) rotacionSimpleDerecha()   17) salir");
            System.out.print("Elige opción: ");

            int op = sc.nextInt();
            Integer x;
            List<Integer> list;

            switch (op) {
                case 1:
                    tree.destruir();
                    System.out.println("Árbol destruido.");
                    break;
                case 2:
                    System.out.println(tree.estaVacio()
                        ? "Árbol vacío." : "Árbol no está vacío.");
                    break;
                case 3:
                    System.out.print("Valor a insertar: "); x = sc.nextInt();
                    tree.insert(x);
                    System.out.println("Insertado " + x + " (rotaciones: "
                        + tree.getLastRotations() + ")");
                    break;
                case 4:
                    System.out.print("Valor a eliminar: "); x = sc.nextInt();
                    tree.remove(x);
                    System.out.println("Eliminado " + x + " (rotaciones: "
                        + tree.getLastRotations() + ")");
                    break;
                case 5:
                    System.out.print("Valor a buscar: "); x = sc.nextInt();
                    System.out.println(tree.search(x)
                        ? "Encontrado." : "No encontrado.");
                    break;
                case 6:
                    System.out.println("Mínimo: " + tree.min());
                    break;
                case 7:
                    System.out.println("Máximo: " + tree.max());
                    break;
                case 8:
                    System.out.print("Predecesor de: "); x = sc.nextInt();
                    System.out.println("Predecesor: " + tree.predecessor(x));
                    break;
                case 9:
                    System.out.print("Sucesor de: "); x = sc.nextInt();
                    System.out.println("Sucesor: " + tree.successor(x));
                    break;
                case 10:
                    list = tree.inOrder();
                    System.out.println("inOrder: " + list);
                    break;
                case 11:
                    list = tree.preOrder();
                    System.out.println("preOrder: " + list);
                    break;
                case 12:
                    list = tree.postOrder();
                    System.out.println("postOrder: " + list);
                    break;
                case 13:
                    tree.balancearIzquierda();
                    System.out.println("balancearIzquierda aplicado. inOrder: "
                        + tree.inOrder());
                    break;
                case 14:
                    tree.balancearDerecha();
                    System.out.println("balancearDerecha aplicado. inOrder: "
                        + tree.inOrder());
                    break;
                case 15:
                    System.out.print("Clave nodo para rotar izq: "); x = sc.nextInt();
                    NodeAVL<Integer> ni = tree.findNode(x);
                    if (ni != null) {
                        tree.setRoot(tree.rotacionSimpleIzquierda(ni));
                        System.out.println("rotacionSimpleIzquierda aplicada. inOrder: "
                            + tree.inOrder());
                    } else System.out.println("Nodo no encontrado.");
                    break;
                case 16:
                    System.out.print("Clave nodo para rotar der: "); x = sc.nextInt();
                    NodeAVL<Integer> nd = tree.findNode(x);
                    if (nd != null) {
                        tree.setRoot(tree.rotacionSimpleDerecha(nd));
                        System.out.println("rotacionSimpleDerecha aplicada. inOrder: "
                            + tree.inOrder());
                    } else System.out.println("Nodo no encontrado.");
                    break;
                case 17:
                    System.out.println("Saliendo..."); sc.close(); System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}