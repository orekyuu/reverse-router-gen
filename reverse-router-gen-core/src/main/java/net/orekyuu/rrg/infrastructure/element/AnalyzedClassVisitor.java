package net.orekyuu.rrg.infrastructure.element;

import net.orekyuu.rrg.api.analyzed.AnalyzedAnnotation;
import net.orekyuu.rrg.api.analyzed.AnalyzedAnnotationParameter;
import net.orekyuu.rrg.api.analyzed.AnalyzedClass;
import net.orekyuu.rrg.api.analyzed.AnalyzedMethod;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner14;
import java.util.ArrayList;
import java.util.List;

public class AnalyzedClassVisitor extends ElementScanner14<Void, Void> {

    TypeElement current;
    List<ExecutableElement> executables = new ArrayList<>();
    @Override
    public Void visitType(TypeElement e, Void arg) {
        current = e;
        super.visitType(e, null);
        return null;
    }

    @Override
    public Void visitExecutable(ExecutableElement e, Void arg) {
        executables.add(e);
        return super.visitExecutable(e, null);
    }

    AnalyzedClass toAnalyzedClass() {
        if (current == null) {
            return null;
        }
        List<AnalyzedMethod> methods = executables.stream().map(this::toMethod).toList();
        List<AnalyzedAnnotation> annotations = current.getAnnotationMirrors().stream().map(this::toAnnotation).toList();
        return new AnalyzedClass(current.getQualifiedName().toString(), annotations, methods);
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
