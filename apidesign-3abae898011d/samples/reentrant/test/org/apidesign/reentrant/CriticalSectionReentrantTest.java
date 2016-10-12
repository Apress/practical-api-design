package org.apidesign.reentrant;

public class CriticalSectionReentrantTest extends CriticalSectionBase {
    @Override
    protected CriticalSection<Integer> create() {
        return new CriticalSectionReentrant<Integer>();
    }
}
