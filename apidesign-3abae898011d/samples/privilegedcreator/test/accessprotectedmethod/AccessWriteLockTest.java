package accessprotectedmethod;


public class AccessWriteLockTest {
    
    @org.junit.Test
    public void testCallToMyDocument() {
        MyDocument doc = new MyDocument();
        
        // following line does not compile as
        // writeLock() has protected access in AbstractDocument
        // doc.writeLock();
        
        doc.writeLockAccess();
    }
    
}
