package net.orekyuu.reverserouter.generate;

import com.squareup.javapoet.MethodSpec;
import net.orekyuu.reverserouter.analyzed.AnalyzedClass;
import net.orekyuu.reverserouter.analyzed.AnalyzedMethod;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JaxrsReverseRouterGenerator implements ReverseRouterGenerator {

    @Override
    public void generate(AnalyzedClass clazz, ReverseRouteWriter writer) {
        clazz.findAnnotationByType("javax.ws.rs.Path")
                .ifPresent(path -> {
                    String classValue = path.getParameterValue(String.class, "value").orElse("");

                    Stream<MethodAndHttpMethod> methods = handlerMethods(clazz);
                    methods.forEach(pair -> {
                        String methodValue = pair.method.findAnnotation("javax.ws.rs.Path").stream()
                                .map(a -> a.getParameterValue(String.class, "value").orElse(""))
                                .findFirst().orElse("");
                        String uri = Stream.concat(Arrays.stream(classValue.split("/")), Arrays.stream(methodValue.split("/")))
                                .filter(s -> !s.isBlank())
                                .collect(Collectors.joining("/", "/", ""));
                        var builder = new ReverseRouteMethodBuilder(clazz.name(), pair.method().name(), pair.httpMethod(), uri);
                        writer.write(builder);
                    });
                });
    }

    record MethodAndHttpMethod(String httpMethod, AnalyzedMethod method) {}
    private Stream<MethodAndHttpMethod> handlerMethods(AnalyzedClass clazz) {
        Stream<String> methodAnnotations = Stream.of("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS");
        return methodAnnotations.flatMap(httpMethod ->
                clazz.findMethodByAnnotation("javax.ws.rs." + httpMethod).stream()
                        .map(m -> new MethodAndHttpMethod(httpMethod, m)));
    }
}
