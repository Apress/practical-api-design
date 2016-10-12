package org.apidesign.math.test;

import org.apidesign.math.Arithmetica;
import junit.framework.TestCase;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class ArithmeticaTest extends TestCase {
    
    public ArithmeticaTest(String testName) {
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

    // BEGIN: design.composition.arith.test
    public void testSumTwo() {
        Arithmetica instance = new Arithmetica();
        assertEquals("+", 5, instance.sumTwo(3, 2));
    }

    public void testSumAll() {
        Arithmetica instance = new Arithmetica();
        assertEquals("+", 6, instance.sumAll(3, 2, 1));
    }

    public void testSumRange() {
        Arithmetica instance = new Arithmetica();
        assertEquals("1+2+3=6", 6, instance.sumRange(1, 3));
        assertEquals("sum(1,10)=55", 55, instance.sumRange(1, 10));
        assertEquals("sum(1,1)=1", 1, instance.sumRange(1, 1));
        assertEquals("sum(10,1)=55", 55, instance.sumRange(10, 1));
    }
    // END: design.composition.arith.test

}
