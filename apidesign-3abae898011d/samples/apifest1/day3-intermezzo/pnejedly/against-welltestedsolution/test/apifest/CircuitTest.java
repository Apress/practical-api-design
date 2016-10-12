package apifest;

import junit.framework.TestCase;
import org.netbeans.apifest.boolcircuit.Circuit;


/** Write a test that works with version from day A and fails with version B.
 */
// BEGIN: apifest.day3.against-welltestedsolution
public class CircuitTest extends TestCase {
    public CircuitTest(String n) {
        super(n);
    }
    
    public void testClass() throws Exception {
        // OK, this is not fair as well.
        assertEquals("Created AND circuit", "AndCircuit", 
            getName(Circuit.createAndCircuit(null, null))
        );
        assertEquals("Created OR circuit", "OrCircuit", 
            getName(Circuit.createOrCircuit(null, null))
        );
    }
    
    private String getName(Object obj) {
        String base = obj.getClass().getName();
        int lastDot = base.lastIndexOf('.');
        int last = base.lastIndexOf('$');
        if (lastDot > last) last = lastDot;
        return base.substring(last+1);
    }
}
// END: apifest.day3.against-welltestedsolution
