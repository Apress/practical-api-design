package apifest;

import java.util.Arrays;
import java.util.Stack;
import junit.framework.TestCase;
import org.netbeans.apifest.boolcircuit.Circuit;
import org.netbeans.apifest.boolcircuit.CircuitFactory;
import org.netbeans.apifest.boolcircuit.Operation;


/** Write a test that works with version from day A and fails with version B.
 */
public class CircuitTest extends TestCase {
    public CircuitTest(String n) {
        super(n);
    }
    
    // no need to comment

    public void testSourceComp() throws Exception {
        Operation nand = new Operation() {
            public char evaluate(char i1, char i2) throws IllegalArgumentException {
               return i1 == '1' && i2 == '1' ? '0' : '1';
            }
        };
    }

    public void testSourceComp2() throws Exception {
        Operation nand = new Operation() {
            public char evaluate(char i1, char i2) throws IllegalArgumentException {
                evaluate(1, 2);
                return i1 == '1' && i2 == '1' ? '0' : '1';
            }
            // checker
            public char evaluate(double d1, double d2) {
                return '1';
            }
        };
    }
    
    public void testBinaryComp() throws Exception {
        Operation nand = new Operation() {
            public char evaluate(char i1, char i2) throws IllegalArgumentException {
               return i1 == '1' && i2 == '1' ? '0' : '1';
            }
        };
        Circuit cir = CircuitFactory.getBasicCircuit(nand);

        Stack<Character> s = new Stack<Character> ();
        s.addAll(Arrays.asList('0', '1'));
        
        assertEquals('1', cir.evaluate(s));
    }

}
