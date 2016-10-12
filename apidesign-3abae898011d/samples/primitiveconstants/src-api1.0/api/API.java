package api;

// BEGIN: theory.binary.constants.api
public abstract class API {
    public static final int VERSION = 1;

    protected API() {
        System.err.println("Initializing version " + VERSION);
        init(API.VERSION);
        System.err.println("Properly initialized: " + this);
    }

    protected abstract void init(int version) throws IllegalStateException;
}
// END: theory.binary.constants.api