package cn.acseed.commons.tools.tree;

import lombok.NoArgsConstructor;

/**
 * an implementation of the Red Black Tree
 * 红黑树满足的性质:
 * (1)每个结点要么红色，要么黑色。
 * (2)根结点是黑色。
 * (3)每个叶子节点（NIL）是黑色。
 * (4)如果一个节点是红色的，则它的两个子结点都是黑色的。
 * (5)对每个节点，从该结点到其所有后代叶子结点的简单路径上，均包含相同数目的黑色结点。
 * Created with hongchen.cao
 * Date: 2018-12-22 12:24
 */
@NoArgsConstructor
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * common nil node
     * represents the parent of root, or the leaf nodes
     */
    private final Node<K, V> NIL = new Node<>(null, null, 0, BLACK);

    /**
     * the root of the tree
     */
    private Node<K, V> root;

    /**
     * node of RBTree
     */
    static class Node<K, V> {
        private K key;
        private V val;
        private Node<K, V> left;
        private Node<K, V> right;
        private Node<K, V> parent;
        private boolean color;
        private int size;

        Node(K key, V val, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
        }
    }


}
