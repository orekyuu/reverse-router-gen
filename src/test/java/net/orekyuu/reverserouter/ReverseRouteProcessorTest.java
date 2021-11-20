package net.orekyuu.reverserouter;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.util.Arrays;

import static com.google.testing.compile.Compiler.javac;

class ReverseRouteProcessorTest {

  public Compilation buildCompilation(JavaFileObject... files) {
    return javac().withProcessors(new ReverseRouteProcessor()).compile(Arrays.asList(files));
  }

  @Test
  void testJaxrsController() {
    JavaFileObject file = JavaFileObjects.forResource("JaxrsController.java");
    Compilation compilation = buildCompilation(file);

    CompilationSubject.assertThat(compilation)
            .generatedSourceFile("ReverseRouter")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("expected/ReverseRouter.java"));
  }
}