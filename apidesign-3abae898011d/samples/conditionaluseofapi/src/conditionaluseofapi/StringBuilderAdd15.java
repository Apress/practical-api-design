package conditionaluseofapi;

/** Implementation for string concatention using Java 5 only.
 * 
 * StringBuilder is supposed to be faster than StringBuffer as it
 * is not synchronized and as such it is does not waste time with
 * synchronization when its methods are called. 
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class StringBuilderAdd15 implements AddString {
    
    private StringBuilder sb = new StringBuilder();

    public void addString(String msg) {
        sb.append(msg);
    }

    public String getMessage() {
        return sb.toString();
    }

    public String toString() {
        return "New Shiny and Fast Java 5 Implementation";
    }
}
