package org.apidesign.deadlock.startuplock;

import java.io.File;
import java.util.logging.Level;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: test.capture.logs.nbjunit
public class CLIHandlerBlockingWithNbTestCaseTest extends NbTestCase {

    public CLIHandlerBlockingWithNbTestCaseTest(String s) {
        super(s);
    }

    @Override
    protected Level logLevel() {
        return Level.ALL;
    }
    
    
    public void testStart() throws Exception {
        File lockFile = File.createTempFile("pref", ".tmp");
        int result = CLIHandlerBlocking.start(lockFile);
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals("Show a failure", -10, result);
    }
}
// END: test.capture.logs.nbjunit
