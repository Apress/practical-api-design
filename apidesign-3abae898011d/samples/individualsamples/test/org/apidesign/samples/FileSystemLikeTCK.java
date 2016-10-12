package org.apidesign.samples;

import java.io.File;
import java.io.IOException;
import junit.framework.Test;
import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.NbTestSetup;
import org.netbeans.junit.NbTestSuite;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.JarFileSystem;

public class FileSystemLikeTCK {
    // BEGIN: tck.factory
    public static abstract class FileSystemFactoryHid extends NbTestSetup {
        public FileSystemFactoryHid(Test testToDecorate) {
            super(testToDecorate);
        }
        
        protected abstract FileSystem createFileSystem(
            String testName, String[] resources
        ) throws Exception;
        protected abstract void destroyFileSystem(
            String testName
        ) throws IOException;
    }
    // END: tck.factory
    

    // BEGIN: tck.setup
    public static class JarFileSystemTest extends FileSystemFactoryHid {
        public JarFileSystemTest(Test testToDecorate) {
            super(testToDecorate);
        }

        public static Test suite() {
            NbTestSuite suite = new NbTestSuite();
            suite.addTestSuite(RepositoryTestHid.class);
            suite.addTestSuite(FileSystemTestHid.class);
            suite.addTestSuite(FileObjectTestHid.class);
            return new JarFileSystemTest(suite);
        }

        protected void destroyFileSystem(String testName) throws IOException {
        }

        protected FileSystem createFileSystem(
            String testName, String[] resources
        ) throws Exception {
            JarFileSystem fs = new JarFileSystem();
            fs.setJarFile(createJarFile(testName, resources));
            return fs;
        }
    }
    // END: tck.setup
    
    static File createJarFile(String name, String[] resources) {
        return null;
    }
    
    public static final class RepositoryTestHid extends NbTestCase {
        public RepositoryTestHid(String name) {
            super(name);
        }
        
    }
    public static final class FileSystemTestHid extends NbTestCase {
        public FileSystemTestHid(String name) {
            super(name);
        }
        
    }
    public static final class FileObjectTestHid extends NbTestCase {
        public FileObjectTestHid(String name) {
            super(name);
        }
    }
}
