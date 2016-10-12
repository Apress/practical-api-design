/*
 * Žluťoučký kůň je naše hříbátko.
 * and open the template in the editor.
 */

package org.apidesign.math.test;

import junit.framework.TestCase;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class FactorialTest extends TestCase {
    
    public FactorialTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testFactorial3() {
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals(6, Factorial.factorial(3));
    }
    
    public void testFactorial4() {
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals(24, Factorial.factorial(4));
    }
    
    public void testFactorial5() {
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals(120, Factorial.factorial(5));
    }
}
