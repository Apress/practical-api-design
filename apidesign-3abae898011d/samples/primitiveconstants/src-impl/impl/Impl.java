package impl;
import api.API;

// BEGIN: theory.binary.constants.impl
public class Impl extends API {
    protected void init(int version) throws IllegalStateException {
        if (version != API.VERSION) {
          throw new IllegalStateException("Wrong API version error!");
        }
    }
// FINISH: theory.binary.constants.impl
    
    public static void main(String[] args) {
        System.err.println("main expects version: " + API.VERSION);
        new Impl();
        System.err.println("done for version: " + API.VERSION);
    }
}
