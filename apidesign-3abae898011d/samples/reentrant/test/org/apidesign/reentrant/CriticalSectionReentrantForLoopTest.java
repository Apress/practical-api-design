package org.apidesign.reentrant;

public class CriticalSectionReentrantForLoopTest extends CriticalSectionBase {
    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionReentrantForLoop<Integer>();
    }

    @Override
    protected boolean reentrantJustOnce() {
        return true;
    }
}