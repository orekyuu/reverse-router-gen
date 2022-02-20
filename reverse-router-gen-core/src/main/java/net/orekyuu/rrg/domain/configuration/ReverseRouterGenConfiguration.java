package net.orekyuu.rrg.domain.configuration;

public interface ReverseRouterGenConfiguration {
    String PREFIX = "net.orekyuu.rrg.";

    default String rootPackageName() {
        return getOption("rootPackageName", "");
    }

    default String reverseRouterClassName() {
        return getOption("reverseRouterClassName", "ReverseRouter");
    }

    String getOption(String optionName, String defaultValue);
}
