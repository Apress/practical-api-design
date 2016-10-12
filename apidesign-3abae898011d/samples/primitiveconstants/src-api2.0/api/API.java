package api;

public abstract class API {
    // BEGIN: theory.binary.constants.api2
    public static final int VERSION = 2;
    // END: theory.binary.constants.api2

    protected API() {
        System.err.println("Initializing version " + VERSION);
        init(API.VERSION);
        System.err.println("Properly initialized: " + this);
    }

    protected abstract void init(int version) throws IllegalStateException;
}
