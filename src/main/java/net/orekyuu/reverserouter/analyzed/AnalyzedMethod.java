package net.orekyuu.reverserouter.analyzed;

import java.lang.annotation.Annotation;
import java.util.List;

public record AnalyzedMethod(String name, List<AnalyzedAnnotation> annotations) {

    public boolean isConstructor() {
        return "<init>".equals(name);
    }

    public List<AnalyzedAnnotation> findAnnotation(Class<? extends Annotation> annotation) {
        return annotations.stream().filter(a -> a.name().equals(annotation.getName())).toList();
    }
}
