package org.apidesign.reentrant;

import java.util.Collection;

public interface CriticalSection<T extends Comparable<T>> {
    
    public void assignPilot(T pilot);
    public int sumBigger(Collection<T> args);
    public int getCount();
}
