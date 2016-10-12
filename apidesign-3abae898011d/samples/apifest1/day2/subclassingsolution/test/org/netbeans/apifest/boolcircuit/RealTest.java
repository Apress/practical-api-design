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

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import junit.framework.TestCase;
import junit.framework.*;

/** This file contains the APIFest quest for day 2. Simply, turn the 
 * boolean circuit into circuit that can compute with double values from 0 to 1.
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
 *   <li>negation - neg(x) = 1 - x, this is correct extension as neg(false)=neg(0)=1-0=1=true
 *   <li>and - and(x,y) = x * y, again this is fine as and(true,true)=1*1=true and also
 *             and(false,true)=0*1=0=false
 *   <li>or - or(x,y) = 1 - (1 - x) * (1 - y) and this is also ok as
 *             or(false,false) = 1 - (1 - 0) * (1 - 0) = 1 - 1 = 0 = false
 *             or(true,false) = 1 - (1 - 1) * (1 - 0) = 1 - 0 * 1 = 1 = true
 * </ul>
 * <p>
 * However as the circuits with doubles are more rich than plain boolean circuits,
 * there is additional requirement to allow any user of your API to write its 
 * own "element" type. This is all going to be exercise in the tests bellow
 * which you are supposed to implement.
 */
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
     * Feed the same circuit with x1=false, x2=true, assert result is true
     *
     * Feed the same circuit with x1=0.0, x2=1.0, assert result is 1.0
     *
     * Feed the same circuit with x1=0.5, x2=0.5, assert result is 0.625
     *
     * Feed the same circuit with x1=0.0, x2=2.0, make sure it throws an exception
     */
    public void testX1andX2orNotX1() {
        FuzzyCircuit c = new CircuitSupport( 2 ) {

            @Override
            public boolean ev(boolean[] in) {                
                return OR.evaluate( AND.evaluate( in[0], in[1] ), NOT.evaluate( in[0] ) );
            }
            
            @Override
            public double ev(double[] in) {
                return OR.evaluate( AND.evaluate( in[0], in[1] ), NOT.evaluate( in[0] ) );
            }
            
        };
        
        assertFalse( c.evaluate(true, false) );
        assertTrue( c.evaluate(false, true) );
        assertEquals(c.evaluate(0.0, 1.0), 1.0, 0.0001);
        assertEquals(c.evaluate(0.5, 0.5), 0.625, 0.0001);
        try {
            c.evaluate(0.0, 2.0);
        }
        catch ( IllegalArgumentException e ) {
            return;
        }
        fail();
    }
    
    /** Ensure that one variable cannot be filled with two different values.
     * Create a circuit for x1 and x1. Make sure that for any usage of your
     * API that would not lead to x1 * x1 result, an exception is thrown.
     * For example if there was a way to feed the circuit with two different 
     * values 0.3 and 0.5 an exception is thrown indicating that this is 
     * improper use of the circuit.
     */
    public void testImproperUseOfTheCircuit() {
        FuzzyCircuit c = new CircuitSupport( 1 ) {

            @Override
            public boolean ev(boolean[] in) {                
                return AND.evaluate( in[0], in[0] );
            }
            
            @Override
            public double ev(double[] in) {
                return AND.evaluate( in[0], in[0] );
            }
            
        };
        
        // Empty array        
        IllegalArgumentException ex = null;        
        try {
            c.evaluate(new boolean[]{} );
        } catch ( IllegalArgumentException e) {
            ex = e ;
        }
        if ( ex == null ) {
            fail();
        }
        
        // Other sizes
        int MAX = 4;    // :-) This test obviously requires a nonsese. 
                        // For testing nonsese Integer.MAX_VALUE
        for( int i = 2; i < MAX ; i++ ) {
            double a[] = new double[i];
            ex = null; 
            try {
                c.evaluate( a );
            }
            catch ( IllegalArgumentException e) {
                ex = e ;
            }
            if ( ex == null ) {
                fail();
            }
        }
        
    }
    
    /** Write your own element type called "gte" that will have two inputs and one output.
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
        final FuzzyCircuit gte = new CircuitSupport( 2 ) {

            // Assumes true > false
            @Override
            public boolean ev(boolean[] in) {                
                return in[0] || ( !in[0] && in[1] ); // May be
            }
            
            @Override
            public double ev(double[] in) {
                return in[0] >= in[1] ? 1.0 : 0.0;
            }
                                  
        };
        
        FuzzyCircuit c = new CircuitSupport( 1 ) {

            // Assumes true > false
            @Override
            public boolean ev(boolean[] in) {                
                return gte.evaluate( AND.evaluate( in[0], NOT.evaluate( in[0] ) ), in[0]);
            }
            
            @Override
            public double ev(double[] in) {
                return gte.evaluate( AND.evaluate( in[0], NOT.evaluate( in[0] ) ), in[0]);
            }
                                  
        };
        
        
        assertEquals(c.evaluate(0.5), 0.0, 0.0001);
        assertEquals(c.evaluate(1.0), 0.0, 0.0001);
        assertEquals(c.evaluate(0.0), 1.0, 0.0001);
        
    }
    
       
    private abstract static class CircuitSupport extends FuzzyCircuit {
    
        private int paramCount;
        
        CircuitSupport( int paramCount ) {
            this.paramCount = paramCount;
        }
                
        abstract boolean ev( boolean in[] );
        abstract double ev( double in[] );
        
        public boolean evaluate(boolean[] in) {
            checkParams( paramCount, in );
            return ev( in );
        }
                        
        public double evaluate(double[] in) {
            checkParams( paramCount, in );
            return ev( in );
        }
        
    }
    
}
