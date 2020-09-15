package com.codeant.rpc.one;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 第一步先了解动态代理
 */
public class JavaDynamicProxyDemo {

    //接口
    interface InterfaceA {
        void doSomething();
    }

    //接口实现类
    static class InterfaceAImpl implements InterfaceA {

        @Override
        public void doSomething() {
            System.out.println("InterfaceImplA.doSomething()");
        }
    }

    //动态代理
    static class InterfaceInvocationHandler implements InvocationHandler {
        private Object obj;

        public InterfaceInvocationHandler(Object o) {
            this.obj = o;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(this.obj, args);
        }
    }

    //测试
    public static void main(String... args) {

        InterfaceA interfaceA = new InterfaceAImpl();

        InterfaceA a = (InterfaceA) Proxy.newProxyInstance(
                interfaceA.getClass().getClassLoader(),
                interfaceA.getClass().getInterfaces(),
                new InterfaceInvocationHandler(interfaceA));

        a.doSomething();
    }
}