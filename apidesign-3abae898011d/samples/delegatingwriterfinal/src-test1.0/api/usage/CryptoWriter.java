
package api.usage;

import api.Writer;
import java.io.IOException;


/** Writer alters each char from 'A' to 'Z' range with next one just like
 * old Romans did.
 *
 * @author Jaroslav Tulach
 */
public class CryptoWriter implements Writer.Impl {
    private Writer out;
    
    private CryptoWriter(Writer out) {
        this.out = out;
    }
    
    
    public static Writer create(Writer out) {
        return Writer.create(new CryptoWriter(out));
    }
    
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] arr = new char[len];
        for (int i = 0; i < len; i++) {
            arr[i] = convertChar(cbuf[off + i]);
        }
        out.write(arr, 0, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(convertChar(str.charAt(off + i)));
        }
        out.write(sb.toString(), 0, len);
    }

    private char convertChar(int c) {
        if (c == 'Z') {
            return 'A';
        }
        if (c == 'z') {
            return 'a';
        }
        return (char)(c + 1);
    }

    public void close() throws IOException {
        out.close();
    }

    public void flush() throws IOException {
        out.flush();
    }
}
