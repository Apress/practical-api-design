package org.apidesign.reentrant;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class CriticalSectionReentrantForLoop<T extends Comparable<T>> implements CriticalSection<T> {
    private volatile T pilot;
    private AtomicInteger cnt = new AtomicInteger();
    
    public void assignPilot(T pilot) {
        this.pilot = pilot;
    }

    // BEGIN: reentrant.merge.for
    public int sumBigger(Collection<T> args) {
        T pilotCopy = this.pilot;
        for (;;) {
            int previous = cnt.get();
            int own = doCriticalSection(args, pilotCopy);
            // if there was no parallel or reentrant change, 
            // apply and return. Otherwise try once more.
            if (cnt.compareAndSet(previous, own + previous)) {
                return own;
            }
        }
    }
    // END: reentrant.merge.for
    
    private int doCriticalSection(Collection<T> args, T pilotCopy) {
        int own = 0;
        for (T cmp : args) {
            if (pilotCopy.compareTo(cmp) < 0) {
                own++;
            }
        }
        return own;
    }

    public int getCount() {
        return cnt.get();
    }
}
