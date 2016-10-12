package org.apidesign.misuse;

// BEGIN: misuse.Connection
public interface Connection {
    public Savepoint setSavepoint();
    
    public interface Savepoint {
        public void rollback();
        // and other useful operations
    }
}
// END: misuse.Connection
