/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.spi.security;

import java.nio.ByteBuffer;

/**
 *
 * @author  Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.Digestor
public abstract class Digestor<Data> {
   protected abstract byte[] digest(Data data);
   protected abstract Data create(String algorithm); 
   protected abstract void update(Data data, ByteBuffer input);
   
// FINISH: day.end.bridges.Digestor   
   
   
   static {
       // initializes the accessor, so the api package can call protected
       // methods from this class
       new DigestorAccessorImpl();
   }
}

