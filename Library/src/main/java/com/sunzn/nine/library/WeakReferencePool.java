package com.sunzn.nine.library;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;

public final class WeakReferencePool<T> {

    private WeakReference<T>[] pool;
    private int size;
    private int curPointer = -1;

    public WeakReferencePool() {
        this(5);
    }

    public WeakReferencePool(int size) {
        this.size = size;
        pool = (WeakReference<T>[]) Array.newInstance(WeakReference.class, size);
    }

    public synchronized T get() {
        if (curPointer == -1 || curPointer > pool.length) return null;
        T obj = pool[curPointer].get();
        pool[curPointer] = null;
        curPointer--;
        return obj;
    }

    public synchronized boolean put(T t) {
        if (curPointer == -1 || curPointer < pool.length - 1) {
            curPointer++;
            pool[curPointer] = new WeakReference<T>(t);
            return true;
        }
        return false;
    }

    public void clearPool() {
        for (int i = 0; i < pool.length; i++) {
            pool[i].clear();
            pool[i] = null;
        }
        curPointer = -1;
    }

    public int size() {
        return pool == null ? 0 : pool.length;
    }

}
