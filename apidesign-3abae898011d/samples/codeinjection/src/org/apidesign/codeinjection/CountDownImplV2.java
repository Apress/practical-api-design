/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.codeinjection;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.apidesign.codeinjection.spi.CountDownExtender;

/** Second version to {@link CountDownImplV1} - imagine someone wants to
 * reimplement {@link #down()} method to decrement by two not just by one.
 * You do not like that idea at all, but you do not want to prevent such
 * behaviour. You just do not want to maintain such bloody code. What can
 * you do? You need to allow <b>code injection</b> via the
 * {@link CountDownExtender} class!
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class CountDownImplV2 extends CountDown {
    private int cnt;

    CountDownImplV2(int initial) {
        this.cnt = initial;
    }

// BEGIN: codeinjection.v2
    public void down() {
        Iterator<CountDownExtender> it;
        it = ServiceLoader.load(CountDownExtender.class).iterator();
        if (it.hasNext()) {
            // injected behaviour
            cnt = it.next().decrement(cnt);
        } else {
            // common behaviour of 1.0 version
            cnt--;
        }
    }

    public boolean isDown() {
        return cnt <= 0;
    }
// END: codeinjection.v2
}
