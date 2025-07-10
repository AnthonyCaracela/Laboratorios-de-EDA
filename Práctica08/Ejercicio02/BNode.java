import java.util.ArrayList;

public class BNode<E> {
    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;
    
    public BNode(int n) {
        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n + 1);
        this.count = 0;
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }
        for (int i = 0; i <= n; i++) {
            this.childs.add(null);
        }
    }
    
    // Check if the current node is full
    public boolean nodeFull(int maxKeys) {
        return this.count >= maxKeys;
    }
    
    // Check if the current node is empty
    public boolean nodeEmpty() {
        return this.count == 0;
    }
    
    // Search for a key in the current node
    @SuppressWarnings("unchecked")
    public boolean searchNode(E key, int[] pos) {
        int i = 0;
        while (i < this.count && ((Comparable<E>) key).compareTo(this.keys.get(i)) > 0) {
            i++;
        }
        pos[0] = i;
        return (i < this.count && ((Comparable<E>) key).compareTo(this.keys.get(i)) == 0);
    }
    
    // Check if node is a leaf
    public boolean isLeaf() {
        for (int i = 0; i <= this.count; i++) {
            if (this.childs.get(i) != null) {
                return false;
            }
        }
        return true;
    }
    
    // Return the keys found in the node
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.count; i++) {
            sb.append(this.keys.get(i));
            if (i < this.count - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
