package org.apidesign.reentrant;

public class CriticalSectionSynchronizedWithNonReentrantLockTest extends CriticalSectionBase {

    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionSynchronizedWithNonReentrantLock<Integer>();
    }

}