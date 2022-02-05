package net.orekyuu.reverserouter.analyzed;

import java.util.List;

public record AnalyzedAnnotation(String name, List<AnalyzedAnnotationParameter> params) {
}
