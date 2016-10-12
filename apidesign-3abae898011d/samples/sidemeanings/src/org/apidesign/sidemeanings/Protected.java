package org.apidesign.sidemeanings;

public abstract class Protected {
    int counter;

    Protected() {}

    protected void increment() {
        counter++;
    }

    public final void incrementTenTimes() {
        for (int i = 0; i < 10; i++) {
            increment();
        }
    }

    public final void assertCounter(int expected) {
        assert expected == counter : "Expected " + expected + " but was " + counter;
    }

    
    public static class Dirty extends Protected {
        // BEGIN: sidemeanings.Protected.Dirty
        protected void increment() {
            // implementation:
            counter++;
        }
        // END: sidemeanings.Protected.Dirty
    }
    
    
    public static abstract class Clean extends Protected {
        // BEGIN: sidemeanings.Protected.Clean
        protected abstract void increment();
        protected final void defaultIncrement() {
            counter++;
        }
        // END: sidemeanings.Protected.Clean
    }
}
