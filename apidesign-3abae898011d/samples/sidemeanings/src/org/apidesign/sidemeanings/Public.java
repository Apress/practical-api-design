package org.apidesign.sidemeanings;

public abstract class Public {
    int counter;

    Public() {}

    public abstract void increment();

    public final void incrementTenTimes() {
        for (int i = 0; i < 10; i++) {
            increment();
        }
    }

    public final void assertCounter(int expected) {
        assert expected == counter : "Expected " + expected + " but was " + counter;
    }

    
    public static abstract class Dirty extends Public {
        // BEGIN: sidemeanings.Public.Dirty
        public void increment() {
            // internal implementation
            counter++;
        }
        // END: sidemeanings.Public.Dirty
    }
    
    
    public static abstract class Clean extends Public {
        // BEGIN: sidemeanings.Public.Clean
        public final void increment() {
            overridableIncrement();
        }
        protected abstract void overridableIncrement();
        protected final void defaultIncrement() {
            counter++;
        }
        // END: sidemeanings.Public.Clean
    }
}
