package net.orekyuu.rrg.infrastructure;

import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.domain.extension.ExtensionRepository;
import net.orekyuu.rrg.domain.extension.Extensions;

import java.util.List;
import java.util.ServiceLoader;

public class ExtensionLoader implements ExtensionRepository {
    @Override
    public Extensions find() {
        ServiceLoader<ReverseRouterGenExtension> extensions = ServiceLoader.load(ReverseRouterGenExtension.class);
        List<ReverseRouterGenExtension> extensionList = extensions.stream()
                .map(ServiceLoader.Provider::get)
                .toList();
        return new Extensions(extensionList);
    }
}
