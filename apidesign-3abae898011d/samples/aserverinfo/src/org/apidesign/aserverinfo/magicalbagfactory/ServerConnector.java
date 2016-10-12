package org.apidesign.aserverinfo.magicalbagfactory;

import java.net.URL;
import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.ShutdownHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import org.openide.util.Lookup;

/** A class to connect to server, identify it and manipulate with
 * it state. The <a href="http://apidesign.org">Practical API Design</a>
 * book used to call it AServerInfo.
 * <p>
 */
public final class ServerConnector {
    public String getName() {
        return name == null ? "noname" : name.getName();
    }

    public URL getURL() {
        return url == null ? null : url.getURL();
    }

    public void reset() {
        if (reset != null) {
            reset.reset();
        }
    }

    /** Additional method for API clients not available from first version.
     * @since 2.0
     */
    public void shutdown() {
        if (shutdown != null) {
            shutdown.shutdown();
        }
    }

    //
    // private part
    //
    
    private final NameProvider name;
    private final URLProvider url;
    private final ResetHandler reset;
    private final ShutdownHandler shutdown;

    private ServerConnector(
        NameProvider name, URLProvider url,
        ResetHandler reset, ShutdownHandler shutdown
    ) {
        this.name = name;
        this.url = url;
        this.reset = reset;
        this.shutdown = shutdown;
    }

    // BEGIN: aserverinfo.magicalbag.create
    public static ServerConnector create(final Lookup interfaces) {
        NameProvider nameP = new NameProvider() {
            public String getName() {
                NameProvider p = interfaces.lookup(NameProvider.class);
                return p == null ? "noname" : p.getName();
            }
        };
        URLProvider urlP = new URLProvider() {
            public URL getURL() {
                URLProvider p = interfaces.lookup(URLProvider.class);
                return p == null ? null : p.getURL();
            }
        };
        ResetHandler resetP = new ResetHandler() {
            public void reset() {
                ResetHandler h = interfaces.lookup(ResetHandler.class);
                if (h != null) {
                    h.reset();
                }
            }
        };
        // code present since version 2.0
        ShutdownHandler shutH = new ShutdownHandler() {
            public void shutdown() {
                ShutdownHandler h = interfaces.lookup(ShutdownHandler.class);
                if (h != null) {
                    h.shutdown();
                }
            }
        };
        
        return new ServerConnector(nameP, urlP, resetP, shutH);
    }
    // END: aserverinfo.magicalbag.create
}
