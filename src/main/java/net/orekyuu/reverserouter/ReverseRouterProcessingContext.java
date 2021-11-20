package net.orekyuu.reverserouter;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;
import java.io.UncheckedIOException;

public record ReverseRouterProcessingContext(ProcessorConfiguration configuration, ProcessingEnvironment processEnv) {

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
