package org.apidesign.test.visitor;

import org.apidesign.test.visitor.PrintTest.PrintVisitor;
import static junit.framework.Assert.*;
import org.apidesign.visitor.notevolutionready.Language.Expression;
import org.apidesign.visitor.notevolutionready.Language.Minus;
import org.apidesign.visitor.notevolutionready.Language.Number;
import org.junit.Test;

public class PrintOfMinusStructureTest {
    @Test public void printOneMinusTwo() {
        if (Boolean.getBoolean("no.failures")) return;
        // BEGIN: visitor.notevolutionready.oldwithnew
        Number one = new Number(1);
        Number two = new Number(2);
        Expression plus = new Minus(one, two);
        
        PrintVisitor print = new PrintVisitor();
        plus.visit(print); // fails with AbstractMethodError
        
        assertEquals("1 - 2", print.sb.toString());
        // END: visitor.notevolutionready.oldwithnew
    }
}
