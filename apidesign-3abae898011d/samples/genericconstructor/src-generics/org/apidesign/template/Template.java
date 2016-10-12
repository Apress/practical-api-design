package org.apidesign.template;

// BEGIN: generics.constructor.template2
public final class Template<T> extends Object {
    private final Class<T> type;

    public Template(Class<T> type) { this.type = type; }
    public Class<T> getType() { return type; }

    // now what!?
    public Template() { this(Object.class); }
} 
// END: generics.constructor.template2
