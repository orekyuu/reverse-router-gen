package net.orekyuu.reverserouter.generate;

import net.orekyuu.reverserouter.analyzed.AnalyzedClass;
import net.orekyuu.reverserouter.analyzed.AnalyzedClasses;

public interface ReverseRouterGenerator {

    default void generate(AnalyzedClasses classes, ReverseRouteWriter writer) {
        classes.all().forEach(it -> generate(it, writer));
    }

    void generate(AnalyzedClass clazz, ReverseRouteWriter writer);
}
