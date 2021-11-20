package net.orekyuu.reverserouter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;

public class ReverseRouteProcessor extends AbstractProcessor {

  private ReverseRouterProcessingContext processingContext;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    Map<String, String> options = processingEnv.getOptions();
    processingContext = new ReverseRouterProcessingContext(new ProcessorConfiguration(options), processingEnv);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      processingContext.finishProcessing();
    }
    return false;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of("*");
  }
}
