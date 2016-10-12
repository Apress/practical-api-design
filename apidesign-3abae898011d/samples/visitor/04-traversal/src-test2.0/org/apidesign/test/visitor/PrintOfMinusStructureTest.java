package org.apidesign.test.visitor;

import org.apidesign.test.visitor.PrintTest.PrintVisitor;
import static junit.framework.Assert.*;
import org.apidesign.visitor.Language.Expression;
import org.apidesign.visitor.Language.Minus;
import org.apidesign.visitor.Language.Number;
import org.junit.Test;

public class PrintOfMinusStructureTest {
    @Test public void printOneMinusTwo() {
        if (Boolean.getBoolean("no.failures")) return;
        Number one = new Number(1);
        Number two = new Number(2);
        Expression minus = new Minus(one, two);
        
        PrintVisitor print = new PrintVisitor();
        minus.visit(print); // fails with IllegalStateException
        
        assertEquals("1 - 2", print.sb.toString());
    }
}
