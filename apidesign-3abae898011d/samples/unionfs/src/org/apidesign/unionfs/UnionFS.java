package org.apidesign.unionfs;

import java.net.URL;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.MultiFileSystem;
import org.openide.filesystems.XMLFileSystem;
import org.xml.sax.SAXException;

public final class UnionFS {
    private UnionFS() {
    }
    
    public static FileSystem union(FileSystem... toUnion) {
        return new MultiFileSystem(toUnion);
    }
    
    public static FileSystem fromResource(URL stream) throws SAXException {
        return new XMLFileSystem(stream);
    }
}
