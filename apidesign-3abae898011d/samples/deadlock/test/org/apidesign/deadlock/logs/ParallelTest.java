package org.apidesign.deadlock.logs;

import java.util.logging.Level;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: test.parallel.test
public class ParallelTest extends NbTestCase {
    public ParallelTest(String testName) {
        super(testName);
    }

    @Override
    protected Level logLevel() {
        return Level.WARNING;
    }

    public void testMain() throws Exception {
        Parallel.main(null);
        if (Boolean.getBoolean("no.failures")) return;
        fail("Ok, just print logged messages");
    }
}
// END: test.parallel.test
