package org.apidesign.sidemeanings.math;

/** 
 */
// BEGIN: design.sidemeanings.arith.factorial
public final class Factorial extends Arithmetica {
    public int factorial(int n) {
        return sumRange(1, n);
    }

    @Override
    protected int overridableSumTwo(int one, int second) {
        return one * second;
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
// END: design.sidemeanings.arith.factorial

