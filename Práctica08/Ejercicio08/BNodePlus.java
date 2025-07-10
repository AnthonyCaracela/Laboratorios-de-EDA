import java.util.ArrayList;

public class BNodePlus<E> {
    protected ArrayList<E> keys;
    protected ArrayList<BNodePlus<E>> childs;
    protected BNodePlus<E> next; // For leaf linking in B+ trees
    protected int count;
    protected boolean isLeaf;
    
    public BNodePlus(int n, boolean isLeaf) {
        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNodePlus<E>>(n + 1);
        this.count = 0;
        this.isLeaf = isLeaf;
        this.next = null;
        
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }
        for (int i = 0; i <= n; i++) {
            this.childs.add(null);
        }
    }
    
    public boolean nodeFull(int maxKeys) {
        return this.count >= maxKeys;
    }
    
    public boolean nodeEmpty() {
        return this.count == 0;
    }
    
    @SuppressWarnings("unchecked")
    public boolean searchNode(E key, int[] pos) {
        int i = 0;
        while (i < this.count && ((Comparable<E>) key).compareTo(this.keys.get(i)) > 0) {
            i++;
        }
        pos[0] = i;
        return (i < this.count && ((Comparable<E>) key).compareTo(this.keys.get(i)) == 0);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isLeaf ? "[LEAF: " : "[INTERNAL: ");
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
