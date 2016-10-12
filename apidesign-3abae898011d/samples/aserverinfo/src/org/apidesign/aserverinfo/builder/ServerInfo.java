package org.apidesign.aserverinfo.builder;

import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.ShutdownHandler;
import org.apidesign.aserverinfo.spi.URLProvider;

/**
 * Mutable "setter" methods for the builder pattern.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: aserverinfo.builder.factory
public class ServerInfo {

    public static ServerInfo empty() {
        return new ServerInfo(null, null, null, null);
    }

    public final ServerInfo nameProvider(NameProvider np) {
        this.name = np;
        return this;
    }

    public final ServerInfo urlProvider(URLProvider up) {
        this.url = up;
        return this;
    }
    // BEGIN: aserverinfo.builder.setter
    public final ServerInfo reset(ResetHandler h) {
        this.reset = h;
        return this;
    }
    // END: aserverinfo.builder.setter
    
    /** All one needs to do when there is a need to add new
     * style of creation is to add new method for a builder.
     * @param handler
     * @return
     * @since 2.0
     */
    public final ServerInfo shutdown(ShutdownHandler handler) {
        this.shutdown = handler;
        return this;
    }
    
    /** Creates the server connector based on current values in the 
     * info. Builder factory method.
     * @return server connector
     */
    public final ServerConnector connect() {
        return new ServerConnector(name, url, reset, shutdown);
    }
    // FINISH: aserverinfo.builder.factory

    private NameProvider name;
    private URLProvider url;
    private ResetHandler reset;
    private ShutdownHandler shutdown;

    private ServerInfo(
        NameProvider name, URLProvider url,
        ResetHandler reset, ShutdownHandler shutdown
    ) {
        this.name = name;
        this.url = url;
        this.reset = reset;
        this.shutdown = shutdown;
    }

}
