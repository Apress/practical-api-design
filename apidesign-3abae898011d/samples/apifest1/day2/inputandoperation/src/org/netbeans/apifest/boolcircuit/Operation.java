/*
 * Operation.java
 *
 * Created on July 12, 2006, 2:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
public abstract class Operation {

    private boolean used;
    
    /** Creates a new instance of Operation */
    protected Operation() {
    }
    
    public abstract boolean performBooleanOperation();
    
    final void markOperationAsUsed() {
        used = true;
    }
    
    final boolean isUsed() {
        return used;
    }

    public abstract double performRealOperation();
    
}
