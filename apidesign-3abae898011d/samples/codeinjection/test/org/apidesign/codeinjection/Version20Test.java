/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.codeinjection;

import org.apidesign.codeinjection.spi.CountDownExtender;
import org.junit.Test;
import org.netbeans.junit.MockServices;
import static org.junit.Assert.*;

/** Final count down test.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class Version20Test {

    public Version20Test() {
    }

    /** creates version 2.0 */
    private static CountDown create(int value) {
        return new CountDownImplV2(value);
    }

    @Test
    public void testDecrementFourTimes() {
        MockServices.setServices();
        CountDown counter = create(4);
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertTrue("Down now", counter.isDown());
    }

    // BEGIN: codeinjection.twice
    @Test
    public void testDecrementTwoTimesEnough() {
        MockServices.setServices(DecrementByTwo.class);
        CountDown counter = create(4);
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertTrue("Two Down is enough", counter.isDown());
    }

    public static final class DecrementByTwo implements CountDownExtender {
        public int decrement(int value) {
            return value - 2;
        }
    }
    // END: codeinjection.twice
}
