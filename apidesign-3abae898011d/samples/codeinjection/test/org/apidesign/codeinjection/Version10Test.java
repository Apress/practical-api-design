/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.codeinjection;

import org.junit.Test;
import static org.junit.Assert.*;

/** Final count down test.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class Version10Test {

    public Version10Test() {
    }

    /** creates version 1.0 */
    private static CountDown create(int value) {
        return new CountDownImplV1(value);
    }

    // BEGIN: codeinjection.fourtimes
    @Test
    public void testDecrementFourTimes() {
        CountDown counter = create(4);
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertFalse("Not down yet", counter.isDown()); counter.down();
        assertTrue("Down now", counter.isDown());
    }
    // END: codeinjection.fourtimes
}
