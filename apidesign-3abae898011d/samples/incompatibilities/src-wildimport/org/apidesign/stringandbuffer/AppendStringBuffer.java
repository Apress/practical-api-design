package org.apidesign.stringandbuffer;

/** This code compiles on 1.3 as well as 1.4. However, if compiled on
 * 1.4, it will throw <code>NoSuchMethodError</code> when executed on
 * 1.3. How is that possible?
 */
public class AppendStringBuffer {
    static {
        // BEGIN: theory.appendstringbuffer
        StringBuffer sb = new StringBuffer();
        StringBuffer another = new StringBuffer();
        sb.append(another);
        // END: theory.appendstringbuffer
    }
}
