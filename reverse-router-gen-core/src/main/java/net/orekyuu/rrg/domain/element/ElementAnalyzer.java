package net.orekyuu.rrg.domain.element;

import net.orekyuu.rrg.api.analyzed.AnalyzedClass;

public interface ElementAnalyzer {

    AnalyzedClass analyze(ProcessingRootElement element);
}
