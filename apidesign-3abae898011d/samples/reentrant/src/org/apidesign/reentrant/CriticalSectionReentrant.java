package org.apidesign.reentrant;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class CriticalSectionReentrant<T extends Comparable<T>> implements CriticalSection<T> {
    private T pilot;
    
    public void assignPilot(T pilot) {
        this.pilot = pilot;
    }

    // BEGIN: reentrant.merge.int
    private AtomicInteger cnt = new AtomicInteger();
    public int sumBigger(Collection<T> args) {
        T pilotCopy = this.pilot;
        int own = doCriticalSection(args, pilotCopy);
        // now merge with global state
        cnt.addAndGet(own);
        return own;
    }
    // END: reentrant.merge.int
    
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
