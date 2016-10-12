package org.apidesign.extensionpoint.impl1;

import org.apidesign.extensionpoint.api.TipOfTheDay;

public class HelloDesign implements TipOfTheDay{
    public String sayHello() {
        return "Hello API Design!";
    }
}
