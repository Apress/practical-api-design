package org.apidesign.math.test;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apidesign.math.Arithmetica;

public class ArithmeticaCompatibilityTest extends TestCase {
    public ArithmeticaCompatibilityTest(String name) {
        super(name);
    }

    private static final class CountingSubclass extends Arithmetica {
        int countSumTwo;
        int countSumAll;
        int countSumRange;

        @Override
        public int sumAll(int... numbers) {
            countSumAll++;
            return super.sumAll(numbers);
        }

        @Override
        public int sumRange(int from, int to) {
            countSumRange++;
            return super.sumRange(from, to);
        }

        @Override
        public int sumTwo(int one, int second) {
            countSumTwo++;
            return super.sumTwo(one, second);
        }
    } // end of CountingSubclass
    
    private static final class CountingOldSubclass extends OldArithmetica1 {
        int countSumTwo;
        int countSumAll;
        int countSumRange;

        @Override
        public int sumAll(int... numbers) {
            countSumAll++;
            return super.sumAll(numbers);
        }

        @Override
        public int sumRange(int from, int to) {
            countSumRange++;
            return super.sumRange(from, to);
        }

        @Override
        public int sumTwo(int one, int second) {
            countSumTwo++;
            return super.sumTwo(one, second);
        }
    } // end of CountingSubclass
    
    // BEGIN: total.rewrite.tests
    public void testRandomCheck () throws Exception {
        if (Boolean.getBoolean("no.failures")) return;
        long seed = System.currentTimeMillis();
        try {
            CountingSubclass now = new CountingSubclass();
            CountingOldSubclass old = new CountingOldSubclass();
            
            compare(now, old, seed);
        
            assertEquals(
                "Verify amount calls to of sumRange is the same", 
                now.countSumRange, old.countSumRange
            );
            assertEquals(
                "Verify amount calls to of sumAll is the same", 
                now.countSumAll, old.countSumAll
            );
            assertEquals(
                "Verify amount calls to of sumTwo is the same", 
                now.countSumTwo, old.countSumTwo
            );
        } catch (AssertionFailedError ex) {
            IllegalStateException n = new IllegalStateException (
                "Seed: " + seed + "\n" + ex.getMessage ()
            );
            n.initCause(ex);
            throw n;
        } catch (Exception ex) {
            IllegalStateException n = new IllegalStateException (
                "Seed: " + seed + "\n" + ex.getMessage ()
            );
            n.initCause(ex);
            throw n;
        }
    }
    
    public void testSimulateOKRunOn1208120436947() throws Exception {
        CountingSubclass now = new CountingSubclass();
        CountingOldSubclass old = new CountingOldSubclass();

        compare(now, old, 1208120436947L);

        assertEquals(
            "Verify amount of calls to sumRange is the same", 
            now.countSumRange, old.countSumRange
        );
        assertEquals(
            "Verify amount of calls to sumAll is the same", 
            now.countSumAll, old.countSumAll
        );
        assertEquals(
            "Verify amount of calls to sumTwo is the same", 
            now.countSumTwo, old.countSumTwo
        );
    }

    public void testSimulateFailureOn1208120628821() throws Exception {
        if (Boolean.getBoolean("no.failures")) return;
        CountingSubclass now = new CountingSubclass();
        CountingOldSubclass old = new CountingOldSubclass();

        compare(now, old, 1208120628821L);

        assertEquals(
            "Verify amount of calls to sumRange is the same", 
            now.countSumRange, old.countSumRange
        );
        assertEquals(
            "Verify amount of calls to sumAll is the same", 
            now.countSumAll, old.countSumAll
        );
        assertEquals(
            "Verify amount of calls to sumTwo is the same", 
            now.countSumTwo, old.countSumTwo
        );
    }
    // END: total.rewrite.tests
    
    // BEGIN: total.rewrite.compare
    private void compare (Arithmetica now, OldArithmetica1 old, long seed) 
    throws Exception {
        java.util.Random r = new java.util.Random (seed);
        
        for (int loop = 0; loop < r.nextInt(5); loop++) {
            int operation = r.nextInt(3);
            switch (operation) {
                case 0: { // sumTwo
                    int a1 = r.nextInt(100);
                    int a2 = r.nextInt(100);
                    int resNow = now.sumTwo(a1, a2);
                    int resOld = old.sumTwo(a1, a2);
                    assertEquals("sumTwo results are equal", resNow, resOld);
                    break;
                }
                case 1: { // sumArray
                    int[] arr = new int[r.nextInt(100)];
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = r.nextInt(100);
                    }
                    int resNow = now.sumAll(arr);
                    int resOld = old.sumAll(arr);
                    assertEquals("sumArray results are equal", resNow, resOld);
                    break;
                }
                case 2: { // sumRange
                    int a1 = r.nextInt(100);
                    int a2 = r.nextInt(100);
                    int resNow = now.sumRange(a1, a1 + a2);
                    int resOld = old.sumRange(a1, a1 + a2);
                    assertEquals("sumRange results are equal", resNow, resOld);
                    break;
                }
            }
        }
    }
    // END: total.rewrite.compare

    
    // BEGIN: total.rewrite.oldimpl
    /** This is a copy of the implementation of Arithmetica from version 1.0 */
    static class OldArithmetica1 {
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
            int[] array = new int[len + 1];
            for (int i = 0; i <= len; i++) {
                array[i] = from + i;
            }
            return sumAll(array);
        }
    } 
    // END: total.rewrite.oldimpl
    
}
