package org.apidesign.aserverinfo.test;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.MalformedURLException;
import java.net.URL;
import org.apidesign.aserverinfo.cummulativefactory.ServerConnector;
import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CummulativeFactoryTest {

    public CummulativeFactoryTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void showUseOfCumulativeFactory() throws Exception {
        Prov p = new Prov();
        NameProvider np = p;
        URLProvider up = p;
        ResetHandler res = p;
        ServerConnector inf;
        
        // BEGIN: ServerConnector.cumulative.creation
        inf = ServerConnector.empty().nameProvider(np).urlProvider(up).reset(res);
        // END: ServerConnector.cumulative.creation
        
        assertEquals("API Design Server", inf.getName());
        assertEquals("http://www.apidesign.org", inf.getURL().toExternalForm());
        inf.reset();
        assertEquals("Once reset", 1, p.resets);
        
    }
    
    @Test
    public void showVerboseUseOfCumulativeFactory() throws Exception {
        Prov prov = new Prov();
        ServerConnector info;
        
        // BEGIN: ServerConnector.cumulative.creation.verbose
        ServerConnector empty = ServerConnector.empty();
        ServerConnector name = empty.nameProvider(prov);
        ServerConnector urlAndName = name.urlProvider(prov);
        info = urlAndName.reset(prov);
        // END: ServerConnector.cumulative.creation.verbose
        
        assertEquals("API Design Server", info.getName());
        assertEquals("http://www.apidesign.org", info.getURL().toExternalForm());
        info.reset();
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
                Logger.getLogger(CummulativeFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        public void reset() {
            resets++;
        }

    }
        
}