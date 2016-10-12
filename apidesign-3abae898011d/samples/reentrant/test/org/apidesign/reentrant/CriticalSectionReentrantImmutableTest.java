package org.apidesign.reentrant;

public class CriticalSectionReentrantImmutableTest extends CriticalSectionBase {
    @Override
    protected boolean reentrantJustOnce() {
        return true;
    }
    
    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionReentrantImmutable<Integer>();
    }
}