package org.apidesign.misuse.projectconfig;

public class ProjectConfigurationOriginal {
    // BEGIN: misuse.prjconfig.orig
    interface ProjectConfigurationProvider {
        public ProjectConfiguration[] getConfigurations();
        public ProjectConfiguration getActive();
        public void setActive(ProjectConfiguration c);
    }
    interface ProjectConfiguration {
        public String getDisplayName();
    }
    // END: misuse.prjconfig.orig
}
