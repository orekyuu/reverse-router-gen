package net.orekyuu.rrg.extension;

import net.orekyuu.rrg.api.ExtensionContext;
import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.api.analyzed.AnalyzedClass;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;

public class MockExtension implements ReverseRouterGenExtension {

    private RequestHandlerRepository handlerRepository;

    @Override
    public void onInit(ExtensionContext extensionContext) {
        handlerRepository = extensionContext.requestHandlerRepository();
    }

    @Override
    public void process(AnalyzedClass analyzedClass) {
    }
}
