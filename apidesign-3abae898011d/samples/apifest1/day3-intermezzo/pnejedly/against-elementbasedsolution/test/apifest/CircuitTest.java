package apifest;

import junit.framework.TestCase;
import org.netbeans.apifest.boolcircuit.Circuit;


/** Write a test that works with version from day A and fails with version B.
 */
// BEGIN: apifest.day3.against-elementbasedsolution
public class CircuitTest extends TestCase {
    public CircuitTest(String n) {
        super(n);
    }
    /**
     * OK, elementbasedsolution looked bulletproof. Every code path 
     * was exactly the same as in the initial version. Except one.     
     */
    public void testEvaluateWithoutAssign() throws Exception {
        Circuit.Variable var = Circuit.var();
        Circuit.Element circuit = Circuit.not(var);

        assertTrue (circuit.result());
    }
}
// END: apifest.day3.against-elementbasedsolution


