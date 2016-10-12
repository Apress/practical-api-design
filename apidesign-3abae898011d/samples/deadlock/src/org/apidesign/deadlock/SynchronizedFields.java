package org.apidesign.deadlock;

public final class SynchronizedFields {
    // BEGIN: deadlock.holdsLock
    private int counter;
    
    private int getCounter() {
        assert Thread.holdsLock(this);
        return counter;
    } 
    
    private void setCounter(int c) {
        assert Thread.holdsLock(this);
        counter = c;
    }
    // END: deadlock.holdsLock
    
    
    public synchronized void increment() {
        setCounter(getCounter() + 1);
    }
    
    public void unsafeDecrement() {
        setCounter(getCounter() - 1);
    }
}
