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
    // BEGIN: visitor.clientprovider.v2
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
            public void visitMinus(Minus m);
        }


        /** @since 2.0 */
        public abstract void dispatchNumber(Number n);
    // FINISH: visitor.clientprovider.v2
        
        public static Visitor create(Version10 v) {
            return create10(v);
        }

        public interface Version10 {
            public boolean visitUnknown(Expression e);
            public void visitPlus(Plus s);
            public void visitNumber(Number n);
        }

        public abstract void dispatchPlus(Plus p);
        public abstract void dispatchMinus(Minus m);
    }
    
    static Visitor create10(final Visitor.Version10 v) {
        return new Visitor() {
            @Override
            public void dispatchPlus(Plus p) {
                v.visitPlus(p);
            }

            @Override
            public void dispatchNumber(Number n) {
                v.visitNumber(n);
            }

            @Override
            public void dispatchMinus(Minus m) {
                if (v.visitUnknown(m)) {
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
                v.visitPlus(p);
            }

            @Override
            public void dispatchNumber(Number n) {
                v.visitNumber(n);
            }

            @Override
            public void dispatchMinus(Minus m) {
                v.visitMinus(m);
            }
        };
    }
}
