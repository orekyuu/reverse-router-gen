package net.orekyuu.rrg.infrastructure.onmemory;

import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;

import java.util.ArrayList;
import java.util.List;

public class OnMemoryRequestHandlerRepository implements RequestHandlerRepository {
    private final List<RequestHandler> handlers = new ArrayList<>();
    @Override
    public void register(RequestHandler handler) {
        handlers.add(handler);
    }

    @Override
    public List<RequestHandler> all() {
        return handlers;
    }
}
