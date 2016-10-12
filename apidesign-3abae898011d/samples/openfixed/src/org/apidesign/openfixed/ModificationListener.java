package org.apidesign.openfixed;

import java.util.EventListener;

// BEGIN: openfixed.listener
public interface ModificationListener extends EventListener {
    public void modification(ModificationEvent ev);
}
// FINISH: openfixed.listener
