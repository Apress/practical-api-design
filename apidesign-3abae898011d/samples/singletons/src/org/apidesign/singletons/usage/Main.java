package org.apidesign.singletons.usage;

// BEGIN: singletons.injectable.usage
import org.apidesign.singletons.api.DialogDisplayer;

public class Main {
    public static void main(String[] args) {
        if (DialogDisplayer.getDefault().yesOrNo("Do you like singletons?")) {
            System.err.println("OK, thank you!");
        } else {
            System.err.println(
                "Visit http://singletons.apidesign.org to change your mind!"
            );
        }
    }
}
// END: singletons.injectable.usage
