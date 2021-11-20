package net.orekyuu.reverserouter;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.testing.compile.Compiler.javac;

class ReverseRouteProcessorTest {

  public Compilation buildCompilation(JavaFileObject... files) {
    return buildCompilation(null, files);
  }

  public Compilation buildCompilation(Map<String, String> options, JavaFileObject... files) {
    Compiler javac = javac();
    if (options != null) {
      List<String> opt = options.entrySet().stream().map(e -> "-Anet.orekyuu.%s=%s".formatted(e.getKey(), e.getValue())).toList();
      javac = javac.withOptions(opt);
    }
    return javac
            .withProcessors(new ReverseRouteProcessor())
            .compile(Arrays.asList(files));
  }

  @Test
  void testDefaultConfiguration() {
    JavaFileObject file = JavaFileObjects.forResource("JaxrsController.java");
    Compilation compilation = buildCompilation(file);

    CompilationSubject.assertThat(compilation)
            .generatedSourceFile("ReverseRouter")
            .hasSourceEquivalentTo(JavaFileObjects.forSourceString("ReverseRouter", "class ReverseRouter {}"));
  }

  @Test
  void testConfiguration() {
    JavaFileObject file = JavaFileObjects.forResource("JaxrsController.java");
    Compilation compilation = buildCompilation(Map.of("reverseRouterClassName", "MyReverseRouter"), file);

    CompilationSubject.assertThat(compilation)
            .generatedSourceFile("MyReverseRouter")
            .hasSourceEquivalentTo(JavaFileObjects.forSourceString("MyReverseRouter", "class MyReverseRouter {}"));
  }
}