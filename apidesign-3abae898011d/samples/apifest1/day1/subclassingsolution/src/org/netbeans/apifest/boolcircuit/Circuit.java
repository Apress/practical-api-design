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


// BEGIN: apifest.day1.subclassingsolution
/** Usefull class for building your own circuits. 
 * 
 */
public abstract class Circuit extends Object {
    
    /** For your conveninece */   
    public static Circuit AND = new Circuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            if ( in.length != 2) {
                throw new IllegalArgumentException(
                    "Should have two parameters"
                );
            }
            return in[0] && in[1];
        }
        
    };
    
    public static Circuit OR = new Circuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            if ( in.length != 2) {
                throw new IllegalArgumentException(
                    "Should have two parameters"
                );
            }
            return in[0] || in[1];
        }
        
    };
    
    public static Circuit NOT = new Circuit() {

        @Override
        public boolean evaluate(boolean[] in) {
            if ( in.length != 1) {
                throw new IllegalArgumentException(
                    "Should have one parameter"
                );
            }
            return !in[0];
        }
        
    };
    
        
    /** Feel free to implement and don't hesitate to throw 
     * IllegalArgumentEception 
     */
    public abstract boolean evaluate(boolean... in);
               
}
// END: apifest.day1.subclassingsolution


