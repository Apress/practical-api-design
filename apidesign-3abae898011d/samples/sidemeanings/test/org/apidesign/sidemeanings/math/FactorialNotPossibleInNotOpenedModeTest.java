package org.apidesign.sidemeanings.math;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class FactorialNotPossibleInNotOpenedModeTest extends FactorialTest {
    @Before @Override
    public void setUp() {
        instance = new Factorial();
        instance.openUpToSubclasses = false;
    }

    @Test @Override
    public void testFactorial4() {
        if (Boolean.getBoolean("no.failures")) return;
        super.testFactorial4();
    }

    @Test @Override
    public void testFactorial5() {
        if (Boolean.getBoolean("no.failures")) return;
        super.testFactorial5();
    }

}
