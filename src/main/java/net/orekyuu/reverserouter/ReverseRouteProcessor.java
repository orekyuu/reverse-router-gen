package net.orekyuu.reverserouter;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;

public class ReverseRouteProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      try {
        Filer filer = processingEnv.getFiler();
        JavaFile reverseRouter = JavaFile.builder("", TypeSpec.classBuilder("ReverseRouter").build()).build();
        reverseRouter.writeTo(filer);
        return true;
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
    return false;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of("*");
  }
}
