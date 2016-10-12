package org.apidesign.visitor;

public final class Language {
    private Language() { }
    
    public static abstract class Expression {
        Expression() {}
        public abstract void visit(Visitor v);
    }
    public static final class Plus extends Expression {
        private final Expression first;
        private final Expression second;
        
        public Plus(Expression first, Expression second) {
            this.first = first;
            this.second = second;
        }
        public Expression getFirst() { return first; }
        public Expression getSecond() { return second; }
        @Override
        public void visit(Visitor v) { v.visitPlus(this); }
    }
    public static final class Number extends Expression {
        private final int value;
        public Number(int value) { this.value = value; }
        public int getValue() { return value; }
        @Override
        public void visit(Visitor v) { v.visitNumber(this); }
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
        public void visit(Visitor v) { 
            v.visitMinus(this);
        }
    }

    // BEGIN: visitor.traversal.v2
    public static abstract class Visitor/*2.0*/ {
        public boolean visitUnknown(Expression e) {
            throw new IllegalStateException("Unknown element faced: " + e);
        }
        public void visitPlus(Plus s) {
            if (visitUnknown(s)) {
                s.getFirst().visit(this);
                s.getSecond().visit(this);
            }
        }
        public void visitMinus(Minus s) {
            if (visitUnknown(s)) {
                s.getFirst().visit(this);
                s.getSecond().visit(this);
            }
        }
        public void visitNumber(Number n) {
            visitUnknown(n);
        }
    }
    // END: visitor.traversal.v2
}
