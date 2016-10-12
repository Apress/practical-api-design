/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.codeinjection;

/** API class that can counts a count down.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @since 1.0
 */
// BEGIN: codeinjection.CountDown
public abstract class CountDown {
    CountDown() {
    }

    public static CountDown create(int initial) {
        return new CountDownImplV1(initial);
    }

    /** Decrements the counter */
    public abstract void down();
    /** @return true if the counter is 0 or less */
    public abstract boolean isDown();
}
// END: codeinjection.CountDown
