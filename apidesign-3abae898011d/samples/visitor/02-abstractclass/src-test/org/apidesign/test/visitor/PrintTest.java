package org.apidesign.test.visitor;

import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.apidesign.visitor.Language.Visitor;
import org.junit.Test;

public class PrintTest {
    public static class PrintVisitor extends Visitor {
        StringBuffer sb = new StringBuffer();
        
        public void visitPlus(Plus s) {
            s.getFirst().visit(this);
            sb.append(" + ");
            s.getSecond().visit(this);
        }

        public void visitNumber(Number n) {
            sb.append (n.getValue());
        }
    }
    
    @Test public void printOnePlusOne() {
        Number one = new Number(1);
        Expression plus = new Plus(one, one);
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print);
        
        assertEquals("1 + 1", print.sb.toString());
    }

    @Test public void printOnePlusTwoPlusThree() {
        Number one = new Number(1);
        Number two = new Number(2);
        Number three = new Number(3);
        Expression plus = new Plus(one, new Plus(two, three));
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print);
        
        assertEquals("1 + 2 + 3", print.sb.toString());
    }
}
