/*
 * NotOperation.java
 *
 * Created on July 12, 2006, 2:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
final class NotOperation extends Operation {

    private Input input;
    
    /** Creates a new instance of NotOperation */
    NotOperation(Input input) {
        this.input = input;
    }

    public boolean performBooleanOperation() {
        return !input.getBooleanValue();
    }

    public double performRealOperation() {
        return 1f - input.getRealValue();
    }
    
    
}
