package com.galaxy.data.structure;

import java.util.HashMap;

/**
 * 线性表-数组
 * @author galaxy
 * @since 2021/8/22 16:03
 */
public class ArrayTest {

    private Object[] data;

    private int size;

    public ArrayTest(){
        this.size = 10;
    }

    public ArrayTest(int l){
        this.size = l;
        data = new Object[size];
    }

    public Object add(Object o){
        if (size < data.length){
            data[++size] = o;
        }else {
            Object[] oldData = data;
            data = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                oldData[i] = data[i];
            }
            data[++size] = o;
        }
        return o;

    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    }
}
