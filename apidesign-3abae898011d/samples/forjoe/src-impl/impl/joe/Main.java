
package impl.joe;

import api.InterfaceThatJustJoeCanImplement;

public class Main {
    public static void main(String[] args) throws Exception {
        InterfaceThatJustJoeCanImplement i = new JoesImpl();
        
        i.everyoneCallThisJoeWillHandleTheRequest();
    }
}
