package org.apidesign.misuse.projectconfig;

public class ProjectConfigurationCorrect {
    // BEGIN: misuse.prjconfig.correct
    interface ProjectConfigurationProvider
        <Configuration extends ProjectConfiguration> {
        public Configuration[] getConfigurations();
        public Configuration getActive();
        public void setActive(Configuration c);
    }
    interface ProjectConfiguration {
        public String getDisplayName();
    }
    // END: misuse.prjconfig.correct
    
    

    /* Following does not work:
    static {
        // BEGIN: misuse.prjconfig.correct.trivial.access
        ProjectConfigurationProvider<?> provider = null; // obtain elsewhere;
        provider.setActive(provider.getConfigurations()[0]);
        // END: misuse.prjconfig.correct.trivial.access
    }
    */

    static 
    // BEGIN: misuse.prjconfig.correct.access
    { 
        ProjectConfigurationProvider<?> provider = null; // obtain elsewhere;
        resetToZero(provider);
    }
    // END: misuse.prjconfig.correct.access

    // BEGIN: misuse.prjconfig.correct.openmethod
    private static <C extends ProjectConfiguration> void resetToZero(
        ProjectConfigurationProvider<C> provider
    ) {
        provider.setActive(provider.getConfigurations()[0]);
    }
    // END: misuse.prjconfig.correct.openmethod

    // BEGIN: misuse.prjconfig.correct.openclass
    static void workWithProjectConfigurationProvider(
        ProjectConfigurationProvider<?> p
    ) {
        ResetToZero<?> rtz = ResetToZero.create(p);
        rtz.obtainFirst();
        // after a while
        rtz.apply();
    }

    static class ResetToZero<C extends ProjectConfiguration> {
        C active;
        final ProjectConfigurationProvider<C> provider;

        ResetToZero(ProjectConfigurationProvider<C> provider) {
            this.provider = provider;
        }

        static <C extends ProjectConfiguration> ResetToZero<C> create(
            ProjectConfigurationProvider<C> p
        ) {
            return new ResetToZero<C>(p);
        }

        public void obtainFirst() {
            active = provider.getConfigurations()[0];
        }

        public void apply() {
            provider.setActive(active);
        }
    }
    // END: misuse.prjconfig.correct.openclass

}
