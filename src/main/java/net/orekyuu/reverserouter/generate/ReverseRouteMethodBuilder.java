package net.orekyuu.reverserouter.generate;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

public class ReverseRouteMethodBuilder {
    private final String handlerClass;
    private final String handlerMethod;
    private final String method;
    private final String path;

    public ReverseRouteMethodBuilder(String handlerClass, String handlerMethod, String method, String path) {
        this.handlerClass = handlerClass;
        this.handlerMethod = handlerMethod;
        this.method = method;
        this.path = path;
    }

    private String methodName() {
        return method.toUpperCase() + path.replace("/", "_");
    }

    public MethodSpec.Builder build() {
        return MethodSpec.methodBuilder(methodName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.OBJECT)
                .addJavadoc(comment())
                .addCode("return null;");
    }

    private String comment() {
        return """
                %s %s <br>
                %s <br>
                %s()
                """.formatted(
                method.toUpperCase(), path,
                handlerClass,
                handlerMethod
        );
    }
}
