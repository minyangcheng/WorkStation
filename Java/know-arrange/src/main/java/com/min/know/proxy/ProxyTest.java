package com.min.know.proxy;

import com.min.know.util.L;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String args[]) {
        RealSubject real = new RealSubject();
        ISubject proxySubject = (ISubject) Proxy.newProxyInstance(ISubject.class.getClassLoader(),
                new Class[]{ISubject.class},
                new ProxyHandler(real));

        int num = proxySubject.doSomething(3);
        L.d("result=%s", num);
    }

    public static interface ISubject {
        public int doSomething(int num);
    }

    public static class RealSubject implements ISubject {
        @Override
        public int doSomething(int num) {
            L.d("call doSomething num=%s", num);
            return num;
        }
    }

    public static class ProxyHandler implements InvocationHandler {

        private Object proxied;

        public ProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //在转调具体目标对象之前，可以执行一些功能处理
            L.d("proxy=%s", proxy.getClass().getName());
            L.d("method=%s", method.getName());
            L.d("args=%s", args);

            //转调具体目标对象的方法
            Object object = method.invoke(proxied, args);

            return object;

            //在转调具体目标对象之后，可以执行一些功能处理
        }

    }

}
