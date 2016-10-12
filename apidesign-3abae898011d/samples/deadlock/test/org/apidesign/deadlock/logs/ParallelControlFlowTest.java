package org.apidesign.deadlock.logs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

public class ParallelControlFlowTest extends NbTestCase {
    public ParallelControlFlowTest(String testName) {
        super(testName);
    }

    @Override
    protected Level logLevel() {
        return Level.WARNING;
    }

// BEGIN: test.parallel.test.controlflow
    public void testMain() throws Exception {
        org.netbeans.junit.Log.controlFlow(Logger.global, null,
            "THREAD: 1st MSG: cnt: 0" +
            "THREAD: 2nd MSG: .*0" +
            "THREAD: 1st MSG: ...: 1" +
            "THREAD: 2nd MSG: cnt: 1" +
            "THREAD: 1st MSG: cnt: 2" +
            "THREAD: 2nd MSG: cnt: 2" +
            "THREAD: 1st MSG: cnt: 3" +
            "THREAD: 2nd MSG: cnt: 3" +
            "THREAD: 1st MSG: cnt: 4" +
            "THREAD: 2nd MSG: cnt: 4" +
            "THREAD: 1st MSG: cnt: 5" +
            "THREAD: 2nd MSG: cnt: 5" +
            "THREAD: 1st MSG: cnt: 6" +
            "THREAD: 2nd MSG: cnt: 6" +
            "THREAD: 1st MSG: cnt: 7" +
            "THREAD: 2nd MSG: cnt: 7" +
            "THREAD: 1st MSG: cnt: 8" +
            "THREAD: 2nd MSG: cnt: 8" +
            "THREAD: 1st MSG: cnt: 9" +
            "THREAD: 2nd MSG: cnt: 9",
            500
        );
        Parallel.main(null);
        if (Boolean.getBoolean("no.failures")) return;
        fail("Ok, just print the logged output");
    }
// END: test.parallel.test.controlflow
    
    
    // BEGIN: test.parallel.test.fivetwo
    public void testFiveAndThenTwo() throws Exception {
        org.netbeans.junit.Log.controlFlow(Logger.global, null,
            "THREAD: 1st MSG: cnt: 5" +
            "THREAD: 2nd MSG: cnt: 2" +
            "THREAD: 1st MSG: cnt: 6",
            5000
        );
        Parallel.main(null);
        if (Boolean.getBoolean("no.failures")) return;
        fail("Ok, just print the logged output");
    }
    // END: test.parallel.test.fivetwo
    
}
