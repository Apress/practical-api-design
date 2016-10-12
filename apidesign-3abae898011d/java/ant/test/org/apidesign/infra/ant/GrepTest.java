/*
 * Copyright 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package org.apidesign.infra.ant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Test;
import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.NbTestSuite;

/**
 *
 * @author Jaroslav Tulach
 */
public class GrepTest extends NbTestCase {
    private static File workDir;
    
    public GrepTest(String s) {
        super(s);
    }
    
    public static Test suite() {
        return new NbTestSuite(GrepTest.class);
        //return new GrepTest("testInXML");
    }

    @Override
    protected void setUp() throws Exception {
        clearWorkDir();
    }

    public void testMissingMethodInAnInterfaceIsDetected() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "// BEGIN: xyz\n" +
            "public interface I {\n" +
            "// FINISH: xyz\n" +
            "  public void get();\n" +
            "}" +
            "";
        File src = createFile(1, "I.java", c1);
        
        
        String c2 =
            "@xyz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        assertEquals("public interface I {\n}\n", r);
    }
    
    public void testSpacesAtBeginingAreStripped() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "// BEGIN: xyz\n" +
            "   public interface I {\n" +
            "     public void ahoj();\n" +
            "   }\n" +
            "// END: xyz\n" +
            "  public void get();\n" +
            "}" +
            "";
        File src = createFile(1, "I.java", c1);
        
        
        String c2 =
            "@xyz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        String result = "public interface I {\n" +
            "  public void ahoj();\n" +
            "}\n";
        assertEquals(result, r);
    }
    
    public void testReportUnpairedBracesAsError() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "// BEGIN: xyz\n" +
            "   public interface I {\n" +
            "     public void ahoj();\n" +
            "// END: xyz\n" +
            "   }\n" +
            "  public void get();\n" +
            "}" +
            "";
        File src = createFile(1, "I.java", c1);
        
        
        String c2 =
            "@xyz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        try {
            execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        } catch (ExecuteUtils.ExecutionError ex) {
            // ok
            return;
        }
        fail("The execution of the script shall fail");
    }
        
    public void testIncludedTexts() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "// BEGIN: clazz\n" +
            "public interface I {\n" +
            "  // BEGIN: method\n" +
            "  public void get();\n" +
            "  // END: method\n" +
            "}\n" +
            "// END: clazz\n" +
            "";
        File src = createFile(1, "I.java", c1);
        
        
        String c2 =
            "@clazz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        if (r.indexOf("BEGIN") >= 0) {
            fail("BEGIN is there: " + r);
        }
        if (r.indexOf("END") >= 0) {
            fail("END is there: " + r);
        }
        if (r.indexOf("interface I") < 0) {
            fail("Missing interface: " + r);
        }
        if (r.indexOf("void get()") < 0) {
            fail("Missing get: " + r);
        }
    }
    public void testIncludedTextsAmper() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "public class C {\n" +
            "  // BEGIN: method\n" +
            "  public void change(int x) { x &= 10; }\n" +
            "  // END: method\n" +
            "}\n" +
            "";
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@method@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        if (r.indexOf("&=") < 0) {
            fail("No XML: " + r);
        }
        if (r.indexOf("&amp;=") >= 0) {
            fail("No XML, we need &amp;: " + r);
        }
    }
    public void testIncludedTextsAmperAndGenerics() throws Exception {
        String c1 =

"package org.apidesign.api.security;\n" +
"" +
"import java.nio.ByteBuffer;\n" +
"import java.util.ServiceLoader;\n" +
"import org.apidesign.spi.security.Digestor;\n" +
"\n" +
"/** Simplified version of a Digest class that allows to compute a fingerprint\n" +
" * for buffer of data.\n" +
" *\n" +
" * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>\n" +
" */\n" +
"// BEGIN: day.end.bridges.Digest\n" +
"public final class Digest {\n" +
"    private final DigestImplementation<?> impl;\n" +
"    \n" +
"    /** Factory method is better than constructor */\n" +
"    private Digest(DigestImplementation<?> impl) {\n" +
"        this.impl = impl;\n" +
"    }\n" +
"    \n" +
"    /** Factory method to create digest for an algorithm.\n" +
"     */\n" +
"    public static Digest getInstance(String algorithm) {\n" +
"        for (Digestor<?> digestor : ServiceLoader.load(Digestor.class)) {\n" +
"            DigestImplementation<?> impl = \n" +
                "DigestImplementation.create(digestor, algorithm);\n" +
"            if (impl != null) {\n" +
"                return new Digest(impl);\n" +
"            }\n" +
"        }\n" +
"        throw new IllegalArgumentException(algorithm);\n" +
"    }\n" +
"      \n" +
"    //\n" +
"    // these methods are kept the same as in original MessageDigest,\n" +
"    // but for simplicity choose just some from the original API\n" +
"    //\n" +
"    \n" +
"    public byte[] digest(ByteBuffer bb) {\n" +
"        return impl.digest(bb);\n" +
"    }\n" +
"}\n" +
"// END: day.end.bridges.Digest\n";
        
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@day.end.bridges.Digest@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        if (r.indexOf("&=") >= 0) {
            fail("Wrong XML: " + r);
        }
        if (r.indexOf("&amp;") > -1) {
            fail("Wrong XML, no &amp;: " + r);
        }
    }
    
    public void testInXML() throws Exception {
        String c1 =
            "<!-- BEGIN: clazz -->\n" +
            "<interface name='I'/>\n" +
            "<!-- END: clazz -->\n" +
            "";
        File src = createFile(1, "I.xml", c1);
        
        
        String c2 =
            "@clazz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.xml");
        
        assertTrue("output created", out.canRead());
        
        String r = readFile(out);
        if (r.indexOf("BEGIN") >= 0) {
            fail("BEGIN is there: " + r);
        }
        if (r.indexOf("END") >= 0) {
            fail("END is there: " + r);
        }
        if (r.indexOf("<interface name='I'/>") < 0) {
            fail("Missing interface: " + r);
        }
    }
    
    public void testLongLineNotDetectedAsBeginsWithGen() throws Exception {
        String c1 =
"package org.apidesign.api.security;\n" +
"" +
"import java.nio.ByteBuffer;\n" +
"import java.util.ServiceLoader;\n" +
"import org.apidesign.spi.security.Digestor;\n" +
"\n" +
"/** Simplified version of a Digest class that allows to compute a fingerprint\n" +
"// BEGIN: x\n" +
" * for buffer of data.\n" +
"// END: x\n" +
" *\n" +
" * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>\n" +
" */\n" +
"// GEN-BEGIN: day.end.bridges.Digest\n" +
"d;   DigestImplementation<?> impl = DigestImplementation.create(digestor, algorithm);\n" +
"// GEN-END: day.end.bridges.Digest\n";
        
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@day.end.bridges.Digest@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
 
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
    }
    
    
    public void testLongLineDetected() throws Exception {
        String c1 =
"package org.apidesign.api.security;\n" +
"" +
"import java.nio.ByteBuffer;\n" +
"import java.util.ServiceLoader;\n" +
"import org.apidesign.spi.security.Digestor;\n" +
"\n" +
"/** Simplified version of a Digest class that allows to compute a fingerprint\n" +
" * for buffer of data.\n" +
" *\n" +
" * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>\n" +
" */\n" +
"// BEGIN: day.end.bridges.Digest\n" +
"d;   DigestImplementation<?> impl = DigestImplementation.create(digestor, algorithm);\n" +
"// END: day.end.bridges.Digest\n";
        
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@day.end.bridges.Digest@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
 
        try {
            execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
        } catch (ExecuteUtils.ExecutionError ex) {
            // OK
            return;
        }
        fail("Should fail, as there is long line");
    }
    public void testLongNotLineDetectedAsShortened() throws Exception {
        String c1 =
"package org.apidesign.api.security;\n" +
"" +
"import java.nio.ByteBuffer;\n" +
"import java.util.ServiceLoader;\n" +
"import org.apidesign.spi.security.Digestor;\n" +
"\n" +
"/** Simplified version of a Digest class that allows to compute a fingerprint\n" +
" * for buffer of data.\n" +
" *\n" +
" * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>\n" +
" */\n" +
"// BEGIN: day.end.bridges.Digest\n" +
"   DigestImplementation<?> impl = DigestImplementation.create(digestor, algorithm);\n" +
"// END: day.end.bridges.Digest\n";
        
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@day.end.bridges.Digest@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
 
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
    }
    
    public void testNotClosedSection() throws Exception {
        String c1 =
            "package ahoj;\n" +
            "// BEGIN: clazz\n" +
            "int x;\n" +
            "\n";
        File src = createFile(1, "I.java", c1);
        
        
        String c2 =
            "@clazz@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
        
        try {
            execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
            fail("Has to fail");
        } catch (ExecuteUtils.ExecutionError ex) {
            // ok
        }
    }
    public void testLongNotLineDetectedAsNotInTheList() throws Exception {
        String c1 =
"package org.apidesign.api.security;\n" +
"" +
"import java.nio.ByteBuffer;\n" +
"import java.util.ServiceLoader;\n" +
"import org.apidesign.spi.security.Digestor;\n" +
"\n" +
"/** Simplified version of a Digest class that allows to compute a fingerprint\n" +
" * for buffer of data.\n" +
" *\n" +
" * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>\n" +
" */\n" +
"   DigestImplementation<?> impl    =      DigestImplementation.create(digestor, algorithm);\n" +
"// BEGIN: day.end.bridges.Digest\n" +
"   DigestImplementation<?> impl    =      null\n" +
"// END: day.end.bridges.Digest\n";
        
        File src = createFile(1, "C.java", c1);
        
        
        String c2 =
            "@day.end.bridges.Digest@";
        File txt = createFile(2, "in.txt", c2);
        
        File out = createFile(3, "out.txt", "");
        out.delete();
 
        execute(1, 2, "-Dfile1=" + txt, "-Dfile2=" + out, "-Dinclude1=*.java");
    }
    
    protected final File createFile(int slot, String name, String content) throws Exception {
        File d1 = new File(getWorkDir(), "dir" + slot);
        File c1 = new File(d1, name);
        copy(content, c1);
        return c1;
    }
    
    protected final void execute(int slotFirst, int slotSecond, String... additionalArgs) throws Exception {
        File d1 = new File(getWorkDir(), "dir" + slotFirst);
        File d2 = new File(getWorkDir(), "dir" + slotSecond);
        
        File build = new File(getWorkDir(), "build.xml");
        extractResource("build.xml", build);
        
        List<String> args = new ArrayList<String>();
        args.addAll(Arrays.asList(additionalArgs));
        args.add("-Ddir1=" + d1);
        args.add("-Ddir2=" + d2);
        ExecuteUtils.execute(build, args.toArray(new String[0]));
    }
    
    private static final void copy(String txt, File f) throws Exception {
        f.getParentFile().mkdirs();
        FileWriter w = new FileWriter(f);
        w.append(txt);
        w.close();
    }

    final File extractResource(String res, File f) throws Exception {
        URL u = GrepTest.class.getResource(res);
        assertNotNull ("Resource should be found " + res, u);
        
        FileOutputStream os = new FileOutputStream(f);
        InputStream is = u.openStream();
        for (;;) {
            int ch = is.read ();
            if (ch == -1) {
                break;
            }
            os.write (ch);
        }
        os.close ();
            
        return f;
    }
    
    final static String readFile (java.io.File f) throws java.io.IOException {
        int s = (int)f.length ();
        byte[] data = new byte[s];
        assertEquals ("Read all data", s, new java.io.FileInputStream (f).read (data));
        
        return new String (data);
    }
    
    final File extractString (String res, String nameExt) throws Exception {
        File f = new File(getWorkDir(), nameExt);
        f.deleteOnExit ();
        
        FileOutputStream os = new FileOutputStream(f);
        InputStream is = new ByteArrayInputStream(res.getBytes("UTF-8"));
        for (;;) {
            int ch = is.read ();
            if (ch == -1) {
                break;
            }
            os.write (ch);
        }
        os.close ();
            
        return f;
    }
    
}