package net.orekyuu.reverserouter.analyzed;

import java.util.List;

public record AnalyzedMethod(String name, List<AnalyzedAnnotation> annotations) {

    public boolean isConstructor() {
        return "<init>".equals(name);
    }

    public List<AnalyzedAnnotation> findAnnotation(String annotation) {
        return annotations.stream().filter(a -> a.name().equals(annotation)).toList();
    }
}
