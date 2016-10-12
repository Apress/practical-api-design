package org.apidesign.test.visitor;

import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Minus;
import org.apidesign.visitor.Language.Number;
import org.junit.Test;

public class InvalidLanguageCheckTest {
    @Test public void printOneMinusTwo() {
        Number one = new Number(1);
        Number two = new Number(2);
        Expression minus = new Minus(one, two);
        
        assertFalse("Recognized as invalid 1.0 version of the language", LanguageCheckTest.isValid10Language(minus));
    }
}
