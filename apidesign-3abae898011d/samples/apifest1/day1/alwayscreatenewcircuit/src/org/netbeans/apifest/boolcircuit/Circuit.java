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
    /** Creates a new instance of Circuits */
    private Circuit() {
    }

    public static Circuit negate(boolean input) {
        return new Negation(input);
    }

    public static Circuit negate(Circuit input) {
        return new Negation(input);
    }

    public static Circuit and(boolean input1, boolean input2) {
        return new And(input1, input2);
    }

    public static Circuit and(Circuit  input1, boolean input2) {
        return new And(input1, new Primitive(input2));
    }

    public static Circuit and(boolean input1, Circuit input2) {
        return new And(new Primitive(input1), input2);
    }

    public static Circuit and(Circuit input1, Circuit input2) {
        return new And(input1, input2);
    }

    public static Circuit or(boolean input1, boolean input2) {
        return new Or(input1, input2);
    }

    public static Circuit or(Circuit input1, boolean input2) {
        return new Or(input1, new Primitive(input2));
    }

    public static Circuit or(boolean input1, Circuit input2) {
        return new Or(new Primitive(input1), input2);
    }

    public static Circuit or(Circuit input1, Circuit input2) {
        return new Or(input1, input2);
    }


    public abstract boolean output();

    private final static class Primitive extends Circuit {
        private boolean input;

        Primitive(boolean input) {
            this.input = input;
        }

        public final boolean output() {
            return input;
        }
    }

    private final static class Negation extends Circuit {
        private Circuit input;

        Negation(boolean input) {
            this.input = new Primitive(input);
        }

        Negation(Circuit input) {
            this.input = input;
        }

        public final boolean output() {
            return !(input.output());
        }
    }

    private final static class And extends Circuit {
        private Circuit input1;
        private Circuit input2;


        And(boolean input1, boolean input2) {
            this.input1 = new Primitive(input1);
            this.input2 = new Primitive(input2);
        }

        And(Circuit input1, Circuit input2) {
            this.input1 = input1;
            this.input2 = input2;
        }

        public final boolean output() {
            return input1.output() && input2.output();
        }
    }

    private final static class Or extends Circuit {
        private Circuit input1;
        private Circuit input2;


        Or(boolean input1, boolean input2) {
            this.input1 = new Primitive(input1);
            this.input2 = new Primitive(input2);
        }

        Or(Circuit input1, Circuit input2) {
            this.input1 = input1;
            this.input2 = input2;
        }

        public final boolean output() {
            return input1.output() || input2.output();
        }
    }
  }