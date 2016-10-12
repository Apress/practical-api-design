package org.apidesign.test.visitor;

import org.apidesign.test.visitor.PrintTest.PrintVisitor;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Minus;
import org.apidesign.visitor.Language.Number;
import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Visitor;
import org.junit.Test;

public class PrintOfMinusStructureTest {
    public static Minus newMinus(
        final Expression first, final Expression second
    ) {
        return new Minus() {
            public Expression getFirst() {
                return first;
            }

            public Expression getSecond() {
                return second;
            }

            public void visit(Visitor v) {
                v.dispatchMinus(this);
            }
        };
    }
    
    
    @Test public void printOneMinusTwo() {
        Number one = PrintTest.newNumber(1);
        Number two = PrintTest.newNumber(2);
        Expression plus = newMinus(one, two);
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print.dispatch);
        
        if (Boolean.getBoolean("no.failures")) {
            assertEquals("unknown12", print.sb.toString());
            return;
        }
        assertEquals("1 - 2", print.sb.toString());
    }
}
