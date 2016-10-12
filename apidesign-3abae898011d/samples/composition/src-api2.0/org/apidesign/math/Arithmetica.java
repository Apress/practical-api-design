package org.apidesign.math;

/** Class to simplify arithmetical operations, improved version to compute
 * the sum for ranges.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 2.0
 */
public class Arithmetica {
    public int sumTwo(int one, int second) {
        return one + second;
    }
    
    public int sumAll(int... numbers) {
        if (numbers.length == 0) {
            return 0;
        }
        int sum = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            sum = sumTwo(sum, numbers[i]);
        }
        return sum;
    }
    
// BEGIN: design.composition.arith2.0
    public int sumRange(int from, int to) {
        return (from + to) * (Math.abs(to - from) + 1) / 2;
    }
// END: design.composition.arith2.0
}
