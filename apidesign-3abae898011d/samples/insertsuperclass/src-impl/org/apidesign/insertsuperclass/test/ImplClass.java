package org.apidesign.insertsuperclass.test;

import org.apidesign.insertsuperclass.api.HelloClass;

public class ImplClass extends HelloClass {
    public String sayHello() {
        return "Hello Unknown!";
    }

    public String sayHelloTo(String who) {
        return "Hello " + who + '!';
    }
}
