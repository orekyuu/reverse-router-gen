package net.orekyuu.rrg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.orekyuu.rrg.domain.element.ProcessingRootElement;
import net.orekyuu.rrg.presentation.ReverseRouterGenController;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class ReverseRouteProcessor extends AbstractProcessor {

  private ReverseRouterGenController application;
  private Injector injector;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.injector = Guice.createInjector(new ReverseRouterGenModule(this));
    this.application = this.injector.getInstance(ReverseRouterGenController.class);
    application.onInit();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element element : roundEnv.getRootElements()) {
      application.onNewElement(new ProcessingRootElement(element), roundEnv);
    }

    if (roundEnv.processingOver()) {
      application.onFinished();
      return true;
    }
    return false;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of("*");
  }

  public ProcessingEnvironment processingEnvironment() {
    return processingEnv;
  }
}
