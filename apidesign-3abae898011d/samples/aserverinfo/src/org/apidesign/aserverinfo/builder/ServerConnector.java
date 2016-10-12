package org.apidesign.aserverinfo.builder;

import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import java.net.URL;
import org.apidesign.aserverinfo.spi.ShutdownHandler;

// BEGIN: aserverinfo.builder.api
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
// FINISH: aserverinfo.builder.api

    //
    // private part
    //

    private final NameProvider name;
    private final URLProvider url;
    private final ResetHandler reset;
    private final ShutdownHandler shutdown;

    ServerConnector(
        NameProvider name, URLProvider url,
        ResetHandler reset, ShutdownHandler shutdown
    ) {
        this.name = name;
        this.url = url;
        this.reset = reset;
        this.shutdown = shutdown;
    }
}
