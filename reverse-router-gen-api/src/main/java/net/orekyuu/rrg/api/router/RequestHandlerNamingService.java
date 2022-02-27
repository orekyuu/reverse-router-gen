package net.orekyuu.rrg.api.router;

public interface RequestHandlerNamingService {

    String reverseRouterName(RequestHandler requestHandler);

    String pathBuilderClassName(RequestHandler requestHandler);
}
