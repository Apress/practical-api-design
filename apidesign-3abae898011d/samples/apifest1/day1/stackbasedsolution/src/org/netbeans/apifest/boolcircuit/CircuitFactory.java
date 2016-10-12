/*
 * CircuitFactory.java
 *
 * Created on July 12, 2006, 3:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

import java.util.Stack;

/**
 *
 */
public class CircuitFactory {
    
    /** Creates a new instance of CircuitFactory */
    private  CircuitFactory() {
    }
    
    public final static Circuit join (final Circuit c1, final Circuit c2, final Operation op) {
        return new Circuit () {
            public char evaluate(Stack input) throws IllegalArgumentException {
                return op.evaluate(c1.evaluate(input), c2.evaluate(input));
            }
        };
    }
    
    public final static Circuit getBasicCircuit (final Operation op) {
        return new Circuit () {
            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
                // special handling of unary oparation
                if (op instanceof Operation.Neg) {
                    return op.evaluate(input.pop(), input.peek());
                } else {
                    return op.evaluate(input.pop(), input.pop());
                }
            }
        };
    }
    public final static Circuit getTrivialCircuit () {
        return new Circuit () {
            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
                return input.peek();
            }
        };
    }
}
