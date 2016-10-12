
// BEGIN: livedb.connect
@LiveDB(
    classname="Age", password="j1", user="j1",
    query="select * from APP.AGE", 
    url="jdbc:derby:classpath:db"
)
package org.apidesign.livedb.example;
// END: livedb.connect

import org.apidesign.livedb.LiveDB;
