package api;

// BEGIN: forjoe.InterfaceThatJustJoeCanImplement
public abstract class InterfaceThatJustJoeCanImplement {
    protected InterfaceThatJustJoeCanImplement() {
        if (!"impl.joe.JoesImpl".equals(getClass().getName())) {
            throw new IllegalStateException(
                "Sorry, you are not allowed to implement this class"
            );
        }
    }
    
    public abstract void everyoneCallThisJoeWillHandleTheRequest();
}
// END: forjoe.InterfaceThatJustJoeCanImplement
