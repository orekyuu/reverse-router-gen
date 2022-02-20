package net.orekyuu.rrg.application.gen;

import net.orekyuu.rrg.api.router.RequestHandler;
import net.orekyuu.rrg.api.router.RequestHandlerRepository;
import net.orekyuu.rrg.domain.file.JavaFileGenerator;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ReverseRouterGenerationService {
    final RequestHandlerRepository requestHandlerRepository;
    final JavaFileGenerator generator;

    @Inject
    public ReverseRouterGenerationService(RequestHandlerRepository requestHandlerRepository, JavaFileGenerator generator) {
        this.requestHandlerRepository = requestHandlerRepository;
        this.generator = generator;
    }

    public void generate() throws IOException {
        List<RequestHandler> requestHandlers = requestHandlerRepository.all();
        generator.generateReverseRouter(requestHandlers);
    }
}
