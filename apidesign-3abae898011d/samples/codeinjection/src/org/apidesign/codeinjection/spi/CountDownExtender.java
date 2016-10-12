package org.apidesign.codeinjection.spi;

/** Allows to define meaning of "--"
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @since 2.0
 */
// BEGIN: codeinjection.slot
public interface CountDownExtender {
    /** Allows providers to specify that it means to "decrement" a value.
     *
     * @param value previous value
     * @return result of "--" operation in interpretation of this extender
     */
    public int decrement(int value);
}
// END: codeinjection.slot
