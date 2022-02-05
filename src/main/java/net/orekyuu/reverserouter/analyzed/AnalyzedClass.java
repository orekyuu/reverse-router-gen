package net.orekyuu.reverserouter.analyzed;

import java.lang.annotation.Annotation;
import java.util.List;

public record AnalyzedClass(String name, List<AnalyzedAnnotation> annotations, List<AnalyzedMethod> methods) {

    public List<AnalyzedMethod> findMethodByAnnotation(Class<? extends Annotation> annotation) {
        return methods().stream()
                .filter(m -> !m.findAnnotation(annotation).isEmpty())
                .toList();
    }
}
