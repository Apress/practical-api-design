package org.apidesign.test.visitor;

import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.apidesign.visitor.Language.Visitor;
import static junit.framework.Assert.*;
import org.junit.Test;

public class PrintTest {
    
    public static Number newNumber(final int value) {
        return new Number() {
            public int getValue() {
                return value;
            }

            public void visit(Visitor v) {
                v.dispatchNumber(this);
            }
        };
    }
    public static Plus newPlus(
        final Expression first, final Expression second
    ) {
        return new Plus() {
            public Expression getFirst() {
                return first;
            }

            public Expression getSecond() {
                return second;
            }

            public void visit(Visitor v) {
                v.dispatchPlus(this);
            }
        };
    }

    // BEGIN: visitor.clientprovider.print
    public static class PrintVisitor implements Visitor.Version10 {
        StringBuffer sb = new StringBuffer();
        
        final Visitor dispatch = Visitor.create(this);
        
        public void visitPlus(Plus s) {
            // s.getFirst().visit(this); // does not compile, we need:
            s.getFirst().visit(dispatch);
            sb.append(" + ");
            s.getSecond().visit(dispatch);
        }

        public void visitNumber(Number n) {
            sb.append (n.getValue());
        }

        public boolean visitUnknown(Expression e) {
            sb.append("unknown");
            return true;
        }
    }
    // END: visitor.clientprovider.print
    
    @Test public void printOnePlusOne() {
        Number one = newNumber(1);
        Expression plus = newPlus(one, one);
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print.dispatch);
        
        assertEquals("1 + 1", print.sb.toString());
    }

    @Test public void printOnePlusTwoPlusThree() {
        Number one = newNumber(1);
        Number two = newNumber(2);
        Number three = newNumber(3);
        Expression plus = newPlus(one, newPlus(two, three));
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print.dispatch);
        
        assertEquals("1 + 2 + 3", print.sb.toString());
    }
}
