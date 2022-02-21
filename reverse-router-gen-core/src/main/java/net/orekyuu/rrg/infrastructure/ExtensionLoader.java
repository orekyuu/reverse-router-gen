package net.orekyuu.rrg.infrastructure;

import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.domain.extension.ExtensionRepository;
import net.orekyuu.rrg.domain.extension.Extensions;
import net.orekyuu.rrg.jaxrs.JaxrsExtension;

import java.util.List;

public class ExtensionLoader implements ExtensionRepository {
    @Override
    public Extensions find() {
        List<ReverseRouterGenExtension> extensionList = List.of(new JaxrsExtension());
        return new Extensions(extensionList);
    }
}
