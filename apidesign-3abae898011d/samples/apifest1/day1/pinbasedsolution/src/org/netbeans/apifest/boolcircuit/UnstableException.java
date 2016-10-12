/*
 * UnstableException.java
 *
 * Created on 12. červenec 2006, 14:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.apifest.boolcircuit;

/**
 * Exception representing that the circuit is unstable for given
 * input configuration. It can happen in case there is backward loop
 * in the logic.
 *
 */
public class UnstableException extends Exception {
    
    /** Creates a new instance of UnstableException */
    public UnstableException() {
    }
    
}
