package com.wlw;

import org.junit.jupiter.api.Test;

public class TestThreadLocal {

    @Test
    public void test() {
        //1.创建线程池
        ThreadLocal tl = new ThreadLocal();

        //2.创建线程
        new Thread(() -> {
            tl.set("消炎");
            tl.get();
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("补水");
            tl.get();
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "紫色").start();
    }
}
