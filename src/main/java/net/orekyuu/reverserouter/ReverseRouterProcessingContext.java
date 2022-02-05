package net.orekyuu.reverserouter;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import net.orekyuu.reverserouter.analyzed.AnalyzedClasses;
import net.orekyuu.reverserouter.scan.ClassScanner;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ReverseRouterProcessingContext {

    final ProcessorConfiguration configuration;
    final ProcessingEnvironment processEnv;
    AnalyzedClasses classes = new AnalyzedClasses();
    ClassScanner scanner = new ClassScanner();

    public ReverseRouterProcessingContext(ProcessorConfiguration configuration, ProcessingEnvironment processEnv) {
        this.configuration = configuration;
        this.processEnv = processEnv;
    }

    public void collect(Element element) {
        scanner.visit(element, classes);
    }

    public void finishProcessing() {
        try {
            Filer filer = processEnv.getFiler();
            JavaFile reverseRouter = JavaFile.builder(configuration.rootPackageName(),
                    TypeSpec.classBuilder(configuration.reverseRouterClassName()).build()).build();
            reverseRouter.writeTo(filer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
