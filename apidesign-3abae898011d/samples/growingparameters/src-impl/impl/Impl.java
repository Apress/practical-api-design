package impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Impl {
    public static void main(String[] args) {
        System.err.println("showUsageOfAbstractClassAPI:");
        showUsageOfAbstractClassAPI();
        System.err.println("showUsageOfRequestResponseAPI:");
        showUsageOfRequestResponseAPI();
    }
    
    private static void assertEquals(String msg, int expected, int real) {
        System.err.println("    " + msg + " expected: " + expected + " was: " + real);
        assert expected == real;
    }
    
    private static void showUsageOfAbstractClassAPI() {
        class ProviderWrittenAgainstVersion1 extends api.classes.Compute {
            @Override
            public List<String> getData() {
                return Collections.singletonList("Hello");
            }
        }
        int index1 = api.classes.Support.searchByDescription("Hello", new ProviderWrittenAgainstVersion1());
        assertEquals("Hello was found", 0, index1);
        int index2 = api.classes.Support.searchByDescription("Unknown", new ProviderWrittenAgainstVersion1());
        assertEquals("Unknown was not found", -1, index2);
        
        class ProviderWrittenAgainstVersion2 extends api.classes.Compute {
            @Override
            public List<String> getData() {
                return Collections.singletonList("Hello");
            }

            @Override
            public Map<String, String> getDataAndDescription() {
                return Collections.singletonMap("Hello", "Says hello");
            }
        }

        int index3 = api.classes.Support.searchByDescription("Says hello", new ProviderWrittenAgainstVersion2());
        assertEquals("Found by description", 0, index3);
    }
    
    
    
    private static void showUsageOfRequestResponseAPI() {
        class ProviderWrittenAgainstVersion1 implements api.request.response.Compute {
            public void computeData(Request request, Response response) {
                response.add("Hello");
            }
        }
        int index1 = api.request.response.Support.searchByDescription("Hello", new ProviderWrittenAgainstVersion1());
        assertEquals("Hello found", 0, index1);
        int index2 = api.request.response.Support.searchByDescription("Unknown", new ProviderWrittenAgainstVersion1());
        assertEquals("Unknown not found", -1, index2);
        
        class ProviderWrittenAgainstVersion2 implements api.request.response.Compute {
            public void computeData(Request request, Response response) {
                response.add("Hello", "Says hello");
            }
        }

        int index3 = api.request.response.Support.searchByDescription("Says hello", new ProviderWrittenAgainstVersion2());
        assertEquals("Description found", 0, index3);
        assert index3 == 0;
    }
}
