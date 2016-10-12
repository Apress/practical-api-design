package org.apidesign.openfixed;

/** Test the Calculator.createAsynch() behavior.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class AsyncTest extends CalculatorBase {
    
    public AsyncTest(String testName) {
        super(testName);
    }

    @Override
    protected Calculator create() {
        return Calculator.createAsynch();
    }

}
