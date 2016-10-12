package org.apidesign.visitor;

public final class Language {

    private Language() {
    }

    public static abstract class Expression {

        Expression() {
        }

        public abstract void visit(Visitor v);
    }

    public static final class Plus extends Expression {

        private final Expression first;
        private final Expression second;

        public Plus(Expression first, Expression second) {
            this.first = first;
            this.second = second;
        }

        public Expression getFirst() {
            return first;
        }

        public Expression getSecond() {
            return second;
        }

        @Override
        public void visit(Visitor v) {
            if (v instanceof Visitor10) {
                ((Visitor10) v).visitPlus(this);
            } else if (v instanceof Visitor30) {
                ((Visitor30) v).visitPlus(this);
            } else {
                v.visitUnknown(this);
            }
        }
    }

    public static final class Number extends Expression {

        private final int value;

        public Number(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public void visit(Visitor v) {
            if (v instanceof Visitor10) {
                ((Visitor10) v).visitNumber(this);
            } else if (v instanceof Visitor30) {
                Real wrapper = new Real(getValue());
                ((Visitor30) v).visitReal(wrapper);
            } else {
                v.visitUnknown(this);
            }
        }
    }
    // BEGIN: visitor.nonmonotonic.Minus3
    /** @since 2.0 */
    public static final class Minus/*3.0*/ extends Expression {
        private final Expression first;
        private final Expression second;
        
        public Minus(Expression first, Expression second) {
            this.first = first;
            this.second = second;
        }
        public Expression getFirst() { return first; }
        public Expression getSecond() { return second; }
        
        public void visit(Visitor v) { 
            if (v instanceof Visitor20) {
                ((Visitor20)v).visitMinus(this);
            } else if (v instanceof Visitor30) {
                ((Visitor30)v).visitMinus(this);
            } else {
                v.visitUnknown(this);
            }
        }
    }
    // END: visitor.nonmonotonic.Minus3
    // BEGIN: visitor.nonmonotonic.real
    /** @since 3.0 */
    public static final class Real extends Expression {
        private final double value;
        public Real(double value) {
            this.value = value;
        }
        public double getValue() {
            return value;
        }
        public void visit(Visitor v)
        // FINISH: visitor.nonmonotonic.real
        {
            if (v instanceof Visitor30) {
                ((Visitor30)v).visitReal(this);
            } else {
                v.visitUnknown(this);
            }
        }
    }

    
    public interface Visitor {
        public void visitUnknown(Expression e);
    }
    public interface Visitor10 extends Visitor {
        public void visitPlus(Plus s);
        public void visitNumber(Number n);
    }
    /** @since 2.0 */
    public interface Visitor20 extends Visitor {
        public void visitMinus(Minus s);
    }
    // BEGIN: visitor.nonmonotonic.visitor
    /** @since 3.0 */
    public interface Visitor30 extends Visitor {
        public void visitPlus(Plus s);
        public void visitMinus(Minus s);
        public void visitReal(Real r);
    }
    // END: visitor.nonmonotonic.visitor
}
