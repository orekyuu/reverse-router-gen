package net.orekyuu.rrg.infrastructure;

import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.api.router.RequestHandlerNamingService;
import net.orekyuu.rrg.domain.configuration.ReverseRouterGenConfiguration;

import javax.inject.Inject;

public class RequestHandlerNamingServiceImpl implements RequestHandlerNamingService {

    final ReverseRouterGenConfiguration configuration;

    @Inject
    public RequestHandlerNamingServiceImpl(ReverseRouterGenConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String reverseRouterName(RequestHandler requestHandler) {
        String controllerRootPackage = configuration.controllerRootPackage();
        String handlerClassFqn = requestHandler.handlerClassName();
        if (handlerClassFqn.startsWith(controllerRootPackage)) {
            handlerClassFqn = handlerClassFqn.substring(controllerRootPackage.length());
        }
        return (lowerCamel(handlerClassFqn) + upperCamel(requestHandler.handlerMethodName())).replace('.', '_');
    }

    @Override
    public String pathBuilderClassName(RequestHandler requestHandler) {
        return upperCamel(reverseRouterName(requestHandler)) + "Builder";
    }

    private String upperCamel(String s) {
        if (s.isEmpty()) {
            return "";
        }
        String a = s.substring(1);
        String b = String.valueOf(s.charAt(0));
        return b.toUpperCase() + a;
    }

    private String lowerCamel(String s) {
        if (s.isEmpty()) {
            return "";
        }
        String a = s.substring(1);
        String b = String.valueOf(s.charAt(0));
        return b.toLowerCase() + a;
    }
}
