package org.apidesign.samples;

public class CodeThatCanBeCalledOnlyByTest {
    private static int number;

    public static int getNumber() {
        return number;
    }
    
    public static void setNumber(int n) {
        // BEGIN: call.only.by.test
        boolean assertionsOn = false;
        assert assertionsOn = true;
        if (assertionsOn) {
            throw new IllegalStateException("This is a testing method only!");
        }
        // END: call.only.by.test
        number = n;
    }
}
