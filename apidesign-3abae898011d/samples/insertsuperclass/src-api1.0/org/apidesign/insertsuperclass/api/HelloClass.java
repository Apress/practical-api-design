package org.apidesign.insertsuperclass.api;

public abstract class HelloClass {
// BEGIN: design.insert.superclass
    public abstract String sayHello();
    public abstract String sayHelloTo(String who);
// END: design.insert.superclass
}
