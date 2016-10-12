package org.apidesign.extensibleicon;

import org.openide.util.Lookup;

// BEGIN: ext.CatQuery
public class CatQuery {
    private CatQuery() { }
    
    public static boolean isCat(ExtIcon icon) {
        for (CatQueryImplementation impl : 
            Lookup.getDefault().lookupAll(CatQueryImplementation.class)
        ) {
            Boolean res = impl.isCat(icon);
            if (res != null) {
                return res;
            }
        }

        for (CatQueryImplementation impl : 
            icon.getLookup().lookupAll(CatQueryImplementation.class)
        ) {
            Boolean res = impl.isCat(icon);
            if (res != null) {
                return res;
            }
        }
        
        return false;
    }
}
// END: ext.CatQuery
