package org.apidesign.sidemeanings;

public abstract class PublicAbstract {
    PublicAbstract() {}

    public abstract void increment();

    public final void incrementTenTimes() {
        for (int i = 0; i < 10; i++) {
            increment();
        }
    }

    public static abstract class Dirty extends PublicAbstract {
        // BEGIN: sidemeanings.PublicAbstract.Dirty
        public abstract void increment();
        // END: sidemeanings.PublicAbstract.Dirty
    }
    
    
    public static abstract class Clean extends PublicAbstract {
        // BEGIN: sidemeanings.PublicAbstract.Clean
        public final void increment() {
            overridableIncrement();
        }
        protected abstract void overridableIncrement();
        // END: sidemeanings.PublicAbstract.Clean
    }
}
