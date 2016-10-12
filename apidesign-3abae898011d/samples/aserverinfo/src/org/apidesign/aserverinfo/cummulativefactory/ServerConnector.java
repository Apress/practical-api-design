package org.apidesign.aserverinfo.cummulativefactory;

import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import java.net.URL;
import org.apidesign.aserverinfo.spi.ShutdownHandler;

// BEGIN: aserverinfo.api
/** A class to connect to server, identify it and manipulate with
 * its state. The <a href="http://apidesign.org">Practical API Design</a>
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
// FINISH: aserverinfo.api

    //
    // cumulative factory methods
    //
    
    // BEGIN: aserverinfo.cumulative.factory
    public static ServerConnector empty() {
        return new ServerConnector(null, null, null, null);
    }
    
    public final ServerConnector nameProvider(NameProvider np) {
        return new ServerConnector(np, this.url, this.reset, this.shutdown);
    }

    public final ServerConnector urlProvider(URLProvider up) {
        return new ServerConnector(this.name, up, this.reset, this.shutdown);
    }
    public final ServerConnector reset(ResetHandler h) {
        return new ServerConnector(this.name, this.url, h, this.shutdown);
    }
    /** All one needs to do when there is a need to add new
     * style of creation is to add new <em>clonning</em> method.
     * @param handler
     * @return
     * @since 2.0
     */
    public final ServerConnector shutdown(ShutdownHandler handler) {
        return new ServerConnector(this.name, this.url, this.reset, handler);
    }
    // END: aserverinfo.cumulative.factory

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
