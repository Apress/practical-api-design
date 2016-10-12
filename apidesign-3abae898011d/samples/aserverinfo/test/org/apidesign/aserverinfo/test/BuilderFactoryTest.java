package org.apidesign.aserverinfo.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apidesign.aserverinfo.builder.ServerConnector;
import org.apidesign.aserverinfo.builder.ServerInfo;
import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BuilderFactoryTest {

    public BuilderFactoryTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void showUseOfBuilder() throws Exception {
        Prov p = new Prov();
        NameProvider np = p;
        URLProvider up = p;
        ResetHandler res = p;
        ServerConnector connection;
        
        // BEGIN: ServerConnector.builder.creation
        connection = ServerInfo.empty()
                .nameProvider(np).urlProvider(up).reset(res).connect();
        // END: ServerConnector.builder.creation
        
        assertEquals("API Design Server", connection.getName());
        assertEquals("http://www.apidesign.org", connection.getURL().toExternalForm());
        connection.reset();
        assertEquals("Once reset", 1, p.resets);
        
    }
    
    @Test
    public void showVerboseUseOfBuilder() throws Exception {
        Prov prov = new Prov();
        ServerConnector connection;
        
        // BEGIN: ServerConnector.builder.creation.verbose
        ServerInfo empty = ServerInfo.empty();
        ServerInfo name = empty.nameProvider(prov);
        ServerInfo urlAndName = name.urlProvider(prov);
        ServerInfo all = urlAndName.reset(prov);
        connection = all.connect();
        // END: ServerConnector.builder.creation.verbose
        
        assertEquals("API Design Server", connection.getName());
        assertEquals("http://www.apidesign.org", connection.getURL().toExternalForm());
        connection.reset();
        assertEquals("Once reset", 1, prov.resets);
        
    }
    
    
    private static class Prov implements NameProvider, URLProvider, ResetHandler {
        int resets;

        public String getName() {
            return "API Design Server";
        }

        public URL getURL() {
            try {
                return new URL("http://www.apidesign.org");
            } catch (MalformedURLException ex) {
                Logger.getLogger(BuilderFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        public void reset() {
            resets++;
        }

    }
        
}