import java.util.ArrayList;

public class BTreePlus<E extends Comparable<E>> {
    private BNodePlus<E> root;
    private int orden;
    private boolean up;
    private BNodePlus<E> nDes;
    
    public BTreePlus(int orden) {
        this.orden = orden;
        this.root = null;
        this.up = false;
        this.nDes = null;
    }
    
    public boolean isEmpty() {
        return this.root == null;
    }
    
    public void destroy() {
        this.root = null;
    }
    
    public void insert(E cl) {
        up = false;
        E mediana;
        BNodePlus<E> pnew;
        
        if (this.root == null) {
            this.root = new BNodePlus<E>(this.orden, true);
            this.root.keys.set(0, cl);
            this.root.count = 1;
            return;
        }
        
        mediana = pushPlus(this.root, cl);
        if (up) {
            pnew = new BNodePlus<E>(this.orden, false);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }
    
    private E pushPlus(BNodePlus<E> current, E cl) {
        int pos[] = new int[1];
        E mediana;
        
        if (current.isLeaf) {
            // Insert in leaf
            boolean found = current.searchNode(cl, pos);
            if (found) {
                System.out.println("Item duplicado: " + cl);
                up = false;
                return null;
            }
            
            if (current.nodeFull(this.orden - 1)) {
                mediana = dividedNodePlus(current, cl, pos[0]);
            } else {
                putNodePlus(current, cl, null, pos[0]);
                up = false;
                mediana = null;
            }
            return mediana;
        } else {
            // Internal node
            boolean found = current.searchNode(cl, pos);
            if (found) {
                // In B+ trees, continue to leaf even if found in internal node
                pos[0]++;
            }
            
            mediana = pushPlus(current.childs.get(pos[0]), cl);
            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    mediana = dividedNodePlus(current, mediana, pos[0]);
                } else {
                    putNodePlus(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }
            return mediana;
        }
    }
    
    private void putNodePlus(BNodePlus<E> current, E cl, BNodePlus<E> rd, int k) {
        int i;
        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            if (!current.isLeaf) {
                current.childs.set(i + 2, current.childs.get(i + 1));
            }
        }
        current.keys.set(k, cl);
        if (!current.isLeaf) {
            current.childs.set(k + 1, rd);
        }
        current.count++;
    }
    
    private E dividedNodePlus(BNodePlus<E> current, E cl, int k) {
        BNodePlus<E> rd = nDes;
        int i, posMdna;
        
        if (current.isLeaf) {
            posMdna = (this.orden + 1) / 2;
        } else {
            posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        }
        
        nDes = new BNodePlus<E>(this.orden, current.isLeaf);
        
        // Create temporary arrays to hold all keys including the new one
        ArrayList<E> tempKeys = new ArrayList<E>();
        ArrayList<BNodePlus<E>> tempChilds = new ArrayList<BNodePlus<E>>();
        
        // Copy existing keys and children
        int j = 0;
        for (i = 0; i < current.count; i++) {
            if (j == k) {
                tempKeys.add(cl);
                if (!current.isLeaf) {
                    tempChilds.add(rd);
                }
                j++;
            }
            tempKeys.add(current.keys.get(i));
            if (!current.isLeaf) {
                tempChilds.add(current.childs.get(i));
            }
            j++;
        }
        if (j == k) {
            tempKeys.add(cl);
            if (!current.isLeaf) {
                tempChilds.add(rd);
            }
        }
        if (!current.isLeaf) {
            tempChilds.add(current.childs.get(current.count));
        }
        
        // Split the keys
        current.count = posMdna;
        for (i = 0; i < posMdna; i++) {
            current.keys.set(i, tempKeys.get(i));
            if (!current.isLeaf) {
                current.childs.set(i, tempChilds.get(i));
            }
        }
        if (!current.isLeaf) {
            current.childs.set(posMdna, tempChilds.get(posMdna));
        }
        
        // Fill the new node
        int startIndex = current.isLeaf ? posMdna : posMdna + 1;
        nDes.count = tempKeys.size() - startIndex;
        for (i = 0; i < nDes.count; i++) {
            nDes.keys.set(i, tempKeys.get(startIndex + i));
            if (!current.isLeaf) {
                nDes.childs.set(i, tempChilds.get(startIndex + i));
            }
        }
        if (!current.isLeaf) {
            nDes.childs.set(nDes.count, tempChilds.get(tempChilds.size() - 1));
        }
        
        // Link leaves in B+ tree
        if (current.isLeaf) {
            nDes.next = current.next;
            current.next = nDes;
        }
        
        up = true;
        return current.isLeaf ? nDes.keys.get(0) : tempKeys.get(posMdna);
    }
    
