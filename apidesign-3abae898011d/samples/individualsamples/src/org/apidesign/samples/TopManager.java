package org.apidesign.samples;

// BEGIN: end.TopManager
public abstract class TopManager {
    public abstract ExecutionEngine getExecutionEngine();
    public abstract CompilationEgine getCompilationEngine();
// FINISH: end.TopManager
    
    
    public static abstract class ExecutionEngine {
    }
    
    public static abstract class CompilationEgine {
    }
}
