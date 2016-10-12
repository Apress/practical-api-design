/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.anagram.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apidesign.anagram.api.WordLibrary;

/**
 * Annotations to mark a static method returning array of Strings with.
 * Such method is then treated as a provider of {@link WordLibrary}.
 * <p>
 * Its retention is set to source one, as it is
 * processed by associated WordsProcessor during compile time.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: anagram.api.Words
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Words {
}
// END: anagram.api.Words
