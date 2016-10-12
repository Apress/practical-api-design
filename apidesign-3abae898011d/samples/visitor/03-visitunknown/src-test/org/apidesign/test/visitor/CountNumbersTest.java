package org.apidesign.test.visitor;

import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.apidesign.visitor.Language.Visitor;
import org.junit.Test;

public class CountNumbersTest {

    // BEGIN: visitor.count.numbers.visitunknown
    private static class CountNumbers extends Visitor/*version1.0*/ {
        int cnt;

        @Override
        public void visitUnknown(Expression exp) {
            // not a number
        }
        public void visitPlus(Plus s) {
            s.getFirst().visit(this);
            s.getSecond().visit(this);
        }
        public void visitNumber(Number n) {
            cnt++;
        }
    }

    public static int countNumbers(Expression expression) {
        CountNumbers counter = new CountNumbers();
        expression.visit(counter);
        return counter.cnt;
    }
    // END: visitor.count.numbers.visitunknown
    
    @Test public void printOnePlusOne() {
        Number one = new Number(1);
        Expression expression = new Plus(one, one);

        assertEquals("Two 1's", 2, countNumbers(expression));
    }

    @Test public void printOnePlusTwoPlusThree() {
        Number one = new Number(1);
        Number two = new Number(2);
        Number three = new Number(3);
        Expression plus = new Plus(one, new Plus(two, three));
        
        assertEquals("Three", 3, countNumbers(plus));
    }
}
