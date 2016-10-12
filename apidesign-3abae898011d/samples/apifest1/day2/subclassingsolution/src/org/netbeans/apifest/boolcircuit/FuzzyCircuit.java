/*
 * FuzzyCircuit.java
 *
 * Created on July 13, 2006, 2:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

public abstract class FuzzyCircuit extends Circuit {
    
    /** Feel free to implement and don't hesitate to throw IllegalArgumentEception 
     */
    public abstract double evaluate(double... in);
    
    public static final void checkParams( int expectedLenght, boolean... in  ) {
        
        // :-) in real world add a test for null too
        
        if (expectedLenght != in.length) {
            
            // :-) Probably unnecessary unless this is a competition 
            switch ( expectedLenght ) {
                case 1:
                    throw new IllegalArgumentException("Should have one parameter");
                case 2:
                    throw new IllegalArgumentException("Should have two parameters");
                default:
                    throw new IllegalArgumentException("Wrong number of parameters!");
            }                       
        }
    }
    
    public static final void checkParams( int expectedLenght, double ... in ) {
        if ( in == null ) {
            throw new IllegalArgumentException( "Parameter in must not be null!");
        }
        if (expectedLenght != in.length) {
            throw new IllegalArgumentException("Wrong number of parameters!");
        }
        
        for( int i = 0; i < in.length; i++ ) {
            if ( in[i] < 0.0 || in[i] > 1.0 ) {
                throw new IllegalArgumentException( 
                        "All params have to be in the range <0.0, 1.0>! " +
                        "param at index " + i + " is " + in[i]);
            }
        }
        
    }
          
}
