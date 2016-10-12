
package org.apidesign.insertsuperclass.test;

import org.apidesign.insertsuperclass.api.HelloClass;
import org.apidesign.insertsuperclass.api.HelloFieldClass;
import org.apidesign.insertsuperclass.api.HelloInterface;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        boolean assertionsOn = false;
        assert assertionsOn = true;
        if (!assertionsOn) {
            throw new IllegalStateException("Enable assertions!");
        }
        
        HelloClass clazz = new ImplClass();
        assert "Hello Unknown!".equals(clazz.sayHello());
        assert "Hello Jaroslav!".equals(clazz.sayHelloTo("Jaroslav"));
        System.err.println("Who defines sayHello(): " + clazz.getClass().getSuperclass().getMethod("sayHello"));

        HelloInterface iface = new ImplInterface();
        assert "Hello Unknown!".equals(iface.sayHello());
        assert "Hello Jaroslav!".equals(iface.sayHelloTo("Jaroslav"));
        System.err.println("Who defines sayHello(): " + iface.getClass().getInterfaces()[0].getMethod("sayHello"));
        
        HelloFieldClass fieldClass = new HelloFieldClass();
        assert "Hello".equals(fieldClass.field);
        System.err.println("Who defines field: " + fieldClass.getClass().getField("field"));
    }
}
