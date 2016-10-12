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
 * A representation of a logic circuit.
 * It can only be constructed from an element net.
 * Usage:
 * <pre>
 *  Circuit c = Circuit.construct(
 *      Element.createOr(
 *          Element.createAnd(
 *              Element.createInput(0),
 *              Element.createInput(1)
 *          ),
 *          Element.createInput(2)
 *      )
 *  );
 *            
 *  boolean val = c.evaluate(false, true, false));
 * </pre>
 */
public class Circuit {
    Element root;
    int pins;
    
    private Circuit(Element elem) {
        root = elem;
        pins = 1 + root.maxInput();
    }
    
    /**
     * Evaluate output of the circuit for given inputs.
     * For general 
     */
    public boolean evaluate(Boolean ... inputs) throws UnstableException {
        if (inputs.length != pins) throw new IllegalArgumentException("Wrong number of inputs, " + pins + " expected.");
        double[] inp = new double[pins];
        for (int i=0; i<pins; i++) inp[i] = inputs[i] ? 1 : 0;
        double res = root.evaluate(inp);
        return res > 0.5;
    }

    public double evaluate(double ... inputs) throws UnstableException {
        if (inputs.length != pins) throw new IllegalArgumentException("Wrong number of inputs, " + pins + " expected.");
        
        double[] inp = new double[pins]; // defensive copy with a check
        for (int i=0; i<inputs.length; i++) {
            if (inputs[i] < 0.0 || inputs[i] > 1.0) {
                throw new IllegalArgumentException("Out of range, pin " + i + ", value=" + inputs[i]);
            }
            inp[i] = inputs[i];
        }
        
        return root.evaluate(inp);
    }
    
    /**
     * Creates a circuit from a preconstructed element net.
     *
     * @param a top-level element representing the logic net.
     * @return a circuit prepared for evaluating result for given inputs.
     * 
     */
    public static Circuit construct(Element elem) {
        return new Circuit(elem);
    }
    
}
