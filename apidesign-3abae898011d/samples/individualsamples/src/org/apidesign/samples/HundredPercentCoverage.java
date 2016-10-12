package org.apidesign.samples;

public class HundredPercentCoverage {
    // BEGIN: hundred.percent
    private int sum = 10;

    public int add(int x) {
        sum += x;
        return sum;
    }

    public int percentageFrom(int howMuch) {
        return 100 * howMuch / sum;
    }
    // END: hundred.percent
}
