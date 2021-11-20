package net.orekyuu.reverserouter;

import java.util.Map;

public record ProcessorConfiguration(String rootPackageName, String reverseRouterClassName) {
    ProcessorConfiguration(Map<String, String> options) {
        this(options.getOrDefault("net.orekyuu.rootPackageName", ""),
                options.getOrDefault("net.orekyuu.reverseRouterClassName", "ReverseRouter"));
    }
}
