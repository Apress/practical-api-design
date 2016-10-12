package api.request.response;

import java.util.List;

public interface Compute {
    public void computeData(Request request, Response response);
    
    /** Right now useless, but may be handy in future */
    public final class Request {
        /** allow access just from friend code */
        Request() {
        }
    }
    
    /** Allows clients to provide the computed strings */
    public final class Response {
        private final List<String> result;
        /** Allow access only to friend code */
        Response(List<String> result) {
            this.result = result;
        }
        
        public void add(String s) {
            result.add(s);
        }
        
        public void addAll(List<String> all) {
            result.addAll(all);
        }
    }
}
