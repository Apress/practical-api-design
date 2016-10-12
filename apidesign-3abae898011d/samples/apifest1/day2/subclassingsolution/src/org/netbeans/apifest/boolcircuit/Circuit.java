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


/** Usefull class for building your own circuits. 
 * </br>
 *  Notice that this API nobly generously permits using operator overloading
 *  </br>
 *  :-) Sometimes when you find an unfixable bug. The best thing to do is to 
 *  make it a fature.
 * 
 */
public abstract class Circuit extends Object {
    
    /** For your conveninece */   
    public static FuzzyCircuit AND = new FuzzyCircuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            checkParams( 2, in );
            return in[0] && in[1];
        }

        public double evaluate(double[] in) {
            checkParams( 2, in );
            return in[0] * in[1];
        }
        
    };
    
    /** For your conveninece */
    public static FuzzyCircuit OR = new FuzzyCircuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            checkParams( 2, in );            
            return in[0] || in[1];
        }

        @Override
        public double evaluate(double[] in) {
            checkParams( 2, in );            
            return 1 - (1 - in[0]) * (1 - in[1]);
        }
    };
    
    
    /** For your conveninece */
    public static FuzzyCircuit NOT = new FuzzyCircuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            checkParams( 1, in );            
            return !in[0];
        }

        @Override
        public double evaluate(double[] in) {
            checkParams( 1, in );            
            return 1 - in[0];
        }
        
        
    };
            
    /** Feel free to implement and don't hesitate to throw IllegalArgumentEception 
     */
    public abstract boolean evaluate(boolean... in);
        
    
    
}
