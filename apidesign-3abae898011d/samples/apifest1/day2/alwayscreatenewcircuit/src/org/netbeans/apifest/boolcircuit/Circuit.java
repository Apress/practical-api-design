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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 */
public abstract class Circuit {
    /** Creates a new instance of Circuits */
    protected Circuit() {
    }

    public static Circuit negate(Input input) {
        return new Negation(new Primitive(input));
    }
    
    @Deprecated
    public static Circuit negate(boolean input) {
        return new Negation(input);
    }
    
    public static Circuit negate(Circuit input) {
        return new Negation(input);
    }

    public static Circuit and(Input input1, Input input2) {
        return new And(new Primitive(input1), new Primitive(input2));
    }
    
    @Deprecated
    public static Circuit and(boolean input1, boolean input2) {
        return new And(input1, input2);
    }

    public static Circuit and(Circuit  input1, Input input2) {
        return new And(input1, new Primitive(input2));
    }
    
    @Deprecated
    public static Circuit and(Circuit  input1, boolean input2) {
        return new And(input1, new Primitive(input2));
    }

    public static Circuit and(Input input1, Circuit input2) {
        return new And(new Primitive(input1), input2);
    }
    
    @Deprecated
    public static Circuit and(boolean input1, Circuit input2) {
        return new And(new Primitive(input1), input2);
    }
    
    public static Circuit and(Circuit input1, Circuit input2) {
        return new And(input1, input2);
    }

    public static Circuit or(Input input1, Input input2) {
        return new Or(new Primitive(input1), new Primitive(input2));
    }
    
    @Deprecated
    public static Circuit or(boolean input1, boolean input2) {
        return new Or(input1, input2);
    }

    public static Circuit or(Circuit input1, Input input2) {
        return new Or(input1, new Primitive(input2));
    }
    
    @Deprecated
    public static Circuit or(Circuit input1, boolean input2) {
        return new Or(input1, new Primitive(input2));
    }

    public static Circuit or(Input input1, Circuit input2) {
        return new Or(new Primitive(input1), input2);
    }
    
    @Deprecated
    public static Circuit or(boolean input1, Circuit input2) {
        return new Or(new Primitive(input1), input2);
    }
    
    public static Circuit or(Circuit input1, Circuit input2) {
        return new Or(input1, input2);
    }
    
    
    public  final boolean output() {
        double v = value();
        if (v > 1 || v < 0) {
            throw new IllegalArgumentException();
        }
        return (v > 0) ? true : false;
    }
    
    private static final double check(double v) {
        if (v > 1 || v < 0) {
            throw new IllegalArgumentException();
        }
        return v;
    }
    
    
    public abstract double value();
    
    private final static class Primitive extends Circuit {
        private Input input;

        Primitive(boolean input) {
            this.input = new Input(input);
        }
        
        Primitive(Input input) {
            this.input = input;
        }
        
        

        public double value() {
            return input.getValue();
        }
    }
    
    private final static class Negation extends Circuit {
        private Circuit input;
        
        @Deprecated
        Negation(boolean input) {
            this.input = new Primitive(input);
        }
        
        Negation(Circuit input) {
            this.input = input;
        }
        

        public double value() {
            double x = input.value();            
            return check(1-x);
        }
    }
    
    private static class And extends Circuit {
        Circuit input1;
        Circuit input2;
                
        And(boolean input1, boolean input2) {
            this.input1 = new Primitive(input1);
            this.input2 = new Primitive(input2);
        }
        
        And(Circuit input1, Circuit input2) {
            this.input1 = input1;
            this.input2 = input2;
        }
        
        public double value() {
            double x = input1.value();
            double y = input2.value();            
            return check(x * y);
        }
    }
    
    private final static class Or extends And {        
        Or(boolean input1, boolean input2) {
            super(input1, input2);
        }
        
        Or(Circuit input1, Circuit input2) {
            super(input1, input2);
        }
        
        public double value() {
            double x = input1.value();
            double y = input2.value();
            
            return check(1 - (1 - x) * (1 - y));
        }        
    }
    
    public static class Input {
        private double value;
        
        public static Input valueOf(boolean initValue) {
            return new Input(initValue);
        }       

        public static Input valueOf(double initValue) {
            return new Input(initValue);
        }       
                        
        /** Creates a new instance of Input */        
        private Input(boolean input) {
            setValue(input);
        }
        
        private Input(double input) {
            setValue(input);
        }
        
        public double getValue() {
            return value;
        }
        
        public void setValue(double input) {
            value = input;
        }
        
        public void setValue(boolean input) {
            value = (input) ? 1 : 0;
        }        
    }    
}
