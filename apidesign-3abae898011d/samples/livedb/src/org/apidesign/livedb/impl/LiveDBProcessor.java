package org.apidesign.livedb.impl;

import java.io.IOException;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.Completions;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import org.apidesign.livedb.LiveDB;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: livedb.processor
@SupportedAnnotationTypes("org.apidesign.livedb.LiveDB")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@ServiceProvider(service=Processor.class)
public final class LiveDBProcessor extends AbstractProcessor {
    @Override
    public boolean process(
        Set<? extends TypeElement> annotations, RoundEnvironment roundEnv
    ) {
        final Filer filer = processingEnv.getFiler();
        for (Element e : roundEnv.getElementsAnnotatedWith(LiveDB.class)) {
            LiveDB db = e.getAnnotation(LiveDB.class);
            PackageElement pe = (PackageElement)e;
            String clsName = pe.getQualifiedName() + "." + db.classname();
            try {
                JavaFileObject src = filer.createSourceFile(clsName, pe);
                Writer w = src.openWriter();
                Connection c = getConnection(
                    db.url(), db.user(), db.password()
                );
                CallableStatement s = c.prepareCall(db.query());
                ResultSet rs = s.executeQuery();
                ResultSetMetaData md = rs.getMetaData();
                generateClassHeader(w, pe, db);
                w.append("class " + db.classname() + " {\n");
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    generateField(w, md, i);
                }
                generateConstructor(w, db, md);
                generateQueryMethod(w, db, md);
                w.append("}");
                w.close();
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        return true;
    }
// FINISH: livedb.processor

    private void generateQueryMethod(Writer w, LiveDB db, ResultSetMetaData md) throws SQLException, IOException {
        w.append("  public static List<" + db.classname() + "> ")
         .append("query() throws SQLException {\n");
        w.append("    Connection c = DriverManager.getConnection(\"")
         .append(db.url()).append("\", \"")
         .append(db.user()).append("\", \"")
         .append(db.password()).append("\");\n");
        w.append("    List<").append(db.classname())
         .append("> res = new ArrayList<")
         .append(db.classname()).append(">();\n");
        w.append("    CallableStatement s = c.prepareCall(\"")
         .append(db.query()).append("\");\n");
        w.append("    ResultSet rs = s.executeQuery();\n");
        w.append("    ResultSetMetaData md = rs.getMetaData();\n");
        w.append("    while (rs.next()) {\n");
        w.append("      res.add(new " + db.classname() + "(\n");
        for (int i = 1; i <= md.getColumnCount(); i++) {
            w.append("        (")
             .append(md.getColumnClassName(i))
             .append(")rs.getObject(" + i).append(")");
            if (i < md.getColumnCount()) {
                w.append(",\n");
            } else {
                w.append("\n");
            }
        }
        w.append("      ));\n");
        w.append("    };\n");
        w.append("    return res;\n");
        w.append("  }");
    }

    private void generateConstructor(Writer w, LiveDB db, ResultSetMetaData md) throws SQLException, IOException {
        w.append("  private " + db.classname() + "(\n");
        for (int i = 1; i <= md.getColumnCount(); i++) {
            w.append("    ").append(md.getColumnClassName(i))
             .append(" ").append(md.getColumnName(i));
            if (i < md.getColumnCount()) {
                w.append(",\n");
            } else {
                w.append("\n");
            }
        }
        w.append("  ) {\n");
        for (int i = 1; i <= md.getColumnCount(); i++) {
            w.append("    this.")
             .append(md.getColumnName(i))
             .append(" = ")
             .append(md.getColumnName(i))
             .append(";\n");
        }
        w.append("  }\n");
    }

    private void generateField(Writer w, ResultSetMetaData md, int i) throws IOException, SQLException {
        w.append("  public final ")
         .append(md.getColumnClassName(i))
         .append(" ")
         .append(md.getColumnName(i))
         .append(";\n");
    }

    private void generateClassHeader(Writer w, PackageElement pe, LiveDB db) throws IOException {
        w.append("package " + pe.getQualifiedName() + ";\n");
        w.append("import java.util.List;\n");
        w.append("import java.util.ArrayList;\n");
        w.append("import java.sql.*;\n");
    }
    private static Connection getConnection(String url, String user, String password) 
    throws SQLException {
        final ClassLoader cl = LiveDBProcessor.class.getClassLoader();
        for (Driver d : ServiceLoader.load(Driver.class, cl)) {
//            System.out.println("looked up: " + d);
            if (d.acceptsURL(url)) {
                //System.out.println("accepts: " + d);
                Properties p = new Properties();
                p.put("user", user);
                p.put("password", password);
                return d.connect(url, p);
            }
        }
        throw new SQLException("No driver found for " + url);
    }

    
    
    // BEGIN: livedb.completions
    @Override
    public Iterable<? extends Completion> getCompletions(
        Element element, AnnotationMirror annotation, 
        ExecutableElement member, String userText
    ) {
        if (!"query".equals(member.getSimpleName().toString())) {
            return Collections.emptyList();
        }
        if (userText == null || userText.length() <= 1) {
            return Collections.singleton(Completions.of("\"SELECT "));
        }
        if (userText.toUpperCase().matches(".*FROM *")) {
            String user = extractValue(annotation, "user");
            String password = extractValue(annotation, "password");
            String url = extractValue(annotation, "url");
            if (user == null || password == null || url == null) {
                return Collections.emptyList();
            }
            try {
                List<Completion> arr = new ArrayList<Completion>();
                Connection c = getConnection(url, user, password);
                DatabaseMetaData meta = c.getMetaData();
                ResultSet res = meta.getTables(null, null, "%", null);
                boolean ok = res.first();
                while (ok) {
                    String txt = userText + res.getString("TABLE_NAME");
                    arr.add(Completions.of(txt));
                    ok = res.next();
                }
                return arr;
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        return Collections.emptyList();
    }
    // END: livedb.completions
    
    private static String extractValue(AnnotationMirror am, String param) {
        AnnotationValue av = null;
        for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet()) {
            if (entry.getKey().toString().equals(param + "()")) {
                av = entry.getValue();
                break;
            }
        }
        if (av == null) {
            return null;
        }
        String s = av.toString();
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
