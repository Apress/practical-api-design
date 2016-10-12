package org.apidesign.javamonitorflaws;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.junit.NbTestCase;

public class CacheTest extends NbTestCase {
    public CacheTest(String n) {
        super(n);
    }

    @Override
    protected int timeOut() {
        return 2000;
    }

    /**
     * To simulate deadlock between the cache's setMultiply method and
     * ToDeadlock's own lock. The root cause is that setMultiply is calling
     * foreign code while holding the caches's <code>this</code> lock:
     
Thread Test Watch Dog: testDeadlockWithSetter
  org.apidesign.javamonitorflaws.MultiplyCache.setMultiply:19
  org.apidesign.javamonitorflaws.CacheTest$1ToDeadlock.assertMultiplyByTen:40
  org.apidesign.javamonitorflaws.CacheTest.testDeadlockWithSetter:50
  sun.reflect.NativeMethodAccessorImpl.invoke0:-2
  sun.reflect.NativeMethodAccessorImpl.invoke:39
  sun.reflect.DelegatingMethodAccessorImpl.invoke:25
  java.lang.reflect.Method.invoke:597
  junit.framework.TestCase.runTest:168
  org.netbeans.junit.NbTestCase.access$200:84
  org.netbeans.junit.NbTestCase$2.doSomething:328
  org.netbeans.junit.NbTestCase$1Guard.run:265
  java.lang.Thread.run:619
Thread to deadlock
  org.apidesign.javamonitorflaws.CacheTest$1ToDeadlock.assertMultiplyByTen:40
  org.apidesign.javamonitorflaws.CacheTest$1ToDeadlock.propertyChange:36
  java.beans.PropertyChangeSupport.firePropertyChange:339
  java.beans.PropertyChangeSupport.firePropertyChange:276
  java.beans.PropertyChangeSupport.firePropertyChange:297
  org.apidesign.javamonitorflaws.MultiplyCache.setMultiply:21
  org.apidesign.javamonitorflaws.CacheTest$1ToDeadlock.run:28
  java.lang.Thread.run:619
     
     */
    public void testDeadlockWithSetter() throws Exception {
        if (Boolean.getBoolean("no.failures")) return;
        
        final CacheToTest cache = new MultiplyCache();

        class ToDeadlock implements Runnable, PropertyChangeListener {
            int value;

            public void run() {
                cache.setMultiply(10);
            }
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    // ok
                }
                changeMultiplyToSeven();
            }

            public synchronized void changeMultiplyToSeven() {
                cache.setMultiply(7);
            }
        }
        ToDeadlock toDeadlock = new ToDeadlock();
        cache.addPropertyChangeListener(toDeadlock);
        Thread t = new Thread(toDeadlock, "to deadlock");
        t.start();

        Thread.sleep(100);

        toDeadlock.changeMultiplyToSeven();
    }

    /** Shows that one can deadlock with the cache's API even the API
     * is locally correctly synchronized.
Thread Test Watch Dog: testDeadlockWithTheAPICacheItself
  org.apidesign.javamonitorflaws.Cache.get:16
  org.apidesign.javamonitorflaws.CacheTest$2ToDeadlock.assertMultiplyByTen
  org.apidesign.javamonitorflaws.CacheTest.testDeadlockViaAPI:112
  java.lang.reflect.Method.invoke:597
  org.netbeans.junit.NbTestCase.access$200:84
  org.netbeans.junit.NbTestCase$2.doSomething:328
  org.netbeans.junit.NbTestCase$1Guard.run:265
  java.lang.Thread.run:619
Thread Deadlock using API
  org.apidesign.javamonitorflaws.CacheTest$2ToDeadlock.assertMultiplyByTen
  org.apidesign.javamonitorflaws.CacheTest$2ToDeadlock.propertyChange:98
  java.beans.PropertyChangeSupport.firePropertyChange:339
  java.beans.PropertyChangeSupport.firePropertyChange:276
  java.beans.PropertyChangeSupport.firePropertyChange:297
  org.apidesign.javamonitorflaws.MultiplyCache.setMultiply:21
  org.apidesign.javamonitorflaws.CacheTest$2ToDeadlock.run:90
  java.lang.Thread.run:619
     */
    public void testDeadlockViaAPI() throws Exception {
        if (Boolean.getBoolean("no.failures")) return;
        testDeadlockViaAPI(new MultiplyCache());
    }

    // BEGIN: monitor.pitfalls.block.propertychange
    private static void testDeadlockViaAPI(final CacheToTest cache)
    throws Exception {
        class ToDeadlock implements Runnable, PropertyChangeListener {
            int lastMultiply;

            public void run() {
                cache.setMultiply(10);
            }
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    storeMultiply();
                } catch (InterruptedException ex) {
                    // ok
                }
            }

            private synchronized void storeMultiply()
            throws InterruptedException {
                lastMultiply = cache.getMultiply();
                // simulates "starvation"
                wait();
            }

            public void assertMultiplyByTen() {
            }
        }
        ToDeadlock toDeadlock = new ToDeadlock();
        cache.addPropertyChangeListener(toDeadlock);
        Thread t = new Thread(toDeadlock, "Deadlock using API");
        t.start();

        Thread.sleep(100);

        // BEGIN: monitor.pitfalls.brokencall
        int value =  cache.get("123");
        assertEquals("3*10=30", 30, value);
        // END: monitor.pitfalls.brokencall
    }
    // END: monitor.pitfalls.block.propertychange

    public void testDeadlockViaAPIWithCacheOK() throws Exception {
        testDeadlockViaAPI(new MultiplyCacheOK());
    }

    static interface CacheToTest {
        public Integer get(String key);

        public void setMultiply(int m);
        public int getMultiply();
        public void addPropertyChangeListener(PropertyChangeListener l);
        public void removePropertyChangeListener(PropertyChangeListener l);
    }
}