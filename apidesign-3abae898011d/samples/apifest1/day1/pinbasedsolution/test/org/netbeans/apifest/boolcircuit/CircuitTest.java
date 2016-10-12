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

import junit.framework.TestCase;
import junit.framework.*;

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

    // BEGIN: apifest.day1.pinbasedsolution.CircuitTest
    /** 
     * Create a circuit to evaluate x1 and x2 and then
     * verify that its result is false for input (false, true) and
     * it is true for input (true, true).
     */
    public void testX1andX2() throws Exception {
        Circuit c = Circuit.construct(
            Element.createAnd(
                Element.createInput(0),
                Element.createInput(1)
            )
        );
        
        assertFalse ("false AND true is false", c.evaluate(false, true));
        assertTrue ("true AND true is true", c.evaluate(true, true));
    }
    
    /** 
     * Create a circuit to evaluate (x1 and x2) or x3 and then
     * verify that its result is false for input (false, true, false) and
     * it is true for input (false, false, true).
     */
    public void testX1andX2orX3() throws Exception {
        Circuit c = Circuit.construct(
            Element.createOr(
                Element.createAnd(
                    Element.createInput(0),
                    Element.createInput(1)
                ),
                Element.createInput(2)
            )
        );
        
        assertFalse (
            "(false AND true) OR false is false", 
            c.evaluate(false, true, false)
        );
        assertTrue (
            "(false AND false) OR true is true", 
            c.evaluate(false, false, true)
        );
    }
    
    /** 
     * Create a circuit to evaluate (x1 or not(x1)) and then
     * verify that its result is true for all values of x1.
     */
    public void testAlwaysTrue() throws Exception {
        Circuit c = Circuit.construct(
            Element.createOr(
                Element.createInput(0),
                Element.createNot(Element.createInput(0))
            )
        );
        
        assertTrue ("tautology is true", c.evaluate(false));
        assertTrue ("tautology is true", c.evaluate(true));
    }
    // END: apifest.day1.pinbasedsolution.CircuitTest
}
