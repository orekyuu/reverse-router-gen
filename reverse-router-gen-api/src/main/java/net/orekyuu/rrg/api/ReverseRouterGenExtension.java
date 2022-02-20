package net.orekyuu.rrg.api;

import net.orekyuu.rrg.api.analyzed.AnalyzedClass;

public interface ReverseRouterGenExtension {

    void onInit(ExtensionContext extensionContext);

    void process(AnalyzedClass analyzedClass);
}
