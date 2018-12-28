package cn.acseed.commons.tools.tree;

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

    public V get(K k) {
        Node<K, V> v = find(k);
        return NIL == v ? null : v.val;
    }

    public void remove(K k) {
        Node<K, V> node = find(k);
        if (NIL == node) {
            return;
        }
        delete(node);
    }

    private Node<K, V> find(K k) {
        Node<K, V> y = this.root;
        while (NIL != y) {
            if (y.key.compareTo(k) < 0) {
                y = y.right;
            } else if (y.key.compareTo(k) > 0) {
                y = y.left;
            } else {
                return y;
            }
        }
        return NIL;
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
     * let node v replace node u
     * sub procedure of the delete process
     */
    private void transplant(Node<K, V> u, Node<K, V> v) {
        if (NIL == u.parent) {
            this.root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }


    /**
     * delete process of the rbTree
     */
    private void delete(Node<K, V> k) {
        Node<K, V> x;
        Node<K, V> y = k;
        boolean yOriginColor = y.color;
        if (NIL == k.left) {
            x = k.right;
            transplant(k, x);
        } else if (NIL == k.right) {
            x = k.left;
            transplant(k, x);
        } else {
            y = minimumKeyNode(k.right);
            yOriginColor = y.color;
            x = y.right;
            if (y.parent == k) {
                x.parent = y;
            } else {
                transplant(y, x);
                y.right = k.right;
                y.right.parent = y;
            }
            transplant(k, y);
            y.left = k.left;
            y.left.parent = y;
            y.color = k.color;
        }

        if (BLACK == yOriginColor) {
            deleteFixUp(x);
        }
    }

    /**
     * the minimum Key node of the subtree
     */
    private Node<K, V> minimumKeyNode(Node<K, V> node) {
        Node<K, V> ret = node;
        while (NIL != node) {
            ret = node;
            node = node.left;
        }
        return ret;
    }

    /**
     * like the insert process,the fixup procedure
     * (1)如果y是原来的根结点，而y的一个红色孩子成为新的根节点，违反性质2
     * (2)如果x和x.p都是红色，违反性质4
     * (3)在树中移动y导致任何包含y的简单路径的黑色节点减少1
     * 将现在占有y原来位置的x视为还有一重黑色
     * 将额外的黑色上移，直到:
     * (1)x指向红黑结点，此时只需简单把x置为黑色
     * (2)x指向根结点，可以简单置为黑色
     * (3)执行适当的旋转和着色，恢复性质
     *
     * while中x总是指向一个具有双重黑色的非根结点
     */
    private void deleteFixUp(Node<K, V> x) {
        Node<K, V> w; // the bro of x
        while (this.root != x && x.color == BLACK) {
            if (x == x.parent.left) {
                w = x.parent.right; // the brother must not be null, because of the tbtree rule
                //case 1 the bro is RED
                if (RED == w.color) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent); // to case 2
                    w = x.parent.right;
                }
                if (BLACK == w.left.color && BLACK == w.right.color) { // case 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (BLACK == w.right.color) { //case 3
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = this.root;
                }
            } else {
                w = x.parent.left;
                if (RED == w.color) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (BLACK == w.left.color && BLACK == w.right.color) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (BLACK == w.left.color) {
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = this.root;
                }
            }
        }
        x.color = BLACK;
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
