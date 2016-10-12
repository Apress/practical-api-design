/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.anagram.impl.annotations;

import java.util.Set;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.apidesign.anagram.api.annotations.Words;
import org.openide.filesystems.annotations.LayerBuilder.File;
import org.openide.filesystems.annotations.LayerGeneratingProcessor;
import org.openide.filesystems.annotations.LayerGenerationException;
import org.openide.util.lookup.ServiceProvider;

/** Compile time caches example. Processor that is triggered
 * during compilation when a <code>@Words</code> annotations is found.
 * It generates some declarative registrations, so the annotated object
 * is found later during runtime, but not instantiated before it is really
 * needed.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: anagram.api.WordsProcessor
@ServiceProvider(service=Processor.class)
@SupportedAnnotationTypes("org.apidesign.anagram.api.annotations.Words")
@SupportedSourceVersion(SourceVersion.RELEASE_5)
public class WordsProcessor extends LayerGeneratingProcessor {

    @Override
    protected boolean handleProcess(
        Set<? extends TypeElement> set, RoundEnvironment env
    ) throws LayerGenerationException {
        Elements elements = processingEnv.getElementUtils();

        for (Element e : env.getElementsAnnotatedWith(Words.class)) {
            Words w = e.getAnnotation(Words.class);

            TypeElement te = (TypeElement)e.getEnclosingElement();
            String teName = elements.getBinaryName(te).toString();
// FINISH: anagram.api.WordsProcessor

// BEGIN: anagram.api.WordsProcessorLayer
            File f = layer(e).file(
                "Services/" + teName.replace('.', '-') + ".instance"
            );
            f.methodvalue(
                "instanceCreate",
                "org.apidesign.anagram.impl.annotations.WordsImpl",
                "create"
            );
            f.stringvalue(
                "instanceClass",
                "org.apidesign.anagram.impl.annotations.WordsImpl"
            );
            f.stringvalue(
                "instanceOf",
                "org.apidesign.anagram.api.WordLibrary"
            );
            f.methodvalue(
                "words",
                teName,
                e.getSimpleName().toString()
            );
            f.write();
// END: anagram.api.WordsProcessorLayer
        }

        return true;
    }

}
