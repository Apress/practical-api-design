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
            } else {
                v.visitUnknown(this);
            }
        }
    }

    /** @since 2.0 */
    public static final class Minus extends Expression {
        private final Expression first;
        private final Expression second;
        
        public Minus(Expression first, Expression second) {
            this.first = first;
            this.second = second;
        }
        public Expression getFirst() { return first; }
        public Expression getSecond() { return second; }
        
        // BEGIN: visitor.cleanversion.dispatch2
        public void visit(Visitor v) { 
            if (v instanceof Visitor20) {
                ((Visitor20)v).visitMinus(this);
            } else {
                v.visitUnknown(this);
            }
        }
        // END: visitor.cleanversion.dispatch2
    }

    // BEGIN: visitor.cleanversion.v2
    public interface Visitor {
        public void visitUnknown(Expression e);
    }
    public interface Visitor10 extends Visitor {
        public void visitPlus(Plus s);
        public void visitNumber(Number n);
    }
    /** @since 2.0 */
    public interface Visitor20 extends Visitor10 {
        public void visitMinus(Minus s);
    }
    // END: visitor.cleanversion.v2
}
