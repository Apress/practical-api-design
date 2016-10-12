package org.apidesign.aserverinfo.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apidesign.aserverinfo.magicalbagfactory.ServerConnector;
import org.apidesign.aserverinfo.spi.NameProvider;
import org.apidesign.aserverinfo.spi.ResetHandler;
import org.apidesign.aserverinfo.spi.URLProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class MagicalBagFactoryTest {

    public MagicalBagFactoryTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void showUseOfMagicalBagFactory() throws Exception {
        Prov provider = new Prov();
        ServerConnector inf;

        // BEGIN: ServerConnector.magicalbag.creation
        InstanceContent magicalContent = new InstanceContent();
        Lookup magicalBag = new AbstractLookup(magicalContent);
        magicalContent.add(provider);
        
        inf = ServerConnector.create(magicalBag);
        // END: ServerConnector.magicalbag.creation
        
        assertEquals("API Design Server", inf.getName());
        assertEquals("http://www.apidesign.org", inf.getURL().toExternalForm());
        inf.reset();
        assertEquals("Once reset", 1, provider.resets);

        provider.resets = 0;
        // BEGIN: ServerConnector.magicalbag.change
        magicalContent.remove(provider);
        inf.reset();
        assertEquals("No reset called now", 0, provider.resets);
        // END: ServerConnector.magicalbag.change
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
                Logger.getLogger(MagicalBagFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        public void reset() {
            resets++;
        }

    }
        
}