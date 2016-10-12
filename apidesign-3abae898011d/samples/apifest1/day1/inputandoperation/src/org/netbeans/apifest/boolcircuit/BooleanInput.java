/*
 * BooleanInput.java
 *
 * Created on July 12, 2006, 2:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
final class BooleanInput extends Input {

    private boolean value;
    
    /** Creates a new instance of BooleanInput */
    public BooleanInput(boolean val) {
        value = val;
    }

    public boolean getBooleanValue() {
        return value;
    }
    
}
