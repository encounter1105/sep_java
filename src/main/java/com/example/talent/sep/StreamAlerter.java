package com.example.talent.sep;

/**
 * @author 林高琦
 */
public class StreamAlerter {
    private RingBuffer ring;
    private Trie trie;

    public StreamAlerter(String[] strings) {
        ring = new RingBuffer(1024);
        trie = new Trie();
        for (int i = strings.length - 1;i >= 0;--i) {
            trie.put(strings[i]);
        }
    }

    public boolean query(char ch) {
        ring.put(ch);
        return trie.contain(ring);
    }
}
