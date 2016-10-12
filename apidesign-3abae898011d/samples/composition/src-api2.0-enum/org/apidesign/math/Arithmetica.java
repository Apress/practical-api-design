package org.apidesign.math;

/** Class to simplify arithmetical operations, improved version to compute
 * the sum for ranges, but only if one uses the new constructor to indicate
 * need for new version.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 2.0
 */
// BEGIN: design.composition.arith2.0.enum
public class Arithmetica {
    private final Version version;
    public enum Version { VERSION_1_0, VERSION_2_0 }
    
    public Arithmetica() {
        this(Version.VERSION_1_0);
    }
    public Arithmetica(Version version) {
        this.version = version;
    }

    public int sumRange(int from, int to) {
        switch (version) {
            case VERSION_1_0:
                return sumRange1(from, to);
            case VERSION_2_0:
                return sumRange2(from, to);
            default:
                throw new IllegalStateException();
        }
    }
// FINISH: design.composition.arith2.0.enum
    
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
}
