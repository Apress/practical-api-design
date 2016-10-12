/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.apifest.boolcircuit;

import java.util.Arrays;
import java.util.Stack;
import junit.framework.TestCase;

/** The initial quest for this APIFest is to create an API for boolean 
 * circuits. Such API shall be able to compose a boolean circuit from
 * basic elements and evaluate the result given initial values for 
 * input variables.
 * <p>
 * The basic elements include:
 * <ul>
 *   <li>negation - has one input and one output and changes 0 on input to 
 *          on output 1 and 1 to 0
 *   <li>and - has two inputs and one output. The output is 1 only if both 
 *          inputs are 1, otherwise it is 0
 *   <li>or - has two inputs and one output. The output is 1 always, except
 *          in the case when both inputs are 0
 * </ul>
 *
 * <p>
 * The boolean circuit can be used to represent boolean formulas and compute
 * the results for certain values of its inputs. The individual tasks described
 * as tests bellow.
 *
 * <p>
 * Links of interest:
 * <ul>
 *   <li><a href="http://en.wikipedia.org/wiki/Truth_table">Truth table</a>
 *   <li><a href="http://en.wikipedia.org/wiki/Tautology_(logic)">Taugology</a>
 * </ul>
 */
public class CircuitTest extends TestCase {
    static {
        // your code shall run without any permissions
    }
    
    public CircuitTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /** 
     * Create a circuit to evaluate x1 and x2 and then
     * verify that its result is false for input (false, true) and
     * it is true for input (true, true).
     */
    public void testX1andX2() {
        Stack<Character> s = new Stack<Character> ();
        s.addAll(Arrays.asList('1', '1'));
        assertEquals("'1' for '11' input.", '1', CircuitFactory.getBasicCircuit(Operation.AND).evaluate(s));
        s.addAll(Arrays.asList('1', '0'));
        assertEquals("'0' for '10' input.", '0', CircuitFactory.getBasicCircuit(Operation.AND).evaluate(s));
    }
    
    /** 
     * Create a circuit to evaluate (x1 and x2) or x3 and then
     * verify that its result is false for input (false, true, false) and
     * it is true for input (false, false, true).
     */
    public void testX1andX2orX3() {
        Stack<Character> s = new Stack<Character> ();
        s.addAll(Arrays.asList('0', '1', '0'));
        assertEquals("'0' for '010' input.", '0', CircuitFactory.join(CircuitFactory.getTrivialCircuit(), CircuitFactory.getBasicCircuit(Operation.OR), Operation.AND).evaluate(s));
        s.addAll(Arrays.asList('0', '0', '1'));
        assertEquals("'1' for '001' input.", '1', CircuitFactory.join(CircuitFactory.getTrivialCircuit(), CircuitFactory.getBasicCircuit(Operation.OR), Operation.AND).evaluate(s));
    }
    /** 
     * Create a circuit to evaluate (x1 or not(x1)) and then
     * verify that its result is true for all values of x1.
     */
    public void testAlwaysTrue() {
        Circuit alwaysTrue = CircuitFactory.join(CircuitFactory.getTrivialCircuit(), CircuitFactory.getBasicCircuit(Operation.NEG), Operation.OR);
        Stack<Character> s = new Stack<Character> ();
        s.addAll(Arrays.asList('0', '0'));
        assertEquals ("'1' for '00'", '1', alwaysTrue.evaluate(s));
        s.addAll(Arrays.asList('1', '1'));
        assertEquals ("'1' for '11'", '1', alwaysTrue.evaluate(s));
    }
    
}
