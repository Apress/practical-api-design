/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.cloneproblem;

import java.util.Date;
import junit.framework.TestCase;

/** The test as would be written by the (not paranoiac) API author.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class IntervalTest extends TestCase {
    
    public IntervalTest(String testName) {
        super(testName);
    }

    // BEGIN: interval.test
    public void testOneSecondInterval() {
        Date now = new Date();
        Date later = new Date(now.getTime() + 1000);
        
        Interval interval = new Interval(now, later);
        assertEquals("1s", 1000, interval.getLength());
    }
    // END: interval.test

    public void testLaterCantBeSooner() {
        Date now = new Date();
        Date later = new Date(now.getTime() - 1000);

        try {
            Interval interval = new Interval(now, later);
            fail("Shall throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    public void testDoesNotThrowNPEOnSecondArg() {
        Date now = new Date();
        try {
            Interval interval = new Interval(now, null);
            fail("Shall throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    public void testDoesNotThrowNPEOnFirstArg() {
        Date now = new Date();
        try {
            Interval interval = new Interval(null, now);
            fail("Shall throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
}
