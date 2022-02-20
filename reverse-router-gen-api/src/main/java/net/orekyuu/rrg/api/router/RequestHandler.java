package net.orekyuu.rrg.api.router;

public record RequestHandler(HttpMethod method,
                             UriInfo uriInfo,
                             String handlerMethodName, String handlerClassName) {

    public String reverseRouterName() {
        return handlerClassName + handlerMethodName;
    }
}
