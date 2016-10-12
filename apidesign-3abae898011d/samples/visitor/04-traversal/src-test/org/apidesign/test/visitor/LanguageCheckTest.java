package org.apidesign.test.visitor;

import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.apidesign.visitor.Language.Visitor;
import org.junit.Test;

public class LanguageCheckTest {

    private static class Valid10Language extends Visitor/*version1.0*/ {
        boolean invalid;

        @Override
        public boolean visitUnknown(Expression exp) {
            invalid = true;
            return false;
        }
        public void visitPlus(Plus s) {
            s.getFirst().visit(this);
            s.getSecond().visit(this);
        }
        public void visitNumber(Number n) { 
        }
    }

    public static boolean isValid10Language(Expression expression) {
        Valid10Language valid = new Valid10Language();
        expression.visit(valid);
        return !valid.invalid;
    }
    
    @Test public void printOnePlusOne() {
        Number one = new Number(1);
        Expression expression = new Plus(one, one);

        assertTrue("Valid language", isValid10Language(expression));
    }

    @Test public void printOnePlusTwoPlusThree() {
        Number one = new Number(1);
        Number two = new Number(2);
        Number three = new Number(3);
        Expression plus = new Plus(one, new Plus(two, three));
        
        assertTrue("Valid language", isValid10Language(plus));
    }
}
