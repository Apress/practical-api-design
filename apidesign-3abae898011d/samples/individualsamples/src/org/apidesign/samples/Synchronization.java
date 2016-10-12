package org.apidesign.samples;

public class Synchronization {
    private final Object anObject = new Object();
    
    public void methodUsingSynchronizedBlock() {
        // BEGIN: synchronize.anObject
        synchronized (anObject) {
            // do the critical stuff
        }
        // END: synchronize.anObject
    }
}
