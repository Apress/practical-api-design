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

package org.apidesign.friendpackage.impl;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import junit.framework.TestCase;
import org.apidesign.friendpackage.api.Item;

/**
 *
 * @author Jaroslav Tulach
 */
public class AccessorTest extends TestCase 
implements ChangeListener {

    private int cnt;
    
    public AccessorTest(String testName) {
        super(testName);
    }

    public void testGetTheItemAttachListenerChangeValue() {
        // BEGIN: design.less.friend.use
        Item item = Accessor.getDefault().newItem();
        assertNotNull("Some item is really created", item);
        
        Accessor.getDefault().addChangeListener(item, this);
        // END: design.less.friend.use
        
        item.setValue(10);
        assertEquals("Value is 10", 10, item.getValue());
        cnt = 0;
        item.setValue(7);
        assertEquals("Now it is 7", 7, item.getValue());
        
        assertEquals("There was one change", 1, cnt);
    }

    public void stateChanged(ChangeEvent e) {
        cnt++;
    }
}
