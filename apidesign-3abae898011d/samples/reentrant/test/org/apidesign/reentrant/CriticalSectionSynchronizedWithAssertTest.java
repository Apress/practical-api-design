package org.apidesign.reentrant;

public class CriticalSectionSynchronizedWithAssertTest extends CriticalSectionBase {
    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionSynchronizedWithAssert<Integer>();
    }

}