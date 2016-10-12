/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apidesign.openfixed;

import java.util.EventObject;
// BEGIN: openfixed.postevent
public final class PostModificationEvent extends EventObject {
    PostModificationEvent(Calculator calc) {
        super(calc);
    }
}
// END: openfixed.postevent
