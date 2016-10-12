package org.apidesign.openfixed;

import java.util.EventListener;

// BEGIN: openfixed.postprocessor
public interface PostModificationListener extends EventListener {
    public void postProcess(PostModificationEvent ev);
}
// END: openfixed.postprocessor
