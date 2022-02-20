package net.orekyuu.rrg.api.router;

import java.util.List;

public interface RequestHandlerRepository {
    void register(RequestHandler handler);

    List<RequestHandler> all();
}
