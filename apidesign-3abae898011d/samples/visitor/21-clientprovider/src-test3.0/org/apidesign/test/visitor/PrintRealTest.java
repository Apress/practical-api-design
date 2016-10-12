package org.apidesign.test.visitor;

import org.apidesign.test.visitor.PrintTest.PrintVisitor;
import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Minus;
import org.apidesign.visitor.Language.Number;
import org.apidesign.visitor.Language.Plus;
import org.apidesign.visitor.Language.Real;
import org.apidesign.visitor.Language.Visitor;
import org.junit.Test;

public class PrintRealTest {
    @Test public void printOneMinusTwo() {
        Real one = newReal(1);
        Real two = newReal(2);
        Expression plus = PrintOfMinusStructureTest.newMinus(one, two);
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print.dispatch);
        
        if (Boolean.getBoolean("no.failures")) {
            assertEquals("unknownunknownunknown", print.sb.toString());
            return;
        }
        assertEquals("1.0 - 2.0", print.sb.toString());
    }
    
    @Test public void visitorReadyForVersion30() {
        class PrintVisitor30 implements Visitor.Version30 {
            StringBuffer sb = new StringBuffer();
            Visitor dispatch = Visitor.create(this);

            public boolean visitUnknown(Expression exp) {
                sb.append("unknown");
                return true;
            }

            public void visitPlus(Plus s) {
                s.getFirst().visit(dispatch);
                sb.append(" + ");
                s.getSecond().visit(dispatch);
            }

            public void visitMinus(Minus m) {
                m.getFirst().visit(dispatch);
                sb.append(" - ");
                m.getSecond().visit(dispatch);
            }

            public void visitReal(Real r) {
                sb.append(r.getValue());
            }
        }
        
        Number one = PrintTest.newNumber(1);
        Number two = PrintTest.newNumber(2);
        Expression plus = PrintOfMinusStructureTest.newMinus(one, two);
        
        PrintVisitor30 print = new PrintVisitor30();
        plus.visit(print.dispatch);
        
        assertEquals("1.0 - 2.0", print.sb.toString());

        Real five = newReal(5);
        Real three = newReal(3);
        Expression realPlus = PrintOfMinusStructureTest.newMinus(five, three);
        
        PrintVisitor30 printReal = new PrintVisitor30();
        realPlus.visit(printReal.dispatch);
        
        assertEquals("5.0 - 3.0", printReal.sb.toString());
        
    }
    
    public static Real newReal(final double value) {
        return new Real() {
            public double getValue() {
                return value;
            }

            public void visit(Visitor v) {
                v.dispatchReal(this);
            }
        };
    }
}
