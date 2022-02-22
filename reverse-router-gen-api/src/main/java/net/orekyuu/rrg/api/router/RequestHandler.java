package net.orekyuu.rrg.api.router;

public record RequestHandler(HttpMethod method,
                             UriInfo uriInfo,
                             String handlerMethodName, String handlerClassName) {

    public String simpleClassName() {
        String[] split = handlerClassName.split("\\.");
        return split[split.length - 1];
    }

    public String reverseRouterName() {
        return lowerCamel(simpleClassName()) + upperCamel(handlerMethodName());
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
