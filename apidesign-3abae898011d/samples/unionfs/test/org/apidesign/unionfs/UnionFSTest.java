package org.apidesign.unionfs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openide.filesystems.FileObject;
import static org.junit.Assert.*;
import org.openide.filesystems.FileSystem;
import org.xml.sax.SAXException;

public class UnionFSTest {

    public UnionFSTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void union() throws SAXException {
        FileSystem one = UnionFS.fromResource(UnionFSTest.class.getResource("fs-one.xml"));
        assertChildren("There is just one file in the folder", one, "Menu", "Open.instance");
        
        FileSystem two = UnionFS.fromResource(UnionFSTest.class.getResource("fs-two.xml"));
        assertChildren("There is just one file in the other folder", two, "Menu", "Close.instance");
        
        FileSystem union = UnionFS.union(one, two);
        assertChildren("There both in the union", union, "Menu", "Open.instance", "Close.instance");
    }

    private static void assertChildren(String msg, FileSystem fs, String folder, String... expect) {
        Set<String> names = new HashSet<String>();
        names.addAll(Arrays.asList(expect));
        
        FileObject fo = fs.getRoot().getFileObject(folder);
        assertNotNull(msg + " folder " + folder + " has to exist", fo);
        
        for (FileObject ch : fo.getChildren()) {
            names.remove(ch.getNameExt());
        }

        if (!names.isEmpty()) {
            fail(msg + " - expected files not found " + names);
        }
    }

}