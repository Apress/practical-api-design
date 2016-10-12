package org.apidesign.reentrant;

import java.util.concurrent.locks.ReentrantLock;

final class NonReentrantLock extends ReentrantLock {
    @Override
    public void lock() {
        if (isHeldByCurrentThread()) {
            throw new IllegalStateException("Attempt to reentrant lock");
        }
        super.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (isHeldByCurrentThread()) {
            throw new IllegalStateException("Attempt to reentrant lock");
        }
        super.lockInterruptibly();
    }
    
}
