package org.apidesign.reentrant;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class CriticalSectionReentrantImmutable<T extends Comparable<T>> implements CriticalSection<T> {
    private ImmutableData<T> data = new ImmutableData<T>();
    
    public void assignPilot(T pilot) {
        data = data.newPilot(pilot);
    }

    // BEGIN: reentrant.merge.immutable
    public int sumBigger(Collection<T> args) {
        for (;;) {
            ImmutableData<T> previous;
            synchronized (this) {
                previous = this.data;
            }
            int[] ret = { 0 };
            ImmutableData<T> now = doCriticalSection(args, previous, ret);
            
            synchronized (this) {
                // if there was no parallel or reentrant change, 
                // apply and return. Otherwise try once more.
                if (previous == this.data) {
                    this.data = now;
                    return ret[0];
                }
            }
        }
    }
    // END: reentrant.merge.immutable
    
    private ImmutableData<T> doCriticalSection(Collection<T> args, ImmutableData<T> data, int[] result) {
        result[0] = 0;
        for (T cmp : args) {
            if (data.pilot.compareTo(cmp) < 0) {
                result[0]++;
            }
        }
        return data.newCnt(data.cnt + result[0]);
    }

    public int getCount() {
        return data.cnt;
    }
    
    private static final class ImmutableData<T> {
        final T pilot;
        final int cnt;
        
        public ImmutableData() {
            pilot = null;
            cnt = 0;
        }
        private ImmutableData(T pilot, int cnt) {
            this.pilot = pilot;
            this.cnt = cnt;
        }
        
        public ImmutableData<T> newPilot(T pilot) {
            return new ImmutableData(pilot, this.cnt);
        }
        public ImmutableData<T> newCnt(int cnt) {
            return new ImmutableData(this.pilot, cnt);
        }
    }
}
