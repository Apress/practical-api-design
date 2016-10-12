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
    
    public static Circuit createNotCircuit(Circuit in) {
        return new NotCircuit(in);
    }
    
    public static Circuit createOrCircuit(Circuit in1, Circuit in2) {
        return new OrCircuit(in1, in2);
    }
    
    public static Circuit createAndCircuit(Circuit in1, Circuit in2) {
        return new AndCircuit(in1, in2);
    }
    
    private Circuit() {
        
    }
    
    public abstract boolean evaluate(Boolean ... inputs);
    
    abstract int inputs();
    
    private static class NotCircuit extends Circuit {

        private Circuit in;
        
        NotCircuit (Circuit in) {
            this.in = in;
        }
        
        int inputs() {
            return in == null? 1: in.inputs();
        }
        
        public boolean evaluate (Boolean ... inputs) {
            if (inputs == null || inputs.length != inputs()) { 
                throw new IllegalArgumentException();
            }
            Boolean o = inputs[0];
            if (in != null) { 
                return !in.evaluate(inputs);
            }
            else if (o != null) {
                return !o.booleanValue();
            }
            throw new IllegalArgumentException();
        }
    }
    
    private static class AndCircuit extends Circuit {
        private Circuit in1, in2;
        
        AndCircuit (Circuit in1, Circuit in2) {
            this.in1 = in1;
            this.in2 = in2;
        }
        
        int inputs() {
            return (in1 == null? 1: in1.inputs()) + (in2 == null? 1: in2.inputs()) ;
        }
        
        public boolean evaluate (Boolean ... inputs) {
            if (inputs == null || inputs.length != inputs()) { 
                throw new IllegalArgumentException();
            }
            boolean x1, x2;
            Boolean o1 = inputs[0];
            if (in1 != null) { 
                Boolean[] ins1 = new Boolean[in1.inputs()];
                System.arraycopy(inputs, 0, ins1, 0, in1.inputs());
                x1 = in1.evaluate(ins1);
            }
            else if (o1 != null) {
                x1 = o1.booleanValue();
            }
            else {
                throw new IllegalArgumentException();
            }
            Boolean o2 = inputs[in1 != null? in1.inputs(): 1];
            if (in2 != null) { 
                Boolean[] ins2 = new Boolean[in2.inputs()];
                System.arraycopy(inputs, in1 != null? in1.inputs(): 1, ins2, 0, in2.inputs());
                x2 = in2.evaluate(ins2);
            }
            else if (o2 != null) {
                x2 = o2.booleanValue();
            }
            else {
                throw new IllegalArgumentException();
            }
            return x1 && x2;
        }
    }
    
    private static class OrCircuit extends Circuit {
        private Circuit in1, in2;
        
        OrCircuit (Circuit in1, Circuit in2) {
            this.in1 = in1;
            this.in2 = in2;
        }
        
        int inputs() {
            return (in1 == null? 1: in1.inputs()) + (in2 == null? 1: in2.inputs()) ;
        }
        
        public boolean evaluate (Boolean ... inputs) {
            if (inputs == null || inputs.length != inputs()) { 
                throw new IllegalArgumentException();
            }
            boolean x1, x2;
            Boolean o1 = inputs[0];
            if (in1 != null) { 
                Boolean[] ins1 = new Boolean[in1.inputs()];
                System.arraycopy(inputs, 0, ins1, 0, in1.inputs());
                x1 = in1.evaluate(ins1);
            }
            else if (o1 != null) {
                x1 = o1.booleanValue();
            }
            else {
                throw new IllegalArgumentException();
            }
            Boolean o2 = inputs[in1 != null? in1.inputs(): 1];
            if (in2 != null) { 
                Boolean[] ins2 = new Boolean[in2.inputs()];
                System.arraycopy(inputs, in1 != null? in1.inputs(): 1, ins2, 0, in2.inputs());
                x2 = in2.evaluate(ins2);
            }
            else if (o2 != null) {
                x2 = o2.booleanValue();
            }
            else {
                throw new IllegalArgumentException();
            }
            return x1 || x2;
        }
    }
}
