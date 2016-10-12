// BEGIN: extension.point.Impl2
package org.apidesign.extensionpoint.impl2;

import org.apidesign.extensionpoint.api.TipOfTheDay;


public class HelloWorld implements TipOfTheDay {
    public String sayHello() {
        return "Hello World!";
    }
}
// END: extension.point.Impl2
