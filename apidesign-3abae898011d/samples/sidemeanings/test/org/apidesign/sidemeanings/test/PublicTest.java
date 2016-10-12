package org.apidesign.sidemeanings.test;

import org.apidesign.sidemeanings.Public;
import org.junit.Test;

public class PublicTest {

    @Test public void testCallTenDoubleIncrementOnDirtyAPI() {
        // BEGIN: sidemeanings.Public.Dirty.test
        class DoubleIncrement extends Public.Dirty {
            @Override
            public void increment() {
                super.increment();
                super.increment();
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        doubleIncr.assertCounter(20);
        // END: sidemeanings.Public.Dirty.test
    }

    @Test public void testCallTenDoubleIncrementOnCleanAPI() {
        // BEGIN: sidemeanings.Public.Clean.test
        class DoubleIncrement extends Public.Clean {
            @Override
            protected void overridableIncrement() {
                defaultIncrement();
                defaultIncrement();
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        doubleIncr.assertCounter(20);
        // END: sidemeanings.Public.Clean.test
    }

}
