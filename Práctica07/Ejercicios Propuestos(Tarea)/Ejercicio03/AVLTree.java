// File: AVLTree.java
import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    private NodeAVL<T> root;
    private List<String> lastRotations = new ArrayList<>();

    public String getLastRotations() {
        String s = lastRotations.toString();
        lastRotations.clear();
        return s;
    }

    public void insert(T key) {
        root = insert(root, key);
    }

    private NodeAVL<T> insert(NodeAVL<T> node, T key) {
        if (node == null) return new NodeAVL<>(key);
        int cmp = key.compareTo(node.data);
        if (cmp < 0) node.left = insert(node.left, key);
        else if (cmp > 0) node.right = insert(node.right, key);
        else return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = node.getBalance();
        if (balance > 1) {
            if (key.compareTo(node.left.data) < 0) {
                lastRotations.add("R_SR");
                return rotateRight(node);
            } else {
                lastRotations.add("R_LR");
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balance < -1) {
            if (key.compareTo(node.right.data) > 0) {
                lastRotations.add("R_SL");
                return rotateLeft(node);
            } else {
                lastRotations.add("R_RL");
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }

    public void remove(T key) {
        root = remove(root, key);
    }

    private NodeAVL<T> remove(NodeAVL<T> node, T key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.data);
        if (cmp < 0) node.left = remove(node.left, key);
        else if (cmp > 0) node.right = remove(node.right, key);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                NodeAVL<T> temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }
        if (node == null) return null;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = node.getBalance();
        if (balance > 1) {
            if (node.left.getBalance() >= 0) {
                lastRotations.add("R_SR");
                return rotateRight(node);
            } else {
                lastRotations.add("R_LR");
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balance < -1) {
            if (node.right.getBalance() <= 0) {
                lastRotations.add("R_SL");
                return rotateLeft(node);
            } else {
                lastRotations.add("R_RL");
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }

    private int height(NodeAVL<T> n) {
        return (n != null) ? n.height : 0;
    }

    private NodeAVL<T> rotateRight(NodeAVL<T> y) {
        NodeAVL<T> x = y.left;
        NodeAVL<T> T2 = x.right;
        x.right = y; y.left = T2;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    private NodeAVL<T> rotateLeft(NodeAVL<T> x) {
        NodeAVL<T> y = x.right;
        NodeAVL<T> T2 = y.left;
        y.left = x; x.right = T2;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private NodeAVL<T> minValueNode(NodeAVL<T> node) {
        NodeAVL<T> current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    private NodeAVL<T> maxValueNode(NodeAVL<T> node) {
        NodeAVL<T> current = node;
        while (current.right != null) current = current.right;
        return current;
    }

    public boolean search(T key) {
        return search(root, key);
    }

    private boolean search(NodeAVL<T> node, T key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.data);
        if (cmp == 0) return true;
        return (cmp < 0) ? search(node.left, key) : search(node.right, key);
    }

    public T min() {
        return (root == null) ? null : minValueNode(root).data;
    }

    public T max() {
        return (root == null) ? null : maxValueNode(root).data;
    }

    public T predecessor(T key) {
        NodeAVL<T> node = findNode(root, key);
        if (node == null) return null;
        if (node.left != null) return maxValueNode(node.left).data;
        NodeAVL<T> pred = null, anc = root;
        while (anc != null) {
            int cmp = key.compareTo(anc.data);
            if (cmp > 0) { pred = anc; anc = anc.right; }
            else if (cmp < 0) anc = anc.left;
            else break;
        }
        return (pred != null) ? pred.data : null;
    }

    public T successor(T key) {
        NodeAVL<T> node = findNode(root, key);
        if (node == null) return null;
        if (node.right != null) return minValueNode(node.right).data;
        NodeAVL<T> succ = null, anc = root;
        while (anc != null) {
            int cmp = key.compareTo(anc.data);
            if (cmp < 0) { succ = anc; anc = anc.left; }
            else if (cmp > 0) anc = anc.right;
            else break;
        }
        return (succ != null) ? succ.data : null;
    }

    private NodeAVL<T> findNode(NodeAVL<T> node, T key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.data);
        if (cmp == 0) return node;
        return (cmp < 0) ? findNode(node.left, key) : findNode(node.right, key);
    }

    /** BUSCADOR PÚBLICO **/
    public NodeAVL<T> findNode(T key) {
        return findNode(root, key);
    }

    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }
    private void inOrder(NodeAVL<T> node, List<T> list) {
        if (node != null) {
            inOrder(node.left, list);
            list.add(node.data);
            inOrder(node.right, list);
        }
    }

    public List<T> preOrder() {
        List<T> list = new ArrayList<>();
        preOrder(root, list);
        return list;
    }
    private void preOrder(NodeAVL<T> node, List<T> list) {
        if (node != null) {
            list.add(node.data);
            preOrder(node.left, list);
            preOrder(node.right, list);
        }
    }

    public List<T> postOrder() {
        List<T> list = new ArrayList<>();
        postOrder(root, list);
        return list;
    }
    private void postOrder(NodeAVL<T> node, List<T> list) {
        if (node != null) {
            postOrder(node.left, list);
            postOrder(node.right, list);
            list.add(node.data);
        }
    }

    // Métodos privados de balanceo para usos internos o forcing:
    private NodeAVL<T> balanceLeft(NodeAVL<T> node) {
        if (node == null) return null;
        int balance = node.getBalance();
        if (balance > 1) {
            if (node.left.getBalance() >= 0) return rotateRight(node);
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        return node;
    }
    private NodeAVL<T> balanceRight(NodeAVL<T> node) {
        if (node == null) return null;
        int balance = node.getBalance();
        if (balance < -1) {
            if (node.right.getBalance() <= 0) return rotateLeft(node);
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    // Métodos públicos extra:
    public void destruir() { root = null; }
    public boolean estaVacio() { return root == null; }
    public void balancearIzquierda() { root = balanceLeft(root); }
    public void balancearDerecha() { root = balanceRight(root); }
    public NodeAVL<T> rotacionSimpleIzquierda(NodeAVL<T> node) { return rotateLeft(node); }
    public NodeAVL<T> rotacionSimpleDerecha(NodeAVL<T> node) { return rotateRight(node); }
    public void setRoot(NodeAVL<T> node) { root = node; }
    public NodeAVL<T> getRoot() { return root; }
}

