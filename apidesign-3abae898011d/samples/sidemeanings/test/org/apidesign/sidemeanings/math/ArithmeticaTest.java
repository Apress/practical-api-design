/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.sidemeanings.math;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class ArithmeticaTest {
    Arithmetica instance;

    public ArithmeticaTest() {
    }

    @Before
    public void setUp() {
        instance = Arithmetica.create();
    }

    @Test
    public void testSumTwo() {
        assertEquals("+", 5, instance.sumTwo(3, 2));
    }

    @Test
    public void testSumAll() {
        assertEquals("+", 6, instance.sumAll(3, 2, 1));
    }

    @Test
    public void testSumRange() {
        assertEquals("1+2+3=6", 6, instance.sumRange(1, 3));
        assertEquals("sum(1,10)=55", 55, instance.sumRange(1, 10));
        assertEquals("sum(1,1)=1", 1, instance.sumRange(1, 1));
        assertEquals("sum(10,1)=55", 55, instance.sumRange(10, 1));
    }
}