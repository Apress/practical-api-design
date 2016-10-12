package apifest;

import junit.framework.TestCase;
import org.netbeans.apifest.boolcircuit.Circuit;
import org.netbeans.apifest.boolcircuit.Operation;

/** Write a test that works with version from day A and fails with version B.
 */
// BEGIN: apifest.day3.against-inputandoperation.CircuitTest
public class CircuitTest extends TestCase {
    public CircuitTest(String n) {
        super(n);
    }
    
    public void testSourceCompatibility() throws Exception {
    }
    
    // One forgotten final and the dissaster is here.
    class MyCircuit extends Circuit {
        public double evaluateRealOperation(Operation op) {
            return 0;
        }
    }
}
// END: apifest.day3.against-inputandoperation.CircuitTest
