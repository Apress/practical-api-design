package org.apidesign.privileged.use;

import org.apidesign.privileged.api.Mutex;
import org.junit.Test;
import static org.junit.Assert.*;

public class MutexTest {
    // BEGIN: mutex.init
    private static final Mutex.Privileged PRIVILEGED = new Mutex.Privileged();
    public static final Mutex MUTEX = new Mutex(PRIVILEGED);
    // END: mutex.init

    public MutexTest() {
    }

    /**
     * Test of withLock method, of class Mutex.
     */
    @Test
    public void readAccess() {
        // BEGIN: mutex.use
        class R implements Runnable {
            int cnt;
            
            public void run() {
                cnt++;
            }
        }
        R r = new R();
        MUTEX.withLock(r);
        assertEquals("Counter increased", 1, r.cnt);
        // END: mutex.use
    }
    
    @Test
    public void usePrivileged() {
        int cnt = 0;
        // BEGIN: mutex.privileged
        try {
            PRIVILEGED.lock();
            // do the operation
            cnt++;
        } finally {
           PRIVILEGED.unlock();
        }
        assertEquals("Counter increased", 1, cnt);
        // END: mutex.privileged
        
    }

}
