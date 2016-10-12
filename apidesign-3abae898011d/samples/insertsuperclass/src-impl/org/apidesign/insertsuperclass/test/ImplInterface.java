package org.apidesign.insertsuperclass.test;

import org.apidesign.insertsuperclass.api.HelloInterface;

public class ImplInterface implements HelloInterface {
    public String sayHello() {
        return "Hello Unknown!";
    }

    public String sayHelloTo(String who) {
        return "Hello " + who + '!';
    }
}
