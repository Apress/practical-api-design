package org.apidesign.infra.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.FilterSet;

/**
 *
 * @author Jaroslav Tulach
 */
public final class GrepFilter extends Task {
    private int len = 80;
    private String id;
    private FileSet fs;
    private Pattern begin = Pattern.compile(".* BEGIN: *(\\p{Graph}+)[-\\> ]*");
    private Pattern end = Pattern.compile(".* (END|FINISH): *(\\p{Graph}+)[-\\> ]*");
    private boolean openoffice;
    private Map<String,String> paths = new HashMap<String, String>();
    
    
    public FileSet createFileSet() {
        if (fs != null) {
            throw new BuildException();
        }
        this.fs = new FileSet();
        return fs;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setMaxLineLength(int len) {
        this.len = len;
    }
    
    public void setOutputFormat(String f) {
        if (f.equals("opendocument")) {
            openoffice = true;
            return;
        }
        throw new BuildException("Wrong format: " + f);
    }

    final FilterSet createFilterSet() {
        if (fs == null) {
            throw new BuildException();
        }
        
        FilterSet filter = new FilterSet();

        try {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            Map<String,CharSequence> texts = new HashMap<String, CharSequence>();
            for (String path : ds.getIncludedFiles()) {
                File file = new File(ds.getBasedir(), path);
                BufferedReader r = new BufferedReader(new FileReader(file));
                for (;;) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    {
                        Matcher m = begin.matcher(line);
                        if (m.matches()) {
                            Item sb = new Item(file);
                            CharSequence prev = texts.put(m.group(1), sb);
                            if (prev != null) {
                                throw new BuildException("Same pattern is there twice: " + m.group(1) + " in " + file);
                            }
                            continue;
                        }
                    }
                    {
                        Matcher m = end.matcher(line);
                        if (m.matches()) {
                            CharSequence s = texts.get(m.group(2));
                            if (s instanceof Item) {
                                texts.put(m.group(2), ((Item)s).toString(m.group(1).equals("FINISH")));
                                continue;
                            }
                            
                            if (s == null) {
                                throw new BuildException("Closing unknown section: " + m.group(2) + " in " + file);
                            }
                            throw new BuildException("Closing not opened section: " + m.group(2) + " in " + file);
                        }
                    }
                    
                    for (CharSequence charSequence : texts.values()) {
                        if (charSequence instanceof Item) {
                            Item sb = (Item)charSequence;
                            sb.append(line);
                        }
                    }
                }
 
                for (Map.Entry<String, CharSequence> entry : texts.entrySet()) {
                    CharSequence v = entry.getValue();
                    if (v instanceof Item) {
                        throw new BuildException("Not closed section " + entry.getKey() + " in " + file);
                    }
                    entry.setValue(v.toString());
                    if (!paths.containsKey(entry.getKey())) {
                        paths.put(entry.getKey(), path);
                    }
                }
            }
            
            for (Map.Entry<String, CharSequence> entry : texts.entrySet()) {
                String text = entry.getValue().toString();
                String out = linize(text);
                filter.addFilter(entry.getKey(), out); // NOI18N
            }
        } catch (IOException ex) {
            throw new BuildException(ex);
        }
        if (!filter.hasFilters()) {
            throw new BuildException("No filter found!");
        }
        return filter;
    }

    @Override
    public void execute() throws BuildException {
        if (id == null) {
            throw new BuildException();
        }
        FilterSet filter = createFilterSet();
        getProject().addReference(id, filter);
    }

    final URL getPath(URL root, String key) throws MalformedURLException {
        return new URL(root, paths.get(key));
    }
    
    private String linize(String input) {
        if (!openoffice) {
            return input;
        }
        
        StringBuilder copy = new StringBuilder();
        for (String l : input.split("\n")) {
            int spaces = 0;
            while (spaces < l.length() && l.charAt(spaces) == ' ') {
                spaces++;
            }
            copy.append("<text:p text:style-name='Code'><text:s text:c='" + spaces + "'/>" + l.substring(spaces) + "</text:p>\n");
        }
        
        return copy.toString();
    }
    
    static final int countChar(CharSequence seq, char ch) {
        int cnt = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (ch == seq.charAt(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    private final class Item implements CharSequence {
        private StringBuilder sb = new StringBuilder();
        private int spaces = Integer.MAX_VALUE;
        private Stack<Integer> remove = new Stack<Integer>();
        private final File file;

        public Item(File file) {
            this.file = file;
        }

        public int length() {
            return sb.length();
        }

        public char charAt(int index) {
            return sb.charAt(index);
        }

        public CharSequence subSequence(int start, int end) {
            return sb.subSequence(start, end);
        }

        private void append(String line) {
            for (int sp = 0; sp < line.length(); sp++) {
                if (line.charAt(sp) != ' ') {
                    if (sp < spaces) {
                        spaces = sp;
                        break;
                    }
                }
            }
            remove.push(sb.length());
            sb.append(line);
            sb.append('\n');
        }

        @Override
        public String toString() {
            return toString(false);
        }
        public String toString(boolean finish) {
            if (remove != null) {
                while (!remove.isEmpty()) {
                   Integer pos = remove.pop();
                   for (int i = 0; i < spaces; i++) {
                       if (sb.charAt(pos) == '\n') {
                           break;
                       }
                       sb.deleteCharAt(pos);
                   }
                }
                remove = null;
                
                int line = 0;
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '\n') {
                        line = 0;
                        continue;
                    }
                    if (++line > len) {
                        throw new BuildException("Line is too long in: " + file + "\n" + sb);
                    }
                }
                
                int open = countChar(sb, '{');
                int end = countChar(sb, '}');
                if (finish) {
                    for (int i = 0; i < open - end; i++) {
                        sb.append("}\n");
                    }
                }
                
                if (countChar(sb, '{') != countChar(sb, '}')) {
                    throw new BuildException("not paired amount of braces in " + file + "\n" + sb);
                }
                
            }
            return sb.toString();
        }
    } // end of Item
}
