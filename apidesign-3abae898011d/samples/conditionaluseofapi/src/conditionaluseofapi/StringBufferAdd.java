package conditionaluseofapi;

/** Implementation for string concatention using Java 1.4 APIs.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class StringBufferAdd implements AddString {
    
    private StringBuffer sb = new StringBuffer();

    public void addString(String msg) {
        sb.append(msg);
    }

    public String getMessage() {
        return sb.toString();
    }

    public String toString() {
        return "Old Java 1.4 implementation";
    }
}
