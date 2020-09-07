package com.example.talent.sep;

/**
 * 从begin到end循环存储值,只有第一个值时begin和end相同，会覆盖掉以前的值
 * @author 林高琦
 */
public class RingBuffer {
    /**
     * 循环数组
     */
    private char[] array;
    private int begin, end, length;

    public RingBuffer(int length) {
        array = new char[length];
        begin = 0;
        end = -1;
        this.length = 0;
    }

    public void put(char c) {
        //放入第一个值
        if (this.length == 0) {
            end = 0;
            array[end] = c;
            this.length = 1;
            return;
        }

        end = (end + 1) % array.length;
        array[end] = c;
        if (end == begin) {
            begin = (begin + 1) % array.length;
        }
        if (this.length < array.length) {
            ++this.length;
        }
    }

    public char get(int index) {
        if (index >= this.length) {
            throw new RuntimeException("超出取值范围！");
        }
        return array[(index + begin) % array.length];
    }

    public int size() {
        return this.length;
    }
}
