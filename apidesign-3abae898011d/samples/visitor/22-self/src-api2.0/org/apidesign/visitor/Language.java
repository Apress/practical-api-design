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
    /** @since 2.0 */
    public interface Minus extends Expression {
        public Expression getFirst();
        public Expression getSecond();
    }
    
    public static abstract class Visitor {
        Visitor() {}
        /** @since 2.0 */
        public static Visitor create(Version20 v) {
            return create20(v);
        }

        /** @since 2.0 */
        public interface Version20 extends Version10 {
            public void visitMinus(Minus m, Visitor self);
        }


        /** @since 2.0 */
        public abstract void dispatchNumber(Number n);
        
        public static Visitor create(Version10 v) {
            return create10(v);
        }

        public interface Version10 {
            public boolean visitUnknown(Expression e, Visitor self);
            public void visitPlus(Plus s, Visitor self);
            public void visitNumber(Number n, Visitor self);
        }

        public abstract void dispatchPlus(Plus p);
        public abstract void dispatchMinus(Minus m);
    }
    
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

            @Override
            public void dispatchMinus(Minus m) {
                if (v.visitUnknown(m, this)) {
                    m.getFirst().visit(this);
                    m.getSecond().visit(this);
                }
            }
        };
    }
    static Visitor create20(final Visitor.Version20 v) {
        return new Visitor() {
            @Override
            public void dispatchPlus(Plus p) {
                v.visitPlus(p, this);
            }

            @Override
            public void dispatchNumber(Number n) {
                v.visitNumber(n, this);
            }

            @Override
            public void dispatchMinus(Minus m) {
                v.visitMinus(m, this);
            }
        };
    }
}
