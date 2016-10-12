package org.apidesign.math;

import org.apidesign.runtime.check.RuntimeCheck;

/** Class to simplify arithmetical operations, improved version to compute
 * the sum for ranges, but only if the virtual machine is configured to
 * run in incompatible mode.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 2.0
 */
// BEGIN: design.composition.arith.runtime
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
        if (calledByV2AwareLoader()) {
            return sumRange2(from, to);
        } else {
            return sumRange1(from, to);
        }
    }

    private int sumRange1(int from, int to) {
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
    
    private int sumRange2(int from, int to) {
        return (from + to) * (Math.abs(to - from) + 1) / 2;
    }

    private static boolean calledByV2AwareLoader() {
        // BEGIN: design.composition.arith2.6.runtime
        StackTraceElement[] arr = Thread.currentThread().getStackTrace();
        ClassLoader myLoader = Arithmetica.class.getClassLoader();
        for (int i = 0; i < arr.length; i++) {
            ClassLoader caller = arr[i].getClass().getClassLoader();
            if (myLoader == caller) {
                continue;
            }
            if (RuntimeCheck.requiresAtLeast(
                "2.0", "org.apidesign.math", caller
            )) {
                return true;
            }
            return false;
        }
        return true;
        // END: design.composition.arith2.6.runtime
    }
    
}
// END: design.composition.arith.runtime
