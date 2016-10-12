package query;

import api.String;

// BEGIN: reexport.wrap1.0
public final class Query {
    public static final class Sequence {
        private String data;
        
        Sequence(String data) {
            this.data = data;
        }

        public int length() {
            return data.length();
        }

        public char charAt(int i) {
            return data.charAt(i);
        }
    }
    
    public Sequence computeReply() {
        char[] hello = { 'H', 'e', 'l', 'l', 'o' };
        return new Sequence(new String(hello));
    }
}
// END: reexport.wrap1.0
