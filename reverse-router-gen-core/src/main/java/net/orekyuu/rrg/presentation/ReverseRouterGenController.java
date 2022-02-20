package net.orekyuu.rrg.presentation;

import net.orekyuu.rrg.api.ExtensionContext;
import net.orekyuu.rrg.api.analyzed.AnalyzedClass;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;
import net.orekyuu.rrg.application.gen.ReverseRouterGenerationService;
import net.orekyuu.rrg.domain.element.ElementAnalyzer;
import net.orekyuu.rrg.domain.element.ProcessingRootElement;
import net.orekyuu.rrg.domain.extension.ExtensionRepository;
import net.orekyuu.rrg.domain.extension.Extensions;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.tools.Diagnostic;
import java.io.IOException;

public class ReverseRouterGenController {

    final ElementAnalyzer analyzer;
    final ExtensionRepository extensionRepository;
    final RequestHandlerRepository requestHandlerRepository;
    final ReverseRouterGenerationService reverseRouterGenerationService;
    final Messager messager;
    Extensions extensions;

    @Inject
    public ReverseRouterGenController(ElementAnalyzer analyzer, ExtensionRepository extensionRepository, RequestHandlerRepository requestHandlerRepository, ReverseRouterGenerationService reverseRouterGenerationService, Messager messager) {
        this.analyzer = analyzer;
        this.extensionRepository = extensionRepository;
        this.requestHandlerRepository = requestHandlerRepository;
        this.reverseRouterGenerationService = reverseRouterGenerationService;
        this.messager = messager;
    }

    public void onInit() {
        extensions = extensionRepository.find();
        extensions.onInit(new ExtensionContext(requestHandlerRepository));
    }

    public void onNewElement(ProcessingRootElement element, RoundEnvironment roundEnvironment) {
        AnalyzedClass clazz = analyzer.analyze(element);
        extensions.process(clazz);
    }

    public void onFinished() {
        try {
            reverseRouterGenerationService.generate();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getClass().toString() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}