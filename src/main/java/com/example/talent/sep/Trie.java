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

    /**
     * 放入字符串时，短字符串覆盖掉长字符串
     * @param s
     */
    public void put(String s) {
        if (s == null || s.isEmpty()) {
            return;
        }
        char[] chars = s.toCharArray();
        Node node = dic;
        boolean isOldNode;
        //先放入最后一个字符
        isOldNode = node.hasChild(chars[chars.length - 1]);
        node = node.putChild(chars[chars.length - 1]);
        for (int i = chars.length - 2;i >= 0;--i) {
            if (isOldNode && !node.hasChild()) {
                //此时字典中存储的是短字符串，不再继续存储
                return;
            }
            isOldNode = node.hasChild(chars[i]);
            node = node.putChild(chars[i]);
        }
        if (node.hasChild()) {
            //此时字典中存储的是长字符串，截断它
            node.removeChildren();
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
        if (node.hasChild()) {
            return false;
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

        public boolean hasChild(Character c) {
            return children.containsKey(c);
        }

        public void removeChildren() {
            children.clear();
        }
    }
}
