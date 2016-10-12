package org.apidesign.deadlock;

import org.junit.Test;
import static org.junit.Assert.*;

public class SynchronizedFieldsTest {
    public SynchronizedFieldsTest() {
    }

    @Test
    public void increment() {
        SynchronizedFields instance = new SynchronizedFields();
        instance.increment();
    }

    @Test
    public void unsafeDecrement() {
        SynchronizedFields instance = new SynchronizedFields();
        try {
            instance.unsafeDecrement();
        } catch (java.lang.AssertionError ex) {
            // OK
            return;
        }
        fail("This will fail as unsafeDecrement is not synchronized, and that" +
            "is why it cannot access the field using getter and setter"
        );
    }

    @Test
    public void fixUnsafeDecrementFromOutside() {
        SynchronizedFields instance = new SynchronizedFields();
        synchronized (instance) {
            // in contract to original "Monitors", one can "fix" this 
            // problem from outside by using synchronizing externally
            instance.unsafeDecrement();
        }
    }
}