/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apidesign.openfixed;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
interface EventSupport {

    public void fireModificationEvent(ModificationEvent ev);

    public void add(ModificationListener l);

    public void remove(ModificationListener l);
    
}
