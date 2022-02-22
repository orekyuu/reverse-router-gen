package net.orekyuu.rrg.api.router;

public record RequestHandler(HttpMethod method,
                             UriInfo uriInfo,
                             String handlerMethodName, String handlerClassName) {

    public String reverseRouterName() {
        return (lowerCamel(handlerClassName()) + upperCamel(handlerMethodName())).replace('.', '_');
    }

    public String pathBuilderName() {
        return upperCamel(reverseRouterName()) + "Builder";
    }

    private String upperCamel(String s) {
        String a = s.substring(1);
        String b = String.valueOf(s.charAt(0));
        return b.toUpperCase() + a;
    }

    private String lowerCamel(String s) {
        String a = s.substring(1);
        String b = String.valueOf(s.charAt(0));
        return b.toLowerCase() + a;
    }
}
