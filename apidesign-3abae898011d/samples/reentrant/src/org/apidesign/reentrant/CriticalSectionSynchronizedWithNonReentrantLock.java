package org.apidesign.reentrant;

import java.nio.channels.NonReadableChannelException;
import java.util.Collection;
import java.util.concurrent.locks.Lock;

public class CriticalSectionSynchronizedWithNonReentrantLock<T extends Comparable<T>> implements CriticalSection<T> {
    private T pilot;
    private int cnt;
    
    public void assignPilot(T pilot) {
        lock.lock();
        try {
            this.pilot = pilot;
        } finally {
            lock.unlock();
        }
    }

    // BEGIN: reentrant.nonreentrant.lock
    private Lock lock = new NonReentrantLock();
    public int sumBigger(Collection<T> args) {
        lock.lock();
        try {
            return doCriticalSection(args);
        } finally {
            lock.unlock();
        }
    }
    // END: reentrant.nonreentrant.lock
    
    private int doCriticalSection(Collection<T> args) {
        for (T cmp : args) {
            if (pilot.compareTo(cmp) < 0) {
                cnt++;
            }
        }
        return cnt;
    }
    
    public int getCount() {
        lock.lock();
        try {
            return cnt;
        } finally {
            lock.unlock();
        }
    }
}
