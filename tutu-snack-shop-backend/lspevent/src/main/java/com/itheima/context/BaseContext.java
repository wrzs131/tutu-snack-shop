package com.itheima.context;
//该类是一个基于 ThreadLocal 的上下文工具类，主要用于在多线程环境中存储和获取当前线程的上下文信息
//主要功能：
//1.线程隔离的上下文存储
//2.存储当前用户ID
//通过 setCurrentId 方法存储当前操作用户的ID
//在任何需要的地方通过 getCurrentId 获取当前用户ID
//3.在需要在同一次请求的多个层级中传递用户身份信息时非常有用
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
