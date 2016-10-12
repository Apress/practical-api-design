package org.apidesign.reentrant;

public class CriticalSectionSynchronizedTest extends CriticalSectionBase {
    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionSynchronized<Integer>();
    }
}