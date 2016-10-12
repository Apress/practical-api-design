package org.apidesign.sidemeanings.test;

import org.apidesign.sidemeanings.Protected;
import org.junit.Test;

public class ProtectedTest {

    @Test public void testCallTenDoubleIncrementOnDirtyAPI() {
        // BEGIN: sidemeanings.Protected.Dirty.test
        class DoubleIncrement extends Protected.Dirty {
            @Override
            protected void increment() {
                super.increment();
                super.increment();
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        doubleIncr.assertCounter(20);
        // END: sidemeanings.Protected.Dirty.test
    }

    @Test public void testCallTenDoubleIncrementOnCleanAPI() {
        // BEGIN: sidemeanings.Protected.Clean.test
        class DoubleIncrement extends Protected.Clean {
            @Override
            protected void increment() {
                // cannot be access directly, it is abstract:
                // super.increment();
                // we need to call default implementation instead
                defaultIncrement();
                defaultIncrement();
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        doubleIncr.assertCounter(20);
        // END: sidemeanings.Protected.Clean.test
    }
}
