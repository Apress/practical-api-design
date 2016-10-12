package org.apidesign.deadlock;

public final class SynchronizedFieldsInternally {
    // BEGIN: deadlock.ownLock
    private final Object LOCK = new Object();
    private int counter;
    
    private int getCounter() {
        assert Thread.holdsLock(LOCK);
        return counter;
    } 
    
    private void setCounter(int c) {
        assert Thread.holdsLock(LOCK);
        counter = c;
    }
    // END: deadlock.ownLock
    
    
    public void increment() {
        synchronized (LOCK) {
            setCounter(getCounter() + 1);
        }
    }
    
    public void unsafeDecrement() {
        setCounter(getCounter() - 1);
    }
}
