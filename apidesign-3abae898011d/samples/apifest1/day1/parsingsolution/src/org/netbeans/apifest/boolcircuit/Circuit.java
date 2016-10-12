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

// BEGIN: apifest.day1.parsingsolution.Circuit
/**
 * Usage:
 * First method parse must be called with valid logical expression on 
 * input.
 * If it returns zero then it is possible to call method evaluate with 
 * array of input values as parameter. Method evaluate can be invoked 
 * many time with different input values.
 * Method parse can be called anytime to change logical expression.
 */
public class Circuit {
    
    /** Parses logical expression
     * @param string representation of logical expression
     * Valid tokens:
     * Input values are represented by x and number starting from 1 eg.: x1
     * AND, NOT, OR and brackets '(',')' can be used.
     * Example of valid expression: x1 AND x2
     * @return 0 when input expression is validated and parsed. 
     * Return nonzero value otherwise.
     */
    public int parse(String expression) {
        return 0;
    }
    
    /** Evaluate logical expression
     * @param array of boolean input values. Size of array must
     * correspond to number of variables used in expression
     * If size of array is bigger then only first N values are used 
     * to evaluate expression. Remaining values are ignored.
     * If size of array is smaller then IllegalArgumentException is thrown.
     * If no expression is set by method parse then 
     * IllegalStateException is thrown.
     */
    public boolean evaluate(boolean [] x) {
        return true;
        
    }
    
}
// END: apifest.day1.parsingsolution.Circuit

