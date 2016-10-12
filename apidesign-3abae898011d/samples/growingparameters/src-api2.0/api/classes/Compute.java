package api.classes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// BEGIN: grow.compute
public abstract class Compute {
    /**
     * @return list of strings to work with 
     * @since 1.0 */
    public abstract List<String> getData();
    /** Computes the strings to work with together with their 
     * associated descriptions. Shall be overriden in subclasses. 
     * By default delegates to {@link #getData}
     * and uses the provided strings as both, the string 
     * and its description.
     * 
     * @return name to description pairs to work with 
     * @since 2.0 */
    public Map<String,String> getDataAndDescription() {
        LinkedHashMap<String,String> ret = 
            new LinkedHashMap<String, String>();
        for (String s : getData()) {
            ret.put(s, s);
        }
        return ret;
    }
}
// END: grow.compute
