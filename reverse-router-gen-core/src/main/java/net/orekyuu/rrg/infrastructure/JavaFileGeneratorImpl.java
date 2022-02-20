package net.orekyuu.rrg.infrastructure;

import com.squareup.javapoet.*;
import net.orekyuu.rrg.api.router.PathSegment;
import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.api.router.UriInfo;
import net.orekyuu.rrg.domain.configuration.ReverseRouterGenConfiguration;
import net.orekyuu.rrg.domain.file.JavaFileGenerator;

import javax.annotation.processing.Filer;
import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
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

        UriInfo uriInfo = handler.uriInfo();
        ArrayList<PathVariable> variables = new ArrayList<>();
        for (PathSegment segment : uriInfo.segments()) {
            processSegment(segment, variables);
        }

        for (PathVariable variable : variables) {
            clazz.addField(FieldSpec.builder(variable.type, variable.variableName, Modifier.PRIVATE).build());
            clazz.addMethod(MethodSpec.methodBuilder(variable.variableName)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(variable.type, variable.variableName)
                    .returns(className)
                    .addCode(CodeBlock.builder()
                            .addStatement("this.%s = %s".formatted(variable.variableName, variable.variableName))
                            .addStatement("return this")
                            .build())
                    .build());
        }

        CodeBlock.Builder stringBuilder = CodeBlock.builder();
        stringBuilder.addStatement("$T builder = new $T();", StringBuilder.class, StringBuilder.class);
        for (PathSegment segment : uriInfo.segments()) {
            stringBuilder.addStatement("builder.append(\"/\")");
            generatePathStringBuilder(segment, stringBuilder);
        }
        if (uriInfo.lastSlash()) {
            stringBuilder.addStatement("builder.append(\"/\")");
        }
        stringBuilder.addStatement("return builder.toString()");

        clazz.addMethod(MethodSpec.methodBuilder("toPathString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addCode(stringBuilder.build())
                .build());

        JavaFile file = JavaFile.builder(className.packageName(), clazz.build()).build();
        file.writeTo(filer);
    }

    private void generatePathStringBuilder(PathSegment segment, CodeBlock.Builder block) {
        if (segment instanceof PathSegment.PathSegmentString s) {
            block.addStatement("builder.append(\"" + s.value() + "\")");
        } else if (segment instanceof PathSegment.PathSegmentVariable s) {
            block.addStatement("builder.append(" + s.name() + ")");
        } else if (segment instanceof PathSegment.CompositePathSegment s) {
            for (PathSegment pathSegment : s.segments()) {
                generatePathStringBuilder(pathSegment, block);
            }
        }
    }

    private void processSegment(PathSegment segment, List<PathVariable> variables) {
        if (segment instanceof PathSegment.PathSegmentString s) {
            return;
        } else if (segment instanceof PathSegment.PathSegmentVariable s) {
            PathVariable variable = new PathVariable(ClassName.get(s.packageName(), s.className()), s.name());
            variables.add(variable);
        } else if (segment instanceof PathSegment.CompositePathSegment s) {
            for (PathSegment pathSegment : s.segments()) {
                processSegment(pathSegment, variables);
            }
        }
    }

    private record PathVariable(ClassName type, String variableName) {

    }
}
