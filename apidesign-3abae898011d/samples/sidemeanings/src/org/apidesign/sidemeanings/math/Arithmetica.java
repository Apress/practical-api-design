package org.apidesign.sidemeanings.math;

/** Rewrite of original
 * <a href="http://source.apidesign.org/hg/apidesign/diff/4e59b6b0e907/samples/composition/src-api1.0/org/apidesign/math/Arithmetica.java">
 * Arithmetica</a>
 * class from the composition project
 * to follow the <q>eliminate fuzzy modifiers</q> rule.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 5.0
 */
// BEGIN: design.sidemeanings.arith
public abstract class Arithmetica {
    // BEGIN: design.sidemeanings.arith.sumTwo
    public final int sumTwo(int one, int second) {
        return overridableSumTwo(one, second);
    }
    protected abstract int overridableSumTwo(int one, int second);
    protected final int defaultSumTwo(int one, int second) {
        return one + second;
    }
    // END: design.sidemeanings.arith.sumTwo
    
    public final int sumAll(int... numbers) {
        return overridableSumAll(numbers);
    }
    protected abstract int overridableSumAll(int... numbers);
    protected final int defaultSumAll(int... numbers) {
        if (numbers.length == 0) {
            return 0;
        }
        int sum = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            // do I want to call sumTwo or defaultSumTwo? That is a question!
            sum = openUpToSubclasses() ?
                sumTwo(sum, numbers[i]) :
                defaultSumTwo(sum, numbers[i]);
        }
        return sum;
    }
    
    public final int sumRange(int from, int to) {
        return overridableSumRange(from, to);
    }
    protected abstract int overridableSumRange(int from, int to);
    protected final int defaultSumRange(int from, int to) {
        int len = to - from;
        if (len < 0) {
            len = -len;
            from = to;
        }
        int[] array = new int[len + 1];
        for (int i = 0; i <= len; i++) {
            array[i] = from + i;
        }
        // BEGIN: design.sidemeanings.arith.the.question
        // Again, an API author has to ask what sumAll one wants to call?
        // Am I about to open up to subclasses or do I want default impl?
        return openUpToSubclasses() ?
            sumAll(array) :
            defaultSumAll(array);
        // END: design.sidemeanings.arith.the.question
    }

    boolean openUpToSubclasses = true;
    boolean openUpToSubclasses() {
        return openUpToSubclasses;
    }


    // BEGIN: design.sidemeanings.arith.factory
    public static Arithmetica create() {
        return new Arithmetica.Impl();
    }
    private static class Impl extends Arithmetica {
        @Override
        protected int overridableSumTwo(int one, int second) {
            return defaultSumTwo(one, second);
        }

        @Override
        protected int overridableSumAll(int... numbers) {
            return defaultSumAll(numbers);
        }

        @Override
        protected int overridableSumRange(int from, int to) {
            return defaultSumRange(from, to);
        }
    }
    // END: design.sidemeanings.arith.factory

}
// END: design.sidemeanings.arith
