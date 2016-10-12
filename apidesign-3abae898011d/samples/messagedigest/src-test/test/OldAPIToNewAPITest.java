/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import org.apidesign.api.security.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** Compares that the MessageDigest and Digest yield the same results for
 * default provider.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class OldAPIToNewAPITest {
    private static byte[] arr;
    private static long time;
    private static byte[] resOld;
    private static byte[] resNew;

    public OldAPIToNewAPITest() {
    }

    @BeforeClass
    public static void setUp() {
        time = System.currentTimeMillis();
        Random r = new Random(time);
        arr = new byte[r.nextInt(1024)];
        r.nextBytes(arr);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void generateHashUsingMessageDigest() throws Exception {
        if (Boolean.getBoolean("no.failures") && Boolean.getBoolean("no.md5")) return;
        // BEGIN: day.end.bridges.BridgeToOldRegister
        // The java.security.Providers cannot be registered in META-INF/services
        // that is why one needs to either configure various properties or
        // make some dummy call that will initialize our bridge class.
        // Then the bridge class registers itself as a MessageDigest provider
        // in its constructor.
        //
        // This is the call:
        Digest initialize = Digest.getInstance("MD5");
        // END: day.end.bridges.BridgeToOldRegister
        
        MessageDigest md = MessageDigest.getInstance("cnt");
        byte[] res = md.digest(arr);
        resOld = res;
    }
    
    
    @Test
    public void generateHashUsingNewDigest() throws Exception {
        Digest d = Digest.getInstance("cnt");
        ByteBuffer bb = ByteBuffer.wrap(arr);
        byte[] res = d.digest(bb);
        resNew = res;
    }


    
    @Test
    public void compareTheHashes() throws Exception {
        if (Boolean.getBoolean("no.failures") && Boolean.getBoolean("no.md5")) return;
        if (!Arrays.equals(resOld, resNew)) {
            fail("Arrays are different:\n" + Arrays.toString(resOld) + "\n" + Arrays.toString(resNew));
        }
    }
}