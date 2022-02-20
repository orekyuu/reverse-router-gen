package net.orekyuu.rrg.infrastructure;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.domain.configuration.ReverseRouterGenConfiguration;
import net.orekyuu.rrg.domain.file.JavaFileGenerator;

import javax.annotation.processing.Filer;
import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class JavaFileGeneratorImpl implements JavaFileGenerator {
    final ReverseRouterGenConfiguration configuration;
    final Filer filer;

    @Inject
    public JavaFileGeneratorImpl(ReverseRouterGenConfiguration configuration, Filer filer) {
        this.configuration = configuration;
        this.filer = filer;
    }

    @Override
    public void generateReverseRouter(List<RequestHandler> handlers) throws IOException {
        ClassName className = ClassName.get(configuration.rootPackageName(), configuration.reverseRouterClassName());
        TypeSpec.Builder clazz = TypeSpec.classBuilder(className);
        clazz.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        clazz.addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build());
        for (RequestHandler handler : handlers) {
            String javadoc = """
                    %s#%s()
                    %s %s
                    """.formatted(handler.handlerClassName(), handler.handlerMethodName(),
                    handler.method(), handler.uriInfo().templateString());

            ClassName pathBuilderName = ClassName.get(configuration.rootPackageName(), handler.pathBuilderName());
            MethodSpec method = MethodSpec.methodBuilder(handler.reverseRouterName())
                    .returns(pathBuilderName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addJavadoc(javadoc)
                    .addCode("return new $T();", pathBuilderName).build();
            clazz.addMethod(method);
        }

        JavaFile file = JavaFile.builder(className.packageName(), clazz.build()).build();
        file.writeTo(filer);
    }

    @Override
    public void generatePathBuilder(RequestHandler handler) throws IOException {
        ClassName className = ClassName.get(configuration.rootPackageName(), handler.pathBuilderName());

        TypeSpec.Builder clazz = TypeSpec.classBuilder(className);
        clazz.addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        JavaFile file = JavaFile.builder(className.packageName(), clazz.build()).build();
        file.writeTo(filer);
    }
}
