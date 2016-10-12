/*
 * Operation.java
 *
 * Created on July 12, 2006, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 *
 */
public interface Operation {
    public char evaluate (char i1, char i2) throws IllegalArgumentException;
    public double evaluate (double i1, double i2) throws IllegalArgumentException;
    public final static Operation OR = new Or ();
    public final static Operation AND = new And ();
    public final static Operation NEG = new Neg ();
    
    
    static class And implements Operation {
        public char evaluate(char i1, char i2) throws IllegalArgumentException {
            if (i1 != '0' && i1 != '1') {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            if (i2 != '0' && i2 != '1') {
                throw new IllegalArgumentException ("Invalid input parameter: " + i2);
            }
            return i1 == '1' && i2 == '1' ? '1' : '0';
        }

        public double evaluate(double i1, double i2) throws IllegalArgumentException {
            if (i1 < 0 || i1 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            if (i2 < 0 || i2 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i2);
            }
            return i1 * i2;
        }
    }
    static class Or implements Operation {
        public char evaluate(char i1, char i2) throws IllegalArgumentException {
            if (i1 != '0' && i1 != '1') {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            if (i2 != '0' && i2 != '1') {
                throw new IllegalArgumentException ("Invalid input parameter: " + i2);
            }
            return i1 == '1' || i2 == '1' ? '1' : '0';
        }

        public double evaluate(double i1, double i2) throws IllegalArgumentException {
            if (i1 < 0 || i1 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            if (i2 < 0 || i2 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i2);
            }
            return 1 - (1 - i1) * (1 - i2);
        }
    }
    static class Neg implements Operation {
        public char evaluate(char i1, char i2) throws IllegalArgumentException {
            if (i1 != '0' && i1 != '1') {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            return i1 == '1' ? '0' : '1';
        }

        public double evaluate(double i1, double i2) throws IllegalArgumentException {
            if (i1 < 0 || i1 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i1);
            }
            if (i2 < 0 || i2 > 1) {
                throw new IllegalArgumentException ("Invalid input parameter: " + i2);
            }
            return 1 - i1;
        }
    }
}

