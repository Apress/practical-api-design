package org.apidesign.sidemeanings.test;

import org.apidesign.sidemeanings.PublicAbstract;
import org.junit.Assert;
import org.junit.Test;

public class PublicAbstractTest {

    @Test public void testCallTenDoubleIncrementOnDirtyAPI() {
        // BEGIN: sidemeanings.PublicAbstract.Dirty.test
        class DoubleIncrement extends PublicAbstract.Dirty {
            int counter;

            @Override
            public void increment() {
                counter += 2;
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        Assert.assertEquals(20, doubleIncr.counter);
        // END: sidemeanings.PublicAbstract.Dirty.test
    }

    @Test public void testCallTenDoubleIncrementOnCleanAPI() {
        // BEGIN: sidemeanings.PublicAbstract.Clean.test
        class DoubleIncrement extends PublicAbstract.Clean {
            int counter;

            @Override
            protected void overridableIncrement() {
                counter += 2;
            }
        }
        DoubleIncrement doubleIncr = new DoubleIncrement();
        doubleIncr.incrementTenTimes();
        Assert.assertEquals(20, doubleIncr.counter);
        // END: sidemeanings.PublicAbstract.Clean.test
    }

}
