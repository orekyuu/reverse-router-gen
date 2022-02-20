package net.orekyuu.rrg.infrastructure.element;

import net.orekyuu.rrg.api.analyzed.AnalyzedClass;
import net.orekyuu.rrg.domain.element.ElementAnalyzer;
import net.orekyuu.rrg.domain.element.ProcessingRootElement;

public class ElementAnalyzerImpl implements ElementAnalyzer {

    @Override
    public AnalyzedClass analyze(ProcessingRootElement element) {
        AnalyzedClassVisitor visitor = new AnalyzedClassVisitor();
        element.element().accept(visitor, null);
        return visitor.toAnalyzedClass();
    }
}
