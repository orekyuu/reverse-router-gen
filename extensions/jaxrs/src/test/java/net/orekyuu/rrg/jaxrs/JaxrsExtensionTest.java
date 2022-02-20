package net.orekyuu.rrg.jaxrs;

import net.orekyuu.rrg.api.ReverseRouterGenExtension;
import net.orekyuu.rrg.testing.ExtensionTesting;
import org.junit.jupiter.api.Test;

public class JaxrsExtensionTest extends ExtensionTesting {
    @Override
    protected ReverseRouterGenExtension extension() {
        return new JaxrsExtension();
    }

    @Test
    void testSimpleController() {
        compile("simpleController");
    }
}
