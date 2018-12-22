package cn.acseed.commons.tools.tree;

import org.junit.Test;

public class RedBlackTreeTest {
    @Test
    public void rbInsertTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("A", 1);
        tree.put("B", 2);
        tree.put("C", 3);
        tree.put("D", 4);
        tree.put("E", 5);
        tree.put("F", 6);
        tree.put("G", 7);
        tree.put("H", 8);
        tree.put("I", 9);
        tree.put("J", 10);
        tree.put("K", 11);
        tree.put("L", 12);
    }
}
