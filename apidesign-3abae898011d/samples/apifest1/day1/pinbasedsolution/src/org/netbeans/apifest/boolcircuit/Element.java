/*
 * Element.java
 *
 * Created on 12. červenec 2006, 14:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 * Representation of an element in the circuit.
 * The internal behaviour of the element is opaque to the API user, it can only
 * be used as a building block for logical equations, using primitive operation
 * factories and a factory for input pin representation.
 * Elements are chained to create the logical net. The inputs to the net are
 * represented by the elements created by {@link #createInput(boolean[])}
 * factory method.
 */
public abstract class Element {
        
    /** Creates a new instance of Element */
    private Element() {
    }
    
    /**
      */
    abstract boolean evaluate(boolean[] inputs);
    
    abstract int maxInput();
    
    /**
     * Creates an Element representing 2-input AND function.
     *
     */
    public static Element createAnd(final Element source1, final Element source2) {
        return new Element() {
            boolean evaluate(boolean[] inputs) {
                return source1.evaluate(inputs) & source2.evaluate(inputs);
            }
            
            int maxInput() {
                return Math.max(source1.maxInput(), source2.maxInput());
            }
        };
    }

    /**
     * Creates an Element representing 2-input OR function.
     *
     */
    public static Element createOr(final Element source1, final Element source2) {
        return new Element() {
            boolean evaluate(boolean[] inputs) {
                return source1.evaluate(inputs) | source2.evaluate(inputs);
            }
            
            int maxInput() {
                return Math.max(source1.maxInput(), source2.maxInput());
            }
        };
    }
    
    /**
     * Creates an Element representing negation.
     *
     */
    public static Element createNot(final Element source1) {
        return new Element() {
            boolean evaluate(boolean[] inputs) {
                return !source1.evaluate(inputs);
            }
            
            int maxInput() {
                return source1.maxInput();
            }
        };
    }
    
    /**
     * Creates an Element representing input to the logical net.
     *
     */
    public static Element createInput(final int pin) {
        return new Element() {
            boolean evaluate(boolean[] inputs) {
                return inputs[pin];
            }
            
            int maxInput() {
                return pin;
            }
        };
    }
    
}
