// File: NodeAVL.java
public class NodeAVL<T extends Comparable<T>> {
    T data;
    NodeAVL<T> left, right;
    int height;

    public NodeAVL(T data) {
        this.data = data;
        this.height = 1;
    }

    public int getBalance() {
        int lh = (left != null) ? left.height : 0;
        int rh = (right != null) ? right.height : 0;
        return lh - rh;
    }
}