    public boolean search(E key) {
        return searchRec(this.root, key);
    }
    
    private boolean searchRec(BNodePlus<E> current, E key) {
        if (current == null) {
            return false;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (current.isLeaf) {
            return found;
        } else {
            if (found) {
                pos[0]++;
            }
            return searchRec(current.childs.get(pos[0]), key);
        }
    }
    
    public void remove(E key) {
        if (this.root == null) {
            System.out.println("Árbol vacío");
            return;
        }
        
        boolean underflow = removeRec(this.root, key);
        
        if (underflow && this.root.count == 0 && !this.root.isLeaf) {
            this.root = this.root.childs.get(0);
        }
    }
    
    private boolean removeRec(BNodePlus<E> current, E key) {
        if (current == null) {
            return false;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (current.isLeaf) {
            if (found) {
                // Remove from leaf
                for (int i = pos[0]; i < current.count - 1; i++) {
                    current.keys.set(i, current.keys.get(i + 1));
                }
                current.count--;
                return current.count < (orden - 1) / 2;
            } else {
                return false;
            }
        } else {
            if (found) {
                pos[0]++;
            }
            boolean underflow = removeRec(current.childs.get(pos[0]), key);
            if (underflow) {
                return fixUnderflowPlus(current, pos[0]);
            }
            return false;
        }
    }
    
    private boolean fixUnderflowPlus(BNodePlus<E> parent, int childIndex) {
        BNodePlus<E> child = parent.childs.get(childIndex);
        int minKeys = (orden - 1) / 2;
        
        if (child.count >= minKeys) {
            return false;
        }
        
        // Try to borrow from left sibling
        if (childIndex > 0) {
            BNodePlus<E> leftSibling = parent.childs.get(childIndex - 1);
            if (leftSibling.count > minKeys) {
                borrowFromLeftPlus(parent, childIndex);
                return false;
            }
        }
        
        // Try to borrow from right sibling
        if (childIndex < parent.count) {
            BNodePlus<E> rightSibling = parent.childs.get(childIndex + 1);
            if (rightSibling.count > minKeys) {
                borrowFromRightPlus(parent, childIndex);
                return false;
            }
        }
        
        // Merge with sibling
        if (childIndex > 0) {
            fuzeNodePlus(parent, childIndex - 1);
        } else {
            fuzeNodePlus(parent, childIndex);
        }
        
        return parent.count < minKeys;
    }
    
    private void borrowFromLeftPlus(BNodePlus<E> parent, int childIndex) {
        BNodePlus<E> child = parent.childs.get(childIndex);
        BNodePlus<E> leftSibling = parent.childs.get(childIndex - 1);
        
        // Move elements in child to make room
        for (int i = child.count; i > 0; i--) {
            child.keys.set(i, child.keys.get(i - 1));
            if (!child.isLeaf) {
                child.childs.set(i + 1, child.childs.get(i));
            }
        }
        if (!child.isLeaf) {
            child.childs.set(1, child.childs.get(0));
        }
        
        if (child.isLeaf) {
            // For leaf nodes, move the last key from left sibling
            child.keys.set(0, leftSibling.keys.get(leftSibling.count - 1));
            parent.keys.set(childIndex - 1, leftSibling.keys.get(leftSibling.count - 1));
        } else {
            // For internal nodes, move parent key down and sibling key up
            child.keys.set(0, parent.keys.get(childIndex - 1));
            child.childs.set(0, leftSibling.childs.get(leftSibling.count));
            parent.keys.set(childIndex - 1, leftSibling.keys.get(leftSibling.count - 1));
        }
        
        child.count++;
        leftSibling.count--;
    }
    
    private void borrowFromRightPlus(BNodePlus<E> parent, int childIndex) {
        BNodePlus<E> child = parent.childs.get(childIndex);
        BNodePlus<E> rightSibling = parent.childs.get(childIndex + 1);
        
        if (child.isLeaf) {
            // For leaf nodes, move the first key from right sibling
            child.keys.set(child.count, rightSibling.keys.get(0));
            parent.keys.set(childIndex, rightSibling.keys.get(1));
        } else {
            // For internal nodes, move parent key down and sibling key up
            child.keys.set(child.count, parent.keys.get(childIndex));
            child.childs.set(child.count + 1, rightSibling.childs.get(0));
            parent.keys.set(childIndex, rightSibling.keys.get(0));
        }
        
        child.count++;
        
        // Shift right sibling's keys
        for (int i = 0; i < rightSibling.count - 1; i++) {
            rightSibling.keys.set(i, rightSibling.keys.get(i + 1));
            if (!rightSibling.isLeaf) {
                rightSibling.childs.set(i, rightSibling.childs.get(i + 1));
            }
        }
        if (!rightSibling.isLeaf) {
            rightSibling.childs.set(rightSibling.count - 1, rightSibling.childs.get(rightSibling.count));
        }
        rightSibling.count--;
    }
    
    private void fuzeNodePlus(BNodePlus<E> parent, int leftChildIndex) {
        BNodePlus<E> leftChild = parent.childs.get(leftChildIndex);
        BNodePlus<E> rightChild = parent.childs.get(leftChildIndex + 1);
        
        if (!leftChild.isLeaf) {
            // For internal nodes, include parent key
            leftChild.keys.set(leftChild.count, parent.keys.get(leftChildIndex));
            leftChild.count++;
        }
        
        // Move all keys and children from right child to left child
        for (int i = 0; i < rightChild.count; i++) {
            leftChild.keys.set(leftChild.count, rightChild.keys.get(i));
            if (!leftChild.isLeaf) {
                leftChild.childs.set(leftChild.count, rightChild.childs.get(i));
            }
            leftChild.count++;
        }
        if (!leftChild.isLeaf) {
            leftChild.childs.set(leftChild.count, rightChild.childs.get(rightChild.count));
        }
        
        // Update leaf linking
        if (leftChild.isLeaf) {
            leftChild.next = rightChild.next;
        }
        
        // Remove key from parent and shift
        for (int i = leftChildIndex; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        parent.count--;
    }
    
    public E min() {
        if (isEmpty()) {
            return null;
        }
        BNodePlus<E> current = this.root;
        while (!current.isLeaf) {
            current = current.childs.get(0);
        }
        return current.keys.get(0);
    }
    
    public E max() {
        if (isEmpty()) {
            return null;
        }
        BNodePlus<E> current = this.root;
        while (!current.isLeaf) {
            current = current.childs.get(current.count);
        }
        return current.keys.get(current.count - 1);
    }
    
    public E predecessor(E key) {
        return predecessorRec(this.root, key, null);
    }
    
    private E predecessorRec(BNodePlus<E> current, E key, E pred) {
        if (current == null) {
            return pred;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (current.isLeaf) {
            if (found && pos[0] > 0) {
                return current.keys.get(pos[0] - 1);
            } else {
                return pred;
            }
        } else {
            if (found) {
                pos[0]++;
            }
            if (pos[0] > 0) {
                pred = current.keys.get(pos[0] - 1);
            }
            return predecessorRec(current.childs.get(pos[0]), key, pred);
        }
    }
    
    public E successor(E key) {
        return successorRec(this.root, key, null);
    }
    
    private E successorRec(BNodePlus<E> current, E key, E succ) {
        if (current == null) {
            return succ;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (current.isLeaf) {
            if (found && pos[0] < current.count - 1) {
                return current.keys.get(pos[0] + 1);
            } else {
                return succ;
            }
        } else {
            if (found) {
                pos[0]++;
            }
            if (pos[0] < current.count) {
                succ = current.keys.get(pos[0]);
            }
            return successorRec(current.childs.get(pos[0]), key, succ);
        }
    }
    
    public String toString() {
        String s = "";
        if (isEmpty()) {
            s += "B+ Tree is empty...";
        } else {
            s = writeTree(this.root, 0);
            s += "\nLeaf sequence: " + getLeafSequence();
        }
        return s;
    }
    
    private String writeTree(BNodePlus<E> current, int level) {
        StringBuilder sb = new StringBuilder();
        if (current != null) {
            // Indentation for current level
            for (int i = 0; i < level; i++) {
                sb.append("  ");
            }
            sb.append("Level ").append(level).append(": ");
            sb.append(current.toString()).append("\n");
            
            // Recursively print children
            if (!current.isLeaf) {
                for (int i = 0; i <= current.count; i++) {
                    if (current.childs.get(i) != null) {
                        sb.append(writeTree(current.childs.get(i), level + 1));
                    }
                }
            }
        }
        return sb.toString();
    }
    
    private String getLeafSequence() {
        StringBuilder sb = new StringBuilder();
        BNodePlus<E> current = this.root;
        
        if (current == null) {
            return "Empty";
        }
        
        // Find leftmost leaf
        while (!current.isLeaf) {
            current = current.childs.get(0);
        }
        
        // Traverse all leaves
        while (current != null) {
            for (int i = 0; i < current.count; i++) {
                sb.append(current.keys.get(i));
                if (i < current.count - 1 || current.next != null) {
                    sb.append(" -> ");
                }
            }
            current = current.next;
        }
        
        return sb.toString();
    }
    
    public void printStepByStep(String operation, E key) {
        System.out.println("\n" + operation + " " + key + ":");
        System.out.println(this.toString());
        System.out.println("------------------------");
    }
}
