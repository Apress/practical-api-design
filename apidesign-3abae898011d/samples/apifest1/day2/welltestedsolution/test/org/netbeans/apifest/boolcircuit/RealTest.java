package org.netbeans.apifest.boolcircuit;

import junit.framework.TestCase;
import junit.framework.*;
import org.netbeans.apifest.custom.Gte;

/** This file contains the APIFest quest for day 2. Simply, turn the 
 * boolean circuit into circuit that can compute with double 
 * values from 0 to 1.
 * <p>
 * This means that where ever a boolean was used to represent input or 
 * output values, one can now use any double number from >= 0 and <= 1.
 * Still, to support backward compatibility, the operations with booleans
 * has to be kept available and have to work. In fact False shall be 
 * treated as 0 and True as 1.
 * <p>
 * The basic elements has to be modified to work on doubles in the following
 * way:
 * <ul>
 *   <li>negation - neg(x) = 1 - x
 *   <li>and - and(x,y) = x * y
 *   <li>or - or(x,y) = 1 - (1 - x) * (1 - y)
 * </ul>
 * <p>
 * However as the circuits with doubles are more rich than plain 
 * boolean circuits, there is additional requirement to allow any user 
 * of your API to write its own "element" type. This is all going to 
 * be exercise in the tests bellow
 * which you are supposed to implement.
 */
// BEGIN: apifest.day2.welltestedsolution.RealTest
public class RealTest extends TestCase {
    static {
        // your code shall run without any permissions
    }
    
    public RealTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /** First of all create a circuit which will evaluate
     * expression (X1 and X2) or not(x1). Hold the circuit
     * in some variable.
     *
     * Feed this circuit with x1=true, x2=false, assert result is false
     *
     * Feed the same circuit with x1=false, x2=true, assert result is 
     * true
     *
     * Feed the same circuit with x1=0.0, x2=1.0, assert result is 1.0
     *
     * Feed the same circuit with x1=0.5, x2=0.5, assert result is 0.625
     *
     * Feed the same circuit with x1=0.0, x2=2.0
     * , make sure it throws an exception
     */
    public void testX1andX2orNotX1() {
        Circuit c = Circuit.createOrCircuit(
            Circuit.createAndCircuit(Circuit.input(0), 
            Circuit.input(1)),
            Circuit.createNotCircuit(Circuit.input(0))
        );
        assertFalse("true, false", c.evaluate(true, false));
        assertTrue("false, true", c.evaluate(false, true));
        assertEquals("0.0, 1.0", 1.0, c.evaluateFuzzy(0.0, 1.0), 0.0);
    }
    
    /** Ensure that one variable cannot be filled with two different 
     * values. Create a circuit for x1 and x1. Make sure that for any 
     * usage of your API that would not lead to x1 * x1 result, an 
     * exception is thrown. For example if there was a way to feed the 
     * circuit with two different values 0.3 and 0.5 an exception is 
     * thrown indicating that this is improper use of the circuit.
     */
    public void testImproperUseOfTheCircuit() {
        // does not apply
        
        Circuit x1 = Circuit.input(0);
        Circuit c = Circuit.createOrCircuit(x1, x1);
        assertTrue("x1 or x1", c.evaluate(true));
        assertFalse("x1 or x1", c.evaluate(false));
        try {
            c.evaluate();
            fail("x1 or x1 with wrong params");
        } catch (IllegalArgumentException iea) {
            //expected
        }
        // the same with two instances of pin
        c = Circuit.createOrCircuit(Circuit.input(0), Circuit.input(0));
        assertTrue("x1 or x1", c.evaluate(true));
        assertTrue("x1 or x1", c.evaluate(true, false));
        assertTrue("x1 or x1", c.evaluate(true, true));
        assertFalse("x1 or x1", c.evaluate(false));
        try {
            c.evaluate();
            fail("x1 or x1 with wrong params");
        } catch (IllegalArgumentException iea) {
            //expected
        }
    }
    
    /** Write your own element type called "gte" that 
     * will have two inputs and one output.
     * The output value will be 1 if x1 >= x2 and 0 otherwise. 
     * 
     * Create 
     * circuit for following expression: (x1 and not(x1)) gte x1
     *
     * Feed the circuit with 0.5 and verify the result is 0
     *
     * Feed the same circuit with 1 and verify the result is 0
     *
     * Feed the same circuit with 0 and verify the result is 1
     */
    public void testGreaterThanEqualElement() {
        Circuit gte = new Gte(Circuit.createAndCircuit(
            Circuit.input(0),
            Circuit.createNotCircuit(Circuit.input(0))),
            Circuit.input(0)
        );
        assertEquals("0.5", 0.0, gte.evaluateFuzzy(0.5), 0.0);
        assertEquals("1.0", 0.0, gte.evaluateFuzzy(1.0), 0.0);
        assertEquals("0.0", 1.0, gte.evaluateFuzzy(0.0), 0.0);
        
    }
    
    public void testSilly() {
        // (x1 and not x2) or x3
        Circuit c = Circuit.createOrCircuit(
            Circuit.createAndCircuit(
            null,
            Circuit.createNotCircuit(null)),
            null
        );
        assertEquals("1 1 1", 1.0, c.evaluateFuzzy(1.0, 1.0, 1.0), 0.0);
        assertEquals("1 1 0", 0.0, c.evaluateFuzzy(1.0, 1.0, 0.0), 0.0);
        assertEquals("1 0 1", 1.0, c.evaluateFuzzy(1.0, 0.0, 1.0), 0.0);
        assertEquals("1 0 0", 1.0, c.evaluateFuzzy(1.0, 0.0, 0.0), 0.0);
        assertEquals("0 1 1", 1.0, c.evaluateFuzzy(0.0, 1.0, 1.0), 0.0);
        assertEquals("0 1 0", 0.0, c.evaluateFuzzy(0.0, 1.0, 0.0), 0.0);
        assertEquals("0 0 1", 1.0, c.evaluateFuzzy(0.0, 0.0, 1.0), 0.0);
        assertEquals("0 0 0", 0.0, c.evaluateFuzzy(0.0, 0.0, 0.0), 0.0);
    }
}
// END: apifest.day2.welltestedsolution.RealTest

