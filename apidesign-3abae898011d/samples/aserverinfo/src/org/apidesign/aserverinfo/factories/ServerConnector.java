package org.apidesign.aserverinfo.factories;

import org.apidesign.aserverinfo.spi.ShutdownHandler;
import java.net.URL;
import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;

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
    // factories
    //
    
    // BEGIN: aserverinfo.regularcreate
    public static ServerConnector create(
        NameProvider nameProvider, 
        URLProvider urlProvider, 
        ResetHandler reset
    )
    // END: aserverinfo.regularcreate
    {
        return new ServerConnector(nameProvider, urlProvider, reset, null);
    }
    
    // BEGIN: aserverinfo.regularcreate.withshutdown
    /** @since 2.0 */
    public static ServerConnector create(
        NameProvider nameProvider, 
        URLProvider urlProvider, 
        ResetHandler reset, 
        ShutdownHandler shutdown
    )
    // END: aserverinfo.regularcreate.withshutdown
    {
        return new ServerConnector(nameProvider, urlProvider, reset, shutdown);
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

}
