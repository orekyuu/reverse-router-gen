package net.orekyuu.rrg.infrastructure.onmemory;

import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OnMemoryRequestHandlerRepository implements RequestHandlerRepository {
    private final Set<RequestHandler> handlers = new HashSet<>();
    @Override
    public void register(RequestHandler handler) {
        handlers.add(handler);
    }

    @Override
    public List<RequestHandler> all() {
        return handlers.stream().toList();
    }
}
