package net.orekyuu.rrg.domain.extension;

import net.orekyuu.rrg.api.ExtensionContext;
import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.api.analyzed.AnalyzedClass;

import java.util.List;

public class Extensions {
    private final List<ReverseRouterGenExtension> extensions;

    public Extensions(List<ReverseRouterGenExtension> extensions) {
        this.extensions = extensions;
    }

    public List<ReverseRouterGenExtension> all() {
        return extensions;
    }

    public void process(AnalyzedClass clazz) {
        for (ReverseRouterGenExtension extension : all()) {
            extension.process(clazz);
        }
    }

    public void onInit(ExtensionContext context) {
        for (ReverseRouterGenExtension extension : all()) {
            extension.onInit(context);
        }
    }
}
