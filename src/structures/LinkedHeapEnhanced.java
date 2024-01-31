package structures;

import collections.trees.BinaryTreeNode;
import collections.trees.heaps.LinkedHeap;

public class LinkedHeapEnhanced<T> extends LinkedHeap<T> {

    private BinaryTreeNode<T> lastNode;

    public LinkedHeapEnhanced() {
        super();
    }
}
