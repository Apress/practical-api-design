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
    
    public final static Circuit2 join (final Circuit2 c1, final Circuit2 c2, final Operation op) {
        return new Circuit2 () {
            public char evaluate(Stack input) throws IllegalArgumentException {
                return op.evaluate(c1.evaluate(input), c2.evaluate(input));
            }
            public double evaluate(double ... input) throws IllegalArgumentException {
                return op.evaluate(c1.evaluate(input), c2.evaluate(input));
            }
        };
    }
    
    public final static Circuit2 getBasicCircuit (final Operation op) {
        return new Circuit2 () {
            public double evaluate(double... input) throws IllegalArgumentException {
                if (input == null || input.length == 1) {
                    throw new IllegalArgumentException ("Invalid input array.");
                }
                return op.evaluate(input[0], input[1]);
            }

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
//    public final static Circuit2 getBasicCircuit (final Operation op, final int posX, final int posY) {
//        return new Circuit2 () {
//            public double evaluate(double... input) throws IllegalArgumentException {
//                return op.evaluate(input[posX], input[posY]);
//            }
//
//            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
//                return getBasicCircuit (op).evaluate(input);
//            }
//        };
//    }
//    public final static Circuit2 getBasicCircuit (final Operation op, final Circuit2 c1, final Circuit2 c2) {
//        return new Circuit2 () {
//            public double evaluate(double... input) throws IllegalArgumentException {
//                return op.evaluate(c2.evaluate(input), c2.evaluate(input));
//            }
//
//            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
//                return getBasicCircuit (op).evaluate(input);
//            }
//        };
//    }
    public final static Circuit getTrivialCircuit () {
        return new Circuit () {
            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
                return input.peek();
            }
        };
    }
    public final static Circuit2 getTrivialCircuit (final int posX) {
        return new Circuit2 () {
            public double evaluate(double... input) throws IllegalArgumentException {
                return input[posX];
            }

            public char evaluate(Stack<Character> input) throws IllegalArgumentException {
                return getTrivialCircuit ().evaluate(input);
            }
        };
    }
}
