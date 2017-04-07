package com.example.ipctest;

/**
 * Created by ldh on 2016/8/30 0030.
 */
public class Test {
    public void myMethod(Object o) {
        System.out.println("My Object");
    }

    public void myMethod(String s) {
        System.out.println("My String:" + s);
    }

    public static void main(String args[]) {
        Test t = new Test();
        t.myMethod(null);
    }
}
