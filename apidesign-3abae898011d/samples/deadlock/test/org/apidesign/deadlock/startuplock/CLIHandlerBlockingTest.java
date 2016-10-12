package org.apidesign.deadlock.startuplock;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

// BEGIN: test.capture.logs
public class CLIHandlerBlockingTest {

    public CLIHandlerBlockingTest() {
    }
    
    @BeforeClass
    public static void initHandler() {
        Logger.getLogger("").addHandler(new H());
        Logger.getLogger("").setLevel(Level.ALL);
    }

    @Before
    public void setUp() {
        H.sb.setLength(0);
    }

    @Test
    public void start() throws Exception {
        File lockFile = File.createTempFile("pref", ".tmp");
        int result = CLIHandlerBlocking.start(lockFile);
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals("Show a failure" + H.sb, -10, result);
    }

    private static final class H extends Handler {
        static StringBuffer sb = new StringBuffer();
        
        @Override
        public void publish(LogRecord record) {
            sb.append(record.getMessage()).append('\n');
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    } // end of H
}
// END: test.capture.logs
