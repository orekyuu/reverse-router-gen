package net.orekyuu.rrg.domain.file;

import net.orekyuu.rrg.api.router.RequestHandler;

import java.io.IOException;
import java.util.List;

public interface JavaFileGenerator {

    void generateReverseRouter(List<RequestHandler> handlers) throws IOException;

    void generatePathBuilder(RequestHandler handler) throws IOException;
}
