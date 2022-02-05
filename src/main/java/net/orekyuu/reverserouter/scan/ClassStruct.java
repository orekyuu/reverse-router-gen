package net.orekyuu.reverserouter.scan;

import net.orekyuu.reverserouter.analyzed.AnalyzedAnnotation;
import net.orekyuu.reverserouter.analyzed.AnalyzedAnnotationParameter;
import net.orekyuu.reverserouter.analyzed.AnalyzedClass;
import net.orekyuu.reverserouter.analyzed.AnalyzedMethod;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

public class ClassStruct {
    private TypeElement currentType;
    private List<ExecutableElement> executables = new ArrayList<>();

    void currentType(TypeElement typeElement) {
        currentType = typeElement;
    }

    void addMethod(ExecutableElement element) {
        executables.add(element);
    }

    void clear() {
        currentType = null;
        executables.clear();
    }

    AnalyzedClass createAnalyzed() {
        List<AnalyzedMethod> methods = executables.stream().map(this::toMethod).toList();
        List<AnalyzedAnnotation> annotations = currentType.getAnnotationMirrors().stream().map(this::toAnnotation).toList();
        return new AnalyzedClass(currentType.getQualifiedName().toString(), annotations, methods);
    }

    private AnalyzedMethod toMethod(ExecutableElement element) {
        List<AnalyzedAnnotation> annotations = element.getAnnotationMirrors().stream().map(this::toAnnotation).toList();
        return new AnalyzedMethod(element.getSimpleName().toString(), annotations);
    }

    private AnalyzedAnnotation toAnnotation(AnnotationMirror annotationMirror) {
        String name = annotationMirror.getAnnotationType().toString();
        List<AnalyzedAnnotationParameter> parameters = annotationMirror.getElementValues().entrySet().stream()
                .map(it -> new AnalyzedAnnotationParameter(
                        it.getKey().toString(),
                        it.getKey().getReturnType().toString(),
                        it.getValue().getValue()))
                .toList();
        return new AnalyzedAnnotation(name, parameters);
    }
}
