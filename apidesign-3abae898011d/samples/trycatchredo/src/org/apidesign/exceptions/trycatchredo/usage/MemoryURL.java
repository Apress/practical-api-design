
package org.apidesign.exceptions.trycatchredo.usage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/** Support for special "memory://" URLs. Useful when testing network communication.
 *
 * @author Jaroslav Tulach
 */
public final class MemoryURL extends URLStreamHandler {
    private MemoryURL() {
    }

    public static void initialize() {
    }
    static {
        class F implements URLStreamHandlerFactory {
            public URLStreamHandler createURLStreamHandler(String protocol) {
                if (protocol.startsWith("memory")) {
                    return new MemoryURL();
                }
                return null;
            }
        }
        F f = new F();
        URL.setURLStreamHandlerFactory(f);
    }
    
    private static Map<String,InputStream> contents = new HashMap<String,InputStream>();
    private static Map<String,OutputStream> outputs = new HashMap<String,OutputStream>();

    public static void registerURL(String u, String content, OutputStream out) throws MalformedURLException {
        contents.put(u, new ByteArrayInputStream(content.getBytes()));
        outputs.put(u, out);
    }
    
    public static String getOutputForURL(String u) {
        OutputStream out = outputs.get(u);
        return out.toString();
    }
    
    protected URLConnection openConnection(URL u) throws IOException {
        return new MC(u);
    }
    
    private static final class MC extends URLConnection {
        private InputStream values;
        private OutputStream out;
        
        public MC(URL u) {
            super(u);
            out = outputs.get(u.toExternalForm());
            if (out == null) {
                out = new ByteArrayOutputStream();
                outputs.put(u.toExternalForm(), out);
            }
        }

        public void connect() throws IOException {
            if (values != null) {
                return;
            }
            values = contents.remove(url.toExternalForm());
            if (values == null) {
                throw new IOException("No such content: " + url);
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            connect();
            return values;
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            if (out == null) {
                out = new ByteArrayOutputStream();
            }
            return out;
        }
    }
}
