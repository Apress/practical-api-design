package apifest;

import junit.framework.TestCase;
import org.netbeans.apifest.boolcircuit.Circuit;


/** Write a test that works with version from day A and fails with version B.
 */
// BEGIN: apifest.day3.against-alwayscreatenewcircuit
public class CircuitTest extends TestCase {
    public CircuitTest(String n) {
        super(n);
    }
    
    public void testReallyUnrealistic() throws Exception {
        // OK, this is not fair, right?
        assertEquals(null, Circuit.or(false, false).getClass().
                getSuperclass().getSuperclass().getSuperclass());
    }
}
// END: apifest.day3.against-alwayscreatenewcircuit
