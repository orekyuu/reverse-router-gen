package net.orekyuu.rrg.jaxrs;

import net.orekyuu.rrg.testing.ExtensionTesting;
import org.junit.jupiter.api.Test;

public class JaxrsExtensionTest extends ExtensionTesting {
    @Test
    void testSimpleController() {
        compile("simpleController");
    }

    @Test
    void testSomeMethod() {
        compile("someMethod");
    }
}
