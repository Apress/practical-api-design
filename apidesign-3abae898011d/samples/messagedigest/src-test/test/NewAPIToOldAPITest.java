package test;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import org.apidesign.api.security.Digest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** Compares that the MessageDigest and Digest yield the same results for
 * default provider.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class NewAPIToOldAPITest {
    private static byte[] arr;
    private static long time;
    private static byte[] resOld;
    private static byte[] resNew;

    public NewAPIToOldAPITest() {
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
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] res = md.digest(arr);
        resOld = res;
    }

    @Test
    public void generateHashUsingNewDigest() throws Exception {
        if (Boolean.getBoolean("no.failures") && Boolean.getBoolean("no.md5")) return;
        Digest d = Digest.getInstance("MD5");
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