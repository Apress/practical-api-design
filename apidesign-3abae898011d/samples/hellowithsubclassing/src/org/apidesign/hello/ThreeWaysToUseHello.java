package org.apidesign.hello;

public class ThreeWaysToUseHello {

    // BEGIN: hello.say
    public static void sayHello() {
        Hello hello = new Hello();
        hello.hello();
    }
    // END: hello.say
    
    // BEGIN: hello.subclass
    private static class MyHello extends Hello {
        @Override
        public void hello() { System.out.println ("Hi"); }
    }
    // END: hello.subclass
    
    // BEGIN: hello.supercall
    private static class SuperHello extends Hello {
        @Override
        public void hello() { 
            super.hello(); 
            System.out.println("Hello once again"); 
        }
    }
    // END: hello.supercall
    
    /** shows more ways to use a class. prints four various messages */
    public static void main(String[] args) {
        sayHello();
        new MyHello().hello();
        new SuperHello().hello();
    }
}
