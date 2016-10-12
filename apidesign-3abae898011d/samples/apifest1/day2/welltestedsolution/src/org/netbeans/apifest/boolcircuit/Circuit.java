/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.apifest.boolcircuit;

/**
 */
public abstract class Circuit {
    
    /** Creates a simple Circuit that is associated with given input pin.
     * @since day2
     */
    public static Circuit input(int pin) {
        return new Input(pin);
    }
    
    /** Create circuit that inverts results of input circuit.
     */
    public static Circuit createNotCircuit(Circuit in) {
        return new Not(in);
    }
    
    public static Circuit createOrCircuit(Circuit in1, Circuit in2) {
        return new Binary(in1, in2, true);
    }
    
    public static Circuit createAndCircuit(Circuit in1, Circuit in2) {
        return new Binary(in1, in2, false);
    }
    
    public Circuit() {
        
    }
    
    public final boolean evaluate(Boolean ... inputs) {
        if (inputs == null) 
            throw new IllegalArgumentException();
        double[] vals = new double [inputs.length];
        for (int i = 0; i < inputs.length; i++) 
            vals[i] = inputs[i]? 1.0: 0.0;
        return evaluateFuzzy(vals) == 1.0;
    }
    
    public final double evaluateFuzzy(double ... inputs) {
        if (inputs == null) 
            throw new IllegalArgumentException();
        return doEvaluate(inputs);
    }
    
    protected abstract double doEvaluate(double ... inputs);
    
    public abstract int maxInputs();
    
    private interface OldEval {
        
        double doEvaluate1(int offset, double ... inputs);
    }
    
    private static class Input extends Circuit {
        
        int pin;
        
        Input(int pin) {
            this.pin = pin;
        }
        
        protected double doEvaluate(double ... inputs) {
            if (inputs == null || inputs.length < maxInputs()) 
                throw new IllegalArgumentException();
            
            return inputs[pin];
        }

        public  int maxInputs() {
            return pin + 1;
        }
        
    }
    
    private static class Not extends Circuit implements OldEval {

        private Circuit in;
        
        Not (Circuit in) {
            this.in = in;
        }
        
        public double doEvaluate(double ... inputs) {
            return doEvaluate1(0, inputs);
        }

        public double doEvaluate1(int i, double ... inputs) {
            if (in == null) {
                if (inputs == null || inputs.length < i + 1)
                    throw new IllegalArgumentException();
                return 1 - inputs[i];
            }
            
            if (in instanceof OldEval) {
                return 1 - ((OldEval)in).doEvaluate1(i, inputs[i]);
            }
            else {
                return 1 - in.evaluateFuzzy(inputs);
            }
        }

        public  int maxInputs() {
            return in != null? in.maxInputs(): 1;
        }
    }
    
    private static class Binary extends Circuit implements OldEval {

        private Circuit in1, in2;
        boolean or;
        
        Binary (Circuit in1, Circuit in2, boolean or) {
            this.in1 = in1;
            this.in2 = in2;
            this.or = or;
        }
        
        public double doEvaluate(double ... inputs) {
            return doEvaluate1(0, inputs);
        }

        public double doEvaluate1(int i, double ... inputs) {
            double x1, x2;
            if (in1 == null) {
                if (inputs == null || inputs.length < i + 1)
                    throw new IllegalArgumentException();
                x1 = inputs[i];
            } else {
                if (in1 instanceof OldEval) {
                    x1 = ((OldEval)in1).doEvaluate1(i, inputs);
                }
                else {
                    x1 = in1.evaluateFuzzy(inputs);
                }
            }
            if (in2 == null) {
                if (inputs == null || inputs.length < i + (in1 != null? in1.maxInputs(): 1) + 1)
                    throw new IllegalArgumentException();
                x2 = inputs[i + (in1 != null? in1.maxInputs(): 1)];
            } else {
                if (in2 instanceof OldEval) {
                    x2 = ((OldEval)in2).doEvaluate1(i + (in1 != null? in1.maxInputs(): 1), inputs);
                }
                else {
                    x2 = in2.evaluateFuzzy(inputs);
                }
            }
            return or? 1 - (1 - x1) * (1 - x2) : x1 * x2;
        }
        
        public  int maxInputs() {
            return (in1 != null? in1.maxInputs(): 1) + 
                    (in2 != null? in2.maxInputs(): 1) ;
        }
    }
}
