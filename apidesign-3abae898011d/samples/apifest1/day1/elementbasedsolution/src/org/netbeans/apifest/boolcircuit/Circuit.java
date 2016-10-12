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
// BEGIN: apifest.day1.elementbasedsolution.Circuit
public final class Circuit {
    private Circuit() {
    }
    
    public static Element and(final Element e1, final Element e2) {
        return new Element() {
            public boolean result() {
                return e1.result() && e2.result();
            }
        };
    }
    public static Element or(final Element e1, final Element e2) {
        return new Element() {
            public boolean result() {
                return e1.result() || e2.result();
            }
        };
    }

    public static Element not(final Element e1) {
        return new Element() {
            public boolean result() {
                return !e1.result();
            }
        };
    }
    
    public static Variable var() {
        return new Variable();
    }
    
    public static abstract class Element {
        private Element() {
        }
        
        public abstract boolean result();
    }
    
    public static final class Variable extends Element {
        private boolean value;
        
        public void assignValue(boolean b) {
            value = b;
        }

        public boolean result() {
            return value;
        }
    }
}
// END: apifest.day1.elementbasedsolution.Circuit
