package org.apidesign.math.test;

import org.apidesign.math.Arithmetica;

/** Class showing inventive, non-expected use of 
 * Arithmetica methods to do multiplication instead of
 * addition.
 */
// BEGIN: design.composition.arith.factorial
public final class Factorial extends Arithmetica {
    public static int factorial(int n) {
        return new Factorial().sumRange(1, n);
    }
    @Override
    public int sumTwo(int one, int second) {
        return one * second;
    }
}
// END: design.composition.arith.factorial

