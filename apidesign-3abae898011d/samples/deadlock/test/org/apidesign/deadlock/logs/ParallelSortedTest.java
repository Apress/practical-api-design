package org.apidesign.deadlock.logs;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: test.parallel.test.sorted
public class ParallelSortedTest extends NbTestCase {
    public ParallelSortedTest(String testName) {
        super(testName);
    }

    @Override
    protected Level logLevel() {
        return Level.WARNING;
    }

    public void testMain() throws Exception {
        Logger.global.addHandler(new BlockingHandler());
        Parallel.main(null);
        if (Boolean.getBoolean("no.failures")) return;
        fail("Ok, just print the logged output");
    }

    private static final class BlockingHandler extends Handler {

        boolean runSecond;

        public synchronized void publish(LogRecord record) {
            if (!record.getMessage().startsWith("cnt")) {
                return;
            }
            boolean snd = Thread.currentThread().getName().equals("2nd");
            if (runSecond == snd) {
                notify();
                runSecond = !runSecond;
            }
            try {
                wait(500);
            } catch (InterruptedException ex) {
            }
        }

        public void flush() {
        }

        public void close() {
        }
    }
}
// END: test.parallel.test.sorted
