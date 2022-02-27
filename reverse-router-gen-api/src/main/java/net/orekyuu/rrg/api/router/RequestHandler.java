package net.orekyuu.rrg.api.router;

public record RequestHandler(HttpMethod method,
                             UriInfo uriInfo,
                             String handlerMethodName, String handlerClassName) implements Comparable<RequestHandler> {
    @Override
    public int compareTo(RequestHandler o) {
        String self = method().toString() + " " + uriInfo().templateString();
        String other = o.method().toString() + " " + o.uriInfo().templateString();
        return self.compareTo(other);
    }
}
