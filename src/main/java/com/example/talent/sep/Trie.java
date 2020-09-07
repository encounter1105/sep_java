package com.example.talent.sep;

import java.util.HashMap;
import java.util.Map;

/**
 * 从后往前逐字符存储字符串
 * @author 林高琦
 */
public class Trie {

    private Node dic;

    public Trie() {
        dic = new Node();
    }

    public void put(String s) {
        char[] chars = s.toCharArray();
        Node node = dic;
        for (int i = chars.length - 1;i >= 0;--i) {
            node = node.putChild(chars[i]);
        }
    }

    public boolean contain(String s) {
        char[] chars = s.toCharArray();
        Node node = dic;
        for (int i = chars.length - 1;i >= 0;--i) {
            node = node.getChild(chars[i]);
            if ( node == null) {
                return false;
            }
        }
        return true;
    }

    public boolean contain(RingBuffer ring) {
        if (!dic.hasChild()) {
            return false;
        }

        Node node = dic;
        for (int i = ring.size() - 1;i >= 0;--i) {
            node = node.getChild(ring.get(i));
            if ( node == null) {
                return false;
            }
            if (!node.hasChild()) {
                return true;
            }
        }
        return true;
    }

    private class Node {
        private char val;
        private Map<Character, Node> children;

        public Node() {
            children = new HashMap<>();
        }

        public Node(char val) {
            this.val = val;
            children = new HashMap<>();
        }

        public Node getChild(Character c) {
            return children.get(c);
        }

        public Node putChild(Character c) {
            Node node = children.get(c);
            if (node == null) {
                node = new Node(c);
                children.put(c, node);
            }
            return node;
        }

        public boolean hasChild() {
            return !children.isEmpty();
        }
    }
}
