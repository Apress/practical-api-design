package org.apidesign.test.visitor;

import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Minus;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.junit.Test;

public class InvalidCountNumbersTest {
    @Test public void printOneMinusTwo() {
        Number one = new Number(1);
        Number three = new Number(3);
        Number four = new Number(4);
        Expression minus = new Plus(one, new Minus(three, four));
        
        assertEquals(
            "Should have three numbers, but visitor does not " +
            "know how to go through minus", 
            3, CountNumbersTest.countNumbers(minus)
        );
    }
}
