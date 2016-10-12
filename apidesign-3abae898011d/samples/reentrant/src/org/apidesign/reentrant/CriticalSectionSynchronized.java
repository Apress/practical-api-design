package org.apidesign.reentrant;

import java.util.Collection;

public class CriticalSectionSynchronized<T extends Comparable<T>> implements CriticalSection<T> {
    private T pilot;
    private int cnt;
    
    public synchronized void assignPilot(T pilot) {
        this.pilot = pilot;
    }

    public int sumBigger(Collection<T> args) {
        for (T cmp : args) {
            if (pilot.compareTo(cmp) < 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public int getCount() {
        return cnt;
    }
}
