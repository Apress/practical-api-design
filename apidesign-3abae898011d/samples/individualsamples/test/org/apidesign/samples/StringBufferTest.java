package org.apidesign.samples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringBufferTest {
    @Test
    public void createRegular() {
        StringBuffer sb = new StringBuffer();
        assertAddAndToString(sb);
    }

    @Test
    public void createUnsynchronized() throws InterruptedException {
        final StringBuffer sb = StringBuffer.createUnsynchronized();
        
        class Lock extends Thread {
            int state;
            
            @Override
            public void run() {
                synchronized (sb) {
                    try {
                        state = 1;
                        sb.notifyAll();
                        sb.wait();
                        state = 2;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StringBufferTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            public void waitLocked() throws InterruptedException {
                synchronized (sb) {
                    for (;;) {
                        if (state == 1) {
                            return;
                        }
                        sb.wait();
                    }
                }
            }
        }
        Lock lock = new Lock();
        lock.start();
        lock.waitLocked();
        
        assertEquals("result is really locked", 1, lock.state);
        
        assertAddAndToString(sb);
        
        assertEquals("result is still locked", 1, lock.state);
    }

    private void assertAddAndToString(StringBuffer sb) {
        sb.append("Hello").append(" ");
        sb.append("API").append(" Design!");
        
        assertEquals("Hello API Design!", sb.toString());
    }

}