/*
 * AndOperation.java
 *
 * Created on July 12, 2006, 2:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
final class AndOperation extends Operation {

    private Input input1;

    private Input input2;
    
    /** Creates a new instance of AndOperation */
    AndOperation(Input in1, Input in2) {
        input1 = in1;
        input2 = in2;
    }

    boolean performBooleanOperation() {
        return input1.getBooleanValue() && input2.getBooleanValue();
    }
    
}
