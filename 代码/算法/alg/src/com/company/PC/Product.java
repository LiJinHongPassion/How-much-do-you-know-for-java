package com.company.PC;

/**
 * 描述:
 *
 * @author codeant
 * @date 20.8.18
 */
public class Product<T> {
    String name;
    T t;

    public  Product(String name, T t) {
        this.name = name;
        this.t = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
