package org.apidesign.stateful.api;

import junit.framework.TestCase;
import org.apidesign.stateful.api.ProgressStateless.InProgress;

public class ProgressTest extends TestCase {
    
    public ProgressTest(String testName) {
        super(testName);
    }

    public void testProgressStatefulWithoutStart() {
        try {
            // BEGIN: progress.wrong.order
            ProgressStateful p = ProgressStateful.create("WrongOrder");
            p.progress(10);
            p.finish();
            // END: progress.wrong.order
            
            fail("Calling progress without start yields an exception!?");
        } catch (IllegalStateException ex) {
            // OK
        }
    }

    public void testProgressStatelessNeedsStart() {
        ProgressStateless p = ProgressStateless.create("GoodOrder");
        InProgress progress = p.start(10);
        // without calling start(), there is no way to call progress() method
        progress.progress(10);
        progress.finish();
    }

}
