
package conditionaluseofapi;

/** Facade interface to shield us from the actual implementation for 
 * Java 1.4 and Java 5.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: theory.binary.overloads.facade
interface AddString {
    public void addString(String msg);
    public String getMessage();
}
// END: theory.binary.overloads.facade
