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
    
    /** @since 3.0 */
    public interface Real extends Expression {
        public double getValue();
    }
    public static abstract class Visitor {
        Visitor() {}
        
        
        /** @since 3.0 */
        public static Visitor create(Version30 v) {
            return create30(v);
        }

        /** @since 3.0 */
        public interface Version30 {
            public boolean visitUnknown(Expression e, Visitor self);
            public void visitPlus(Plus s, Visitor self);
            public void visitMinus(Minus s, Visitor self);
            public void visitReal(Real r, Visitor self);
        }

        /** @since 3.0 */
        public abstract void dispatchReal(Real r);
        
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

            @Override
            public void dispatchReal(Real r) {
                v.visitUnknown(r, this);
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

            @Override
            public void dispatchReal(Real r) {
                v.visitUnknown(r, this);
            }
        };
    }
    static Visitor create30(final Visitor.Version30 v) {
        return new Visitor() {
            @Override
            public void dispatchReal(Real r) {
                v.visitReal(r, this);
            }

            @Override
            public void dispatchNumber(final Number n) {
                class RealWrapper implements Real {
                    public double getValue() {
                        return n.getValue();
                    }
                    public void visit(Visitor v) {
                        n.visit(v);
                    }
                }
                v.visitReal(new RealWrapper(), this);
            }

            @Override
            public void dispatchPlus(Plus p) {
                v.visitPlus(p, this);
            }

            @Override
            public void dispatchMinus(Minus m) {
                v.visitMinus(m, this);
            }
        };
    }
}
