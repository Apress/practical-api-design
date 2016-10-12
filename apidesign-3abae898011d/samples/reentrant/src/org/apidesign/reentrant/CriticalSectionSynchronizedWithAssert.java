package org.apidesign.reentrant;

import java.util.Collection;

public class CriticalSectionSynchronizedWithAssert<T extends Comparable<T>> implements CriticalSection<T> {
    private T pilot;
    private int cnt;
    
    public synchronized void assignPilot(T pilot) {
        assert !working : "Shall not be working yet in order to be consistent";
        this.pilot = pilot;
    }

    // BEGIN: reentrant.assert
    private boolean working;
    public int sumBigger(Collection<T> args) {
        assert !working : "Shall not be working yet in order to be consistent";
        working = true;
        try {
            return doCriticalSection(args);
        } finally {
            working = false;
        }
    }
    // END: reentrant.assert
    
    
    private int doCriticalSection(Collection<T> args) {
        for (T cmp : args) {
            if (pilot.compareTo(cmp) < 0) {
                cnt++;
            }
        }
        return cnt;
    }
    
    public int getCount() {
        assert !working : "Shall not be working yet in order to be consistent";
        return cnt;
    }
}
