
package org.apidesign.delegatingwriter;

import java.io.IOException;
import java.io.Writer;

/** Writer alters each char from 'A' to 'Z' range with next one just like
 * old Romans did.
 *
 * @author Jaroslav Tulach
 */
public class CryptoWriter extends AltBufferedWriter {
    public CryptoWriter(Writer out) {
        super(out);
    }
    public CryptoWriter(
        Writer out, AltBufferedWriter.Behaviour behaviour
    ) {
        super(out, behaviour);
    }
/* The above code is here to let us simulate different behaviours of the append
 * method. In reality, the class would just subclass BufferedWriter, as shown bellow:
 BEGIN: writer.CryptoWriter
public class CryptoWriter extends BufferedWriter {
    public CryptoWriter(Writer out) {
        super(out);
    }
    
    /* We need to override all known methods of BufferedWriter 
    * and do conversion of the argument char, string or char array.
    */
    
    @Override
    public void write(char[] buf, int off, int len) throws IOException {
        char[] arr = new char[len];
        for (int i = 0; i < len; i++) {
            arr[i] = encryptChar(buf[off + i]);
        }
        super.write(arr, 0, len);
    }

    @Override
    public void write(int c) throws IOException {
        super.write(encryptChar(c));
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(encryptChar(str.charAt(off + i)));
        }
        super.write(sb.toString(), 0, len);
    }

    private char encryptChar(int c) {
        if (c == 'Z') {
            return 'A';
        }
        if (c == 'z') {
            return 'a';
        }
        return (char)(c + 1);
    }
// FINISH: writer.CryptoWriter

    /* delegates to write(cbuf, 0, cbuf.length)
    public void write(char[] cbuf) throws IOException {
    }
    */

    /* delegates to write(str, 0, str.length())
    public void write(String str) throws IOException {
    }
    */

    
/* As this class was written against the version provided by JDK 1.4, we
 * could not override the append methods, as they did not exist at that time.
     
    @Override
    public Writer append(CharSequence csq) throws IOException {
    }

    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
    }

    @Override
    public Writer append(char c) throws IOException {
    }
*/
    
}
