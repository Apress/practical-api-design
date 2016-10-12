/*
 * OperationInput.java
 *
 * Created on July 12, 2006, 2:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
final class OperationInput extends Input {
    private Operation operation;
    /** Creates a new instance of OperationInput */
    public OperationInput(Operation oper) {
        operation = oper;
    }

    public boolean getBooleanValue() {
        return operation.performBooleanOperation();
    }
    
}
