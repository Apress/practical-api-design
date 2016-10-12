/*
 * Žluťoučký kůň je naše hříbátko.
 * and open the template in the editor.
 */

package org.apidesign.sidemeanings.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class FactorialTest {
    Factorial instance;

    @Before
    public void setUp() {
        instance = new Factorial();
    }

    @Test
    public void testFactorial3() {
        Assert.assertEquals(6, instance.factorial(3));
    }
    
    @Test
    public void testFactorial4() {
        Assert.assertEquals(24, instance.factorial(4));
    }
    
    @Test
    public void testFactorial5() {
        Assert.assertEquals(120, instance.factorial(5));
    }
}
