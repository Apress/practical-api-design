package org.apidesign.sidemeanings;

// BEGIN: sidemeanings.Mixed.Dirty
public abstract class MixedClass {
    private int counter;
    private int sum;

    protected MixedClass() {
        super();
    }

    public final int apiForClients() {
        int subclass = toBeImplementedBySubclass();
        sum += subclass;
        return sum / counter;
    }

    protected abstract int toBeImplementedBySubclass();

    protected final void toBeCalledBySubclass() {
        counter++;
    }
}
// END: sidemeanings.Mixed.Dirty
