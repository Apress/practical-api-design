package org.apidesign.infra.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.FilterSet;

public class GrepCopy extends Task {
    private GrepFilter filter = new GrepFilter();
    private File dir;
    private URL url;
    
    public GrepCopy() {
    }

    public FileSet createFileSet() {
        return filter.createFileSet();
    }

    public void setTarget(File dir) {
        this.dir = dir;
    }
    
    public void setBaseURL(URL url) {
        this.url = url;
    }

    @Override
    public void execute() throws BuildException {
        filter.setProject(getProject());

        FilterSet set = filter.createFilterSet();

        for (Object object : set.getFilterHash().entrySet()) {

            FileWriter w = null;
            try {
                Entry en = (Entry) object;
                String key = (String)en.getKey();
                {
                    File to = new File(dir, key);
                    to.getParentFile().mkdirs();
                    w = new FileWriter(to);
                    w.write((String) en.getValue());
                    w.close();
                }
                
                if (url != null) {
                    URL u = filter.getPath(url, key);
                    File to = new File(dir, key + ".url");
                    to.getParentFile().mkdirs();
                    w = new FileWriter(to);
                    w.write(u.toExternalForm());
                    w.close();
                }
                
            } catch (IOException ex) {
                throw new BuildException(ex);
            }
        }
    }
}
