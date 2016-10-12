/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.privileged.api;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@netbeans.org>
 */
// BEGIN: mutex.api
public final class Mutex {
    Lock lock = new ReentrantLock();
    
    public Mutex() {
    }
    
    public void withLock(Runnable r) {
        try {
            lock.lock();
            r.run();
        } finally {
            lock.unlock();
        }
    }
// FINISH: mutex.api

// BEGIN: mutex.privileged.api
    public Mutex(Privileged privileged) {
        if (privileged.mutex != null) {
            throw new IllegalStateException();
        }
        privileged.mutex = this;
    }
    
    public static final class Privileged {
        private Mutex mutex;
        
        public void lock() {
            mutex.lock.lock();
        }
        
        public void unlock() {
            mutex.lock.unlock();
        }
    }
// END: mutex.privileged.api
}
