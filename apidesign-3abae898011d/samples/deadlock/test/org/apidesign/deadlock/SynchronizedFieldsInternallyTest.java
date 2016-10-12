package org.apidesign.deadlock;

import org.junit.Test;
import static org.junit.Assert.*;

public class SynchronizedFieldsInternallyTest {
    public SynchronizedFieldsInternallyTest() {
    }

    @Test
    public void increment() {
        SynchronizedFieldsInternally instance = new SynchronizedFieldsInternally();
        instance.increment();
    }

    @Test
    public void unsafeDecrement() {
        SynchronizedFieldsInternally instance = new SynchronizedFieldsInternally();
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
        SynchronizedFieldsInternally instance = new SynchronizedFieldsInternally();
        try {
            synchronized (instance) {
                instance.unsafeDecrement();
            }
        } catch (AssertionError ex) {
            // OK
            return;
        }
        fail("Unlike the SynchronizedFieldsTest, the fix by wrapping instance" +
            "into own synchronized block will not help, neither any other" +
            "fix, the lock is really private"
        );
    }
}