package com.wlw.utils;


/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // 提供ThreadLocal对象，用于存储每个线程独有的数据
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    /**
     * 根据键获取值
     *
     * @param <T> 值的类型
     * @return 存储的值，如果未设置则返回null
     */
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    /**
     * 存储键值对
     *
     * @param value 要存储的值
     */
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    /**
     * 清除ThreadLocal 防止内存泄漏
     * 应在线程执行完毕后调用此方法，以确保不会因ThreadLocal持有引用而导致内存泄漏
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

