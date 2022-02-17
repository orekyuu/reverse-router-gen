package net.orekyuu.reverserouter.analyzed;

import java.util.List;
import java.util.Optional;

public record AnalyzedAnnotation(String name, List<AnalyzedAnnotationParameter> params) {

    public <T> Optional<T> getParameterValue(Class<T> parameterValueType, String name) {
        String parameterValueMethod = name + "()";
        return params.stream()
                .filter(p -> p.name().equals(parameterValueMethod))
                .map(p -> parameterValueType.cast(p.value()))
                .findFirst();
    }
}
