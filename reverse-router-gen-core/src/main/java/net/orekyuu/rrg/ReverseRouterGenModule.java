package net.orekyuu.rrg;

import com.google.inject.AbstractModule;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;
import net.orekyuu.rrg.domain.configuration.ReverseRouterGenConfiguration;
import net.orekyuu.rrg.domain.element.ElementAnalyzer;
import net.orekyuu.rrg.domain.extension.ExtensionRepository;
import net.orekyuu.rrg.domain.file.JavaFileGenerator;
import net.orekyuu.rrg.infrastructure.ExtensionLoader;
import net.orekyuu.rrg.infrastructure.JavaFileGeneratorImpl;
import net.orekyuu.rrg.infrastructure.element.ElementAnalyzerImpl;
import net.orekyuu.rrg.infrastructure.onmemory.OnMemoryRequestHandlerRepository;
import net.orekyuu.rrg.presentation.ReverseRouterGenController;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;

public class ReverseRouterGenModule extends AbstractModule {

    private final ReverseRouteProcessor processor;

    public ReverseRouterGenModule(ReverseRouteProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected void configure() {
        bind(JavaFileGenerator.class).to(JavaFileGeneratorImpl.class).asEagerSingleton();
        bind(ReverseRouterGenController.class).asEagerSingleton();
        bind(ElementAnalyzer.class).to(ElementAnalyzerImpl.class).asEagerSingleton();
        bind(ExtensionRepository.class).toInstance(new ExtensionLoader());
        bind(RequestHandlerRepository.class).to(OnMemoryRequestHandlerRepository.class).asEagerSingleton();
        bind(Filer.class).toInstance(processor.processingEnvironment().getFiler());
        bind(Messager.class).toInstance(processor.processingEnvironment().getMessager());
        bind(ReverseRouterGenConfiguration.class).toInstance((optionName, defaultValue) ->
                processor.processingEnvironment()
                        .getOptions()
                        .getOrDefault(ReverseRouterGenConfiguration.PREFIX + optionName, defaultValue));
    }
}
