package net.orekyuu.rrg.jaxrs;

import net.orekyuu.rrg.api.ExtensionContext;
import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.api.analyzed.AnalyzedAnnotation;
import net.orekyuu.rrg.api.analyzed.AnalyzedClass;
import net.orekyuu.rrg.api.analyzed.AnalyzedMethod;
import net.orekyuu.rrg.api.router.*;

import java.util.ArrayList;
import java.util.List;

public class JaxrsExtension implements ReverseRouterGenExtension {

    private RequestHandlerRepository handlerRepository;
    private static final String PATH_ANNOTATION = "javax.ws.rs.Path";

    @Override
    public void onInit(ExtensionContext extensionContext) {
        handlerRepository = extensionContext.requestHandlerRepository();
    }

    @Override
    public void process(AnalyzedClass analyzedClass) {
        List<PathSegment> classSegment = analyzedClass.findAnnotationByType(PATH_ANNOTATION)
                .map(this::parse)
                .orElse(List.of());
        for (AnalyzedMethod method : analyzedClass.methods()) {
            List<PathSegment> segments = buildHandlerSegment(method, classSegment);
            if (segments.isEmpty() || method.isConstructor()) {
                continue;
            }
            UriInfo.Builder builder = UriInfo.builder();
            for (PathSegment segment : segments) {
                builder.pathSegment(segment);
            }
            UriInfo uriInfo = builder.build();

            handlerRepository.register(new RequestHandler(HttpMethod.GET, uriInfo, method.name(), analyzedClass.name()));
        }
    }

    private List<PathSegment> buildHandlerSegment(AnalyzedMethod method, List<PathSegment> classSegment) {
        List<PathSegment> methodSegment = method.findAnnotation(PATH_ANNOTATION).stream()
                .findFirst()
                .map(this::parse)
                .orElse(List.of());

        ArrayList<PathSegment> root = new ArrayList<>(classSegment);
        root.addAll(methodSegment);
        return root;
    }

    private List<PathSegment> parse(AnalyzedAnnotation annotation) {
        String value = annotation.getParameterValue(String.class, "value").orElse("");
        String[] strings = value.split("/");
        ArrayList<PathSegment> result = new ArrayList<>();

        for (String segment : strings) {
            result.add(new PathSegment.PathSegmentString(segment));
        }
        return result;
    }
}
