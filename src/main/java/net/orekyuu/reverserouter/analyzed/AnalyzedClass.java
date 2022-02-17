package net.orekyuu.reverserouter.analyzed;

import java.util.List;
import java.util.Optional;

public record AnalyzedClass(String name, List<AnalyzedAnnotation> annotations, List<AnalyzedMethod> methods) {

    public List<AnalyzedMethod> findMethodByAnnotation(String annotation) {
        return methods().stream()
                .filter(m -> !m.findAnnotation(annotation).isEmpty())
                .toList();
    }

    public Optional<AnalyzedAnnotation> findAnnotationByType(String type) {
        return annotations.stream()
                .filter(a -> a.name().equals(type))
                .findFirst();
    }
}
