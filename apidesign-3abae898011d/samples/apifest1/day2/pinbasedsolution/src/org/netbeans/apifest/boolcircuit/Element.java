package org.netbeans.apifest.boolcircuit;

/**
 * Representation of an element in the circuit.
 * The internal behaviour of the element is opaque to the API user, 
 * it can only be used as a building block for logical equations, 
 * using primitive operation factories and a factory for input 
 * pin representation.
 * Elements are chained to create the logical net. 
 * The inputs to the net are represented by the elements created 
 * by {@link #createInput(boolean[])} factory method.
 */
public abstract class Element {
        
    /** Creates a new instance of Element */
    private Element() {
    }
    
    /**
      */
    abstract double evaluate(double[] inputs);
    
    abstract int maxInput();
    
    /**
     * Creates an Element representing 2-input AND function.
     *
     */
    public static Element createAnd(
        final Element source1, final Element source2
    ) {
        return new Element() {
            double evaluate(double[] inputs) {
                return source1.evaluate(inputs) * source2.evaluate(inputs);
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
    public static Element createOr(
        final Element source1, final Element source2
    ) {
        return new Element() {
            double evaluate(double[] inputs) {
                double x = source1.evaluate(inputs);
                double y = source2.evaluate(inputs);
                return 1 - (1 - x)*(1-y);
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
            double evaluate(double[] inputs) {
                return 1 - source1.evaluate(inputs);
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
            double evaluate(double[] inputs) {
                return inputs[pin];
            }
            
            int maxInput() {
                return pin;
            }
        };
    }

    // BEGIN: apifest.day2.pinbasedsolution.FunctionFactory
    /**
     * Creates an Element with user-defined transfer function.
     */
    public static Element createGate(
        final Element source1, final Element source2, final Function function
    ) {
        return new Element() {
            double evaluate(double[] inputs) {
                double x = source1.evaluate(inputs);
                double y = source2.evaluate(inputs);
                double result = function.evaluate(x, y);
                if (result < 0.0 || result > 1.0) {
                    throw new InternalError("Illegal gate function");
                }
                return result;
            }
            
            int maxInput() {
                return Math.max(source1.maxInput(), source2.maxInput());
            }
        };
    }
    // END: apifest.day2.pinbasedsolution.FunctionFactory
}
