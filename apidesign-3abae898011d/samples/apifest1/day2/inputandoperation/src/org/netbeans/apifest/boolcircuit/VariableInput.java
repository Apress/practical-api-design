/*
 * VariableInput.java
 *
 * Created on July 13, 2006, 3:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 * @author mkleint
 */
public final class VariableInput extends Input {
    
    private double value;
    
    /** Creates a new instance of VariableInput */
    VariableInput() {
        value = 0f;
    }
    
    public void setBooleanValue(boolean bool) {
        value = bool ? 1d : 0d;
    }
    
    public boolean getBooleanValue() {
        return value == 1d;
    }

    public double getRealValue() {
        return value;
    }
    
    public void setRealValue(double real) throws IllegalArgumentException {
        if (real < 0d || real > 1d) {
            throw new IllegalArgumentException();
        };
        value = real;
    }
    
}
