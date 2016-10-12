package api.request.response;

import java.util.List;
import java.util.Map;

// BEGIN: grow.request.response
public interface Compute {
    public void computeData(Request request, Response response);
    
    public final class Request {
        // only getters public, rest hidden only for friend code
        Request() {
        }
    }
    
    public final class Response {
        // only setters public, rest available only for friend code
        private final Map<String,String> result;
        /** Allow access only to friend code */
        Response(Map<String,String> result) {
            this.result = result;
        }
        
        public void add(String s) {
            result.put(s, s);
        }
        
        public void addAll(List<String> all) {
            for (String s : all) {
                add(s);
            }
        }

        /** @since 2.0 */
        public void add(String s, String description) {
            result.put(s, description);
        }
    }
}
// END: grow.request.response
