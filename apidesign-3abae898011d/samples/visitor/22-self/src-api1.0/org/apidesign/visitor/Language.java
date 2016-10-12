package org.apidesign.visitor;

public final class Language {
    private Language() { }
    
    public interface Expression {
        public abstract void visit(Visitor v);
    }
    public interface Plus extends Expression {
        public Expression getFirst();
        public Expression getSecond();
    }
    public interface Number extends Expression {
        public int getValue();
    }

    // BEGIN: visitor.clientprovider.self.v1
    public static abstract class Visitor {
        Visitor() {}

        public static Visitor create(Version10 v) {
            return create10(v);
        }

        public interface Version10 {
            public boolean visitUnknown(Expression e, Visitor self);
            public void visitPlus(Plus s, Visitor self);
            public void visitNumber(Number n, Visitor self);
        }

        public abstract void dispatchPlus(Plus p);
        public abstract void dispatchNumber(Number n);
    }
    // END: visitor.clientprovider.self.v1
    
    static Visitor create10(final Visitor.Version10 v) {
        return new Visitor() {
            @Override
            public void dispatchPlus(Plus p) {
                v.visitPlus(p, this);
            }

            @Override
            public void dispatchNumber(Number n) {
                v.visitNumber(n, this);
            }
        };
    }
}
