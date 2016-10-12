package org.apidesign.template;


// BEGIN: generics.constructor.template1
public final class Template extends Object {
    private final Class type;

    public Template(Class type) { this.type = type; }
    public Class getType() { return type; }


    public Template() { this(Object.class); }
} 
// END: generics.constructor.template1
