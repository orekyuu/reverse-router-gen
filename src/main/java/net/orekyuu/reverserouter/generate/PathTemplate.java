package net.orekyuu.reverserouter.generate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class PathTemplate {
    private final String path;

    public PathTemplate(String path) {
        this.path = path;
    }

    public String build(Map<String, ?> pathVariable) {
        String[] split = path.split("/");
        String last = path.endsWith("/") ? "/" : "";
        return Arrays.stream(split)
                .map(part -> {
                    if (part.matches("^:\\w+$")) {
                        String variableName = part.substring(1);
                        Object o = pathVariable.get(variableName);
                        if (o != null) {
                            return o.toString();
                        }
                    }
                    return part;
                })
                .collect(Collectors.joining("/", "", last));
    }
}
