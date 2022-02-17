package net.orekyuu.reverserouter;

import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import net.orekyuu.reverserouter.analyzed.*;
import net.orekyuu.reverserouter.scan.ClassScanner;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.ws.rs.Path;

import java.util.List;
import java.util.Set;

import static com.google.testing.compile.Compiler.javac;
import static org.junit.jupiter.api.Assertions.*;

class ClassScannerTest {

    public AnalyzedClasses compile(String file) {
        AnalyzedClasses classes = new AnalyzedClasses();

        Compiler javac = javac();
        javac.withProcessors(new AbstractProcessor() {
            @Override
            public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
                Set<? extends Element> elements = roundEnv.getRootElements();
                ClassScanner scanner = new ClassScanner();
                for (Element element : elements) {
                    scanner.visit(element, classes);
                }
                return false;
            }

            @Override
            public Set<String> getSupportedAnnotationTypes() {
                return Set.of("*");
            }
        }).compile(JavaFileObjects.forResource(file));

        return classes;
    }

    @Test
    void testJaxrsController() {
        AnalyzedClasses classes = compile("JaxrsController.java");
        Set<AnalyzedClass> all = classes.all();
        assertEquals(all.size(), 1);
        AnalyzedClass clazz = all.stream().findFirst().get();

        assertEquals(clazz.annotations().size(), 1);
        AnalyzedAnnotation annotation = clazz.annotations().get(0);
        assertEquals(annotation.name(), "javax.ws.rs.Path");
        assertEquals(annotation.params().size(), 1);
        assertEquals(annotation.params().get(0), new AnalyzedAnnotationParameter("value()", "java.lang.String", "/users"));

        assertEquals(clazz.methods().size(), 2);
        List<AnalyzedMethod> methodByAnnotation = clazz.findMethodByAnnotation(Path.class.getName());
        assertEquals(methodByAnnotation.size(), 1);
        assertEquals(methodByAnnotation.get(0).name(), "all");
    }
}