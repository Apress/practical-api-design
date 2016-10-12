package org.apidesign.aserverinfo.spi;

/**
 * Additional interface descrabing capability of a server connection
 * introduced in subsequent version of the API to emulate evolution
 * issues with various implementation of creational patterns.
 *
 * @since 2.0
 * @see org.apidesign.aserverinfo.cloningfactory.ServerConnector
 * @see org.apidesign.aserverinfo.factories.ServerConnector
 * @see org.apidesign.aserverinfo.magicalbagfactory.ServerConnector
 */
public interface ShutdownHandler {
    public void shutdown();
}
