package net.orekyuu.rrg.extension;

import net.orekyuu.rrg.domain.extension.ExtensionRepository;
import net.orekyuu.rrg.domain.extension.Extensions;
import net.orekyuu.rrg.infrastructure.ExtensionLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ExtensionLoaderTest {

    ExtensionRepository repository = new ExtensionLoader();

    @Test
    void loadExtensions() {
        Extensions extensions = repository.find();
        Assertions.assertThat(extensions.all()).hasSize(1);
    }
}