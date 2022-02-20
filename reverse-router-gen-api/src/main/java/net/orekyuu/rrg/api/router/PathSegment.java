package net.orekyuu.rrg.api.router;

import java.util.List;
import java.util.stream.Collectors;

public sealed interface PathSegment {
    record CompositePathSegment(List<PathSegment> segments) implements PathSegment {

        @Override
        public String templateString() {
            return segments().stream()
                    .map(PathSegment::templateString)
                    .collect(Collectors.joining());
        }
    }

    record PathSegmentVariable(String name, String packageName, String className) implements PathSegment {
        @Override
        public String templateString() {
            return "{" + name + "}";
        }
    }

    record PathSegmentString(String value) implements PathSegment {

        @Override
        public String templateString() {
            return value;
        }
    }

    String templateString();
}
