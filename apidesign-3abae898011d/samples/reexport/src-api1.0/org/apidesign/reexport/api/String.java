package api;

// BEGIN: reexport.String10
public final class String {
    private final char[] chars;
    
    public String(char[] chars) {
        this.chars = chars.clone();
    }
    
    public int length() {
        return chars.length;
    }
    public char charAt(int i) {
        return chars[i];
    }
}
// END: reexport.String10
