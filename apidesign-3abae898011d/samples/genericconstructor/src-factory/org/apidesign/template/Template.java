package org.apidesign.template;

// BEGIN: generics.constructor.template3
public final class Template<T> extends Object {
    private final Class<T> type;

    public Template(Class<T> type) { this.type = type; }
    public Class<T> getType() { return type; }

    @Deprecated
    @SuppressWarnings("unchecked")
    public Template() { this((Class<T>)Object.class); }
    
    public static Template<Object> create() {
        return new Template<Object>(Object.class);
    }
} 
// END: generics.constructor.template3
