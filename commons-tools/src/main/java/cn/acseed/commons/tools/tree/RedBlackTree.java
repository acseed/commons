package cn.acseed.commons.tools.tree;

import lombok.NoArgsConstructor;

/**
 * an implementation of the Red Black Tree
 * 红黑树满足性质:
 * (1)每个结点要么红色，要么黑色。
 * (2)根结点是黑色。
 * (3)每个叶子节点（NIL）是黑色。
 * (4)如果一个节点是红色的，则它的两个子结点都是黑色的。
 * (5)对每个节点，从该结点到其所有后代叶子结点的简单路径上，均包含相同数目的黑色结点。
 * Created with hongchen.cao
 * Date: 2018-12-22 12:24
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * common nil node
     * represents the parent of root, or the leaf nodes
     */
    private final Node<K, V> NIL = new Node<>(null, null, BLACK);

    /**
     * the root of the tree
     */
    private Node<K, V> root;

    RedBlackTree() {
        this.root = NIL;
    }

    /**
     * left rotate the node x
     */
    private void leftRotate(Node<K, V> x) {
        Node<K, V> y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (NIL == x.parent) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    /**
     * right rotate the node x
     */
    private void rightRotate(Node<K, V> x) {
        Node<K, V> y = x.left;
        x.left = y.right;
        if (NIL != y.right) {
            y.right.parent = x;
        }

        y.parent = x.parent;
        if (NIL == x.parent) {
            this.root = y;
        } else if (x.parent.left == x) {
            x.parent.left = y;
        } else if (x.parent.right == x) {
            x.parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }

    public void put(K k, V v) {
        Node<K, V> node = new Node<>(k, v, RED);
        insert(node);
    }

    /**
     * insert a new node to the tree
     * @param node
     */
    private void insert(Node<K, V> node) {
        Node<K, V> y = NIL;
        Node<K, V> x = this.root;
        while (NIL != x) {
            y = x;
            if (node.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (NIL == y) {
            this.root = node;
        } else if (node.key.compareTo(y.key) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        node.left = NIL;
        node.right = NIL;
        node.color = RED;
        insertFixUp(node);
    }

    private void insertFixUp(Node<K, V> x) {
        while (x.parent.color == RED) {
            if (x.parent == x.parent.parent.left) {
                Node<K, V> y = x.parent.parent.right;
                if (RED == y.color) {
                    y.color = BLACK;
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.right) {
                        x = x.parent;
                        leftRotate(x);
                    }

                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    rightRotate(x.parent.parent);
                }
            } else {
                Node<K, V> y = x.parent.parent.left;
                if (RED == y.color) {
                    y.color = BLACK;
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.left) {
                        x = x.parent;
                        rightRotate(x);
                    }
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    leftRotate(x.parent.parent);
                }
            }
        }
        this.root.color = BLACK;
    }

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

        Node(K key, V val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }
}
