package org.apidesign.openfixed;

/** Test the Calculator.create() behavior.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class BasicTest extends CalculatorBase {
    public BasicTest(String testName) {
        super(testName);
    }

    @Override
    protected Calculator create() {
        return Calculator.create();
    }
}
