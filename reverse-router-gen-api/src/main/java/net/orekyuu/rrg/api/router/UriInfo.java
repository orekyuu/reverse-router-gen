package net.orekyuu.rrg.api.router;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record UriInfo(List<PathSegment> segments, boolean lastSlash) {

    public String templateString() {
        String last = "";
        if (lastSlash) {
            last = "/";
        }
        return segments.stream()
                .map(PathSegment::templateString)
                .collect(Collectors.joining("/", "/", last));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        boolean lastSlash = false;
        List<PathSegment> segments = new ArrayList<>();

        public Builder lastSlash() {
            lastSlash = true;
            return this;
        }

        public Builder stringSegment(String segment) {
            segments.add(new PathSegment.PathSegmentString(segment));
            return this;
        }

        public Builder variableSegment(String variableName, String packageName, String className) {
            segments.add(new PathSegment.PathSegmentVariable(variableName, packageName, className));
            return this;
        }

        public Builder pathSegment(PathSegment segment) {
            segments.add(segment);
            return this;
        }

        public UriInfo build() {
            return new UriInfo(segments, lastSlash);
        }
    }
}
