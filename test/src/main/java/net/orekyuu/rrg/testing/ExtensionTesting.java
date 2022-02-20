package net.orekyuu.rrg.testing;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import net.orekyuu.rrg.ReverseRouteProcessor;
import org.junit.jupiter.api.Assertions;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.google.testing.compile.Compiler.javac;

public abstract class ExtensionTesting {

    public void compile(String testCaseDir) {
        try {
            URL resource = getClass().getClassLoader().getResource(testCaseDir);
            Path testCaseDirPath = Paths.get(resource.toURI());

            Path caseDir = testCaseDirPath.resolve("case");
            Path actualDir = testCaseDirPath.resolve("actual");

            List<JavaFileObject> files = files(caseDir);
            Compilation compilation = javac()
                    .withProcessors(new ReverseRouteProcessor())
                    .compile(files);

            JavaFileObjectSubject subject = CompilationSubject.assertThat(compilation)
                    .generatedSourceFile("ReverseRouter");

            List<JavaFileObject> actualFiles = files(actualDir);
            for (JavaFileObject actualFile : actualFiles) {
                subject.hasSourceEquivalentTo(actualFile);
            }
        } catch (URISyntaxException | IOException e) {
            Assertions.fail(e);
        }
    }

    private List<JavaFileObject> files(Path dir) throws IOException {
        List<Path> paths = Files.walk(dir).toList();
        List<JavaFileObject> javaFiles = new ArrayList<>();
        for (Path path : paths) {
            URL url = path.toUri().toURL();
            if (Files.isRegularFile(path)) {
                javaFiles.add(JavaFileObjects.forResource(url));
            }
        }
        return javaFiles;
    }
}
