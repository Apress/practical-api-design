package api;

public final class String {
    private final char[] chars;
    
    public String(char[] chars) {
        this.chars = chars.clone();
    }
    
    public int getSize() {
        return chars.length;
    }
    public char charAt(int i) {
        return chars[i];
    }
}
