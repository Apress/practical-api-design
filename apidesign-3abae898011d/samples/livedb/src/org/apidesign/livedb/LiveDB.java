package org.apidesign.livedb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: livedb.connection.annotation
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.SOURCE)
public @interface LiveDB {
    String url();
    String user();
    String password();
    String query();
    String classname();
}
// END: livedb.connection.annotation
