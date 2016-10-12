/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.codeinjection;

/** The implementation of {@link CountDown} as of version 1.0.
 * The {@link #down()} method always decrements by one.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @since 1.0
 */
final class CountDownImplV1 extends CountDown {
    CountDownImplV1(int initial) {
        this.cnt = initial;
    }
    
// BEGIN: codeinjection.v1
    private int cnt;
    public void down() {
        cnt--;
    }

    public boolean isDown() {
        return cnt <= 0;
    }
// END: codeinjection.v1
}
