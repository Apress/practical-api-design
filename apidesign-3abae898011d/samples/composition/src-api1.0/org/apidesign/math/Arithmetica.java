package org.apidesign.math;

/** Class to simplify arithmetical operations.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 1.0
 */
// BEGIN: design.composition.arith1.0
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
    
    public int sumRange(int from, int to) {
        int len = to - from;
        if (len < 0) {
            len = -len;
            from = to;
        }
        int[] array = new int[len + 1];
        for (int i = 0; i <= len; i++) {
            array[i] = from + i;
        }
        return sumAll(array);
    }
}
// END: design.composition.arith1.0
