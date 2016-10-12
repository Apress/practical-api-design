package org.apidesign.samples;

import java.util.ArrayList;
import java.util.List;

public class StringBuffer {
    private List<String> all = new ArrayList<String>();
    
    public StringBuffer() {
        // prevent subclassing from 3rd party code
        if (getClass() != StringBuffer.class && getClass() != StringBufferUnsynch.class) {
            throw new IllegalStateException();
        }
    }
    
    // BEGIN: string.buffer.factory
    public static StringBuffer createUnsynchronized() {
        return new StringBufferUnsynch();
    }
    // END: string.buffer.factory
    
    public synchronized StringBuffer append(String s) {
        return appendImpl(s);
    }
    
    @Override
    public synchronized String toString() {
        return toStringImpl();
    }
    
    final String toStringImpl() {
        String ret = "";
        for (String s : all) {
            ret += s;
        }
        return ret;
    }
    
    
    final StringBuffer appendImpl(String s) {
        all.add(s);
        return this;
    }
    
    
    private static final class StringBufferUnsynch extends StringBuffer {
        @Override
        public StringBuffer append(String s) {
            return appendImpl(s);
        }
        @Override
        public String toString() {
            return toStringImpl();
        }
    }
}
