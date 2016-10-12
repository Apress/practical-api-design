package org.apidesign.visitor.notevolutionready;

public final class Language {
    private Language() { }
    
    // BEGIN: visitor.notevolutionready.v1
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

    public interface Visitor {
        public void visitPlus(Plus s);
        public void visitNumber(Number n);
    }
    // END: visitor.notevolutionready.v1
}
