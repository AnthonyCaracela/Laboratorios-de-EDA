import java.util.ArrayList;

public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;
    
    public BTree(int orden) {
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
        BNode<E> pnew;
        mediana = push(this.root, cl);
        if (up) {
            pnew = new BNode<E>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }
    
    private E push(BNode<E> current, E cl) {
        int pos[] = new int[1];
        E mediana;
        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        } else {
            boolean fl;
            fl = current.searchNode(cl, pos);
            if (fl) {
                System.out.println("Item duplicado: " + cl);
                up = false;
                return null;
            }
            mediana = push(current.childs.get(pos[0]), cl);
            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }
            return mediana;
        }
    }
    
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        int i;
        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }
    
    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int i, posMdna;
        posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        nDes = new BNode<E>(this.orden);
        
        for (i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }
        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;
        
        if (k <= this.orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }
        
        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }
    
    public boolean search(E key) {
        return searchRec(this.root, key);
    }
    
    private boolean searchRec(BNode<E> current, E key) {
        if (current == null) {
            return false;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (found) {
            return true;
        } else {
            return searchRec(current.childs.get(pos[0]), key);
        }
    }
    
    public void remove(E key) {
        if (this.root == null) {
            System.out.println("Árbol vacío");
            return;
        }
        
        boolean underflow = removeRec(this.root, key);
        
        if (underflow && this.root.count == 0) {
            this.root = this.root.childs.get(0);
        }
    }
    
    private boolean removeRec(BNode<E> current, E key) {
        if (current == null) {
            return false;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (found) {
            if (current.isLeaf()) {
                // Remove from leaf
                for (int i = pos[0]; i < current.count - 1; i++) {
                    current.keys.set(i, current.keys.get(i + 1));
                }
                current.count--;
                return current.count < (orden - 1) / 2;
            } else {
                // Replace with successor and remove successor
                E successor = getSuccessor(current, pos[0]);
                current.keys.set(pos[0], successor);
                return removeRec(current.childs.get(pos[0] + 1), successor);
            }
        } else {
            boolean underflow = removeRec(current.childs.get(pos[0]), key);
            if (underflow) {
                return fixUnderflow(current, pos[0]);
            }
            return false;
        }
    }
    
    private E getSuccessor(BNode<E> current, int keyIndex) {
        BNode<E> node = current.childs.get(keyIndex + 1);
        while (!node.isLeaf()) {
            node = node.childs.get(0);
        }
        return node.keys.get(0);
    }
    
    private boolean fixUnderflow(BNode<E> parent, int childIndex) {
        BNode<E> child = parent.childs.get(childIndex);
        int minKeys = (orden - 1) / 2;
        
        if (child.count >= minKeys) {
            return false;
        }
        
        // Try to borrow from left sibling
        if (childIndex > 0) {
            BNode<E> leftSibling = parent.childs.get(childIndex - 1);
            if (leftSibling.count > minKeys) {
                borrowFromLeft(parent, childIndex);
                return false;
            }
        }
        
        // Try to borrow from right sibling
        if (childIndex < parent.count) {
            BNode<E> rightSibling = parent.childs.get(childIndex + 1);
            if (rightSibling.count > minKeys) {
                borrowFromRight(parent, childIndex);
                return false;
            }
        }
        
        // Merge with sibling
        if (childIndex > 0) {
            fuzeNode(parent, childIndex - 1);
        } else {
            fuzeNode(parent, childIndex);
        }
        
        return parent.count < minKeys;
    }
    
    private void borrowFromLeft(BNode<E> parent, int childIndex) {
        BNode<E> child = parent.childs.get(childIndex);
        BNode<E> leftSibling = parent.childs.get(childIndex - 1);
        
        // Move parent key to child
        for (int i = child.count; i > 0; i--) {
            child.keys.set(i, child.keys.get(i - 1));
            child.childs.set(i + 1, child.childs.get(i));
        }
        child.childs.set(1, child.childs.get(0));
        
        child.keys.set(0, parent.keys.get(childIndex - 1));
        child.childs.set(0, leftSibling.childs.get(leftSibling.count));
        child.count++;
        
        // Move left sibling's last key to parent
        parent.keys.set(childIndex - 1, leftSibling.keys.get(leftSibling.count - 1));
        leftSibling.count--;
    }
    
    private void borrowFromRight(BNode<E> parent, int childIndex) {
        BNode<E> child = parent.childs.get(childIndex);
        BNode<E> rightSibling = parent.childs.get(childIndex + 1);
        
        // Move parent key to child
        child.keys.set(child.count, parent.keys.get(childIndex));
        child.childs.set(child.count + 1, rightSibling.childs.get(0));
        child.count++;
        
        // Move right sibling's first key to parent
        parent.keys.set(childIndex, rightSibling.keys.get(0));
        
        // Shift right sibling's keys
        for (int i = 0; i < rightSibling.count - 1; i++) {
            rightSibling.keys.set(i, rightSibling.keys.get(i + 1));
            rightSibling.childs.set(i, rightSibling.childs.get(i + 1));
        }
        rightSibling.childs.set(rightSibling.count - 1, rightSibling.childs.get(rightSibling.count));
        rightSibling.count--;
    }
    
    private void fuzeNode(BNode<E> parent, int leftChildIndex) {
        BNode<E> leftChild = parent.childs.get(leftChildIndex);
        BNode<E> rightChild = parent.childs.get(leftChildIndex + 1);
        
        // Move parent key to left child
        leftChild.keys.set(leftChild.count, parent.keys.get(leftChildIndex));
        leftChild.count++;
        
        // Move all keys and children from right child to left child
        for (int i = 0; i < rightChild.count; i++) {
            leftChild.keys.set(leftChild.count, rightChild.keys.get(i));
            leftChild.childs.set(leftChild.count, rightChild.childs.get(i));
            leftChild.count++;
        }
        leftChild.childs.set(leftChild.count, rightChild.childs.get(rightChild.count));
        
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
        BNode<E> current = this.root;
        while (!current.isLeaf()) {
            current = current.childs.get(0);
        }
        return current.keys.get(0);
    }
    
    public E max() {
        if (isEmpty()) {
            return null;
        }
        BNode<E> current = this.root;
        while (!current.isLeaf()) {
            current = current.childs.get(current.count);
        }
        return current.keys.get(current.count - 1);
    }
    
    public E predecessor(E key) {
        return predecessorRec(this.root, key, null);
    }
    
    private E predecessorRec(BNode<E> current, E key, E pred) {
        if (current == null) {
            return pred;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (found) {
            if (!current.isLeaf()) {
                BNode<E> leftChild = current.childs.get(pos[0]);
                while (!leftChild.isLeaf()) {
                    leftChild = leftChild.childs.get(leftChild.count);
                }
                return leftChild.keys.get(leftChild.count - 1);
            } else {
                return pred;
            }
        } else {
            if (pos[0] > 0) {
                pred = current.keys.get(pos[0] - 1);
            }
            return predecessorRec(current.childs.get(pos[0]), key, pred);
        }
    }
    
    public E successor(E key) {
        return successorRec(this.root, key, null);
    }
    
    private E successorRec(BNode<E> current, E key, E succ) {
        if (current == null) {
            return succ;
        }
        
        int[] pos = new int[1];
        boolean found = current.searchNode(key, pos);
        
        if (found) {
            if (!current.isLeaf()) {
                BNode<E> rightChild = current.childs.get(pos[0] + 1);
                while (!rightChild.isLeaf()) {
                    rightChild = rightChild.childs.get(0);
                }
                return rightChild.keys.get(0);
            } else {
                return succ;
            }
        } else {
            if (pos[0] < current.count) {
                succ = current.keys.get(pos[0]);
            }
            return successorRec(current.childs.get(pos[0]), key, succ);
        }
    }
    
    public String toString() {
        String s = "";
        if (isEmpty()) {
            s += "BTree is empty...";
        } else {
            s = writeTree(this.root, 0);
        }
        return s;
    }
    
    private String writeTree(BNode<E> current, int level) {
        StringBuilder sb = new StringBuilder();
        if (current != null) {
            // Indentation for current level
            for (int i = 0; i < level; i++) {
                sb.append("  ");
            }
            sb.append("Level ").append(level).append(": ");
            sb.append(current.toString()).append("\n");
            
            // Recursively print children
            for (int i = 0; i <= current.count; i++) {
                if (current.childs.get(i) != null) {
                    sb.append(writeTree(current.childs.get(i), level + 1));
                }
            }
        }
        return sb.toString();
    }
    
    public void printStepByStep(String operation, E key) {
        System.out.println("\n" + operation + " " + key + ":");
        System.out.println(this.toString());
        System.out.println("------------------------");
    }
}
