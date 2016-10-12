/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Jaroslav Tulach. Portions Copyright 2007 Jaroslav Tulach. 
 * All Rights Reserved.
 */
package conditionaluseofapi;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        // BEGIN: theory.binary.overloads.init
        AddString add;

        try {
            Class onlyOn15 = Class.forName("java.lang.StringBuilder");
            Class codeOn15 = Class.forName(
                "conditionaluseofapi.StringBuilderAdd15"
            );
            add = (AddString)codeOn15.newInstance();
        } catch (ClassNotFoundException ex) {
            add = new StringBufferAdd();
        }
        // END: theory.binary.overloads.init
        
        add.addString("Hello");
        add.addString(" ");
        add.addString("World!");
        
        System.out.println(add.getMessage());
        System.out.println("printed with: " + add);
    }

}
