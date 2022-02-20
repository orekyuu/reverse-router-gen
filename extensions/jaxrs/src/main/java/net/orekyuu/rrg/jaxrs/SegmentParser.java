package net.orekyuu.rrg.jaxrs;

import net.orekyuu.rrg.api.router.PathSegment;

import java.util.ArrayList;
import java.util.List;

public class SegmentParser {
    public static PathSegment parse(String segment) {
        char[] chars = segment.toCharArray();
        StringBuilder buffer = new StringBuilder();
        List<PathSegment> segments = new ArrayList<>();
        for (char c : chars) {
            switch (c) {
                case '{': {
                    String value = buffer.toString();
                    if (!value.isEmpty()) {
                        segments.add(new PathSegment.PathSegmentString(value));
                    }
                    buffer = new StringBuilder();
                    break;
                }
                case '}': {
                    String value = buffer.toString();
                    if (!value.isEmpty()) {
                        segments.add(new PathSegment.PathSegmentVariable(value,
                                "java.lang", "String"));
                    }
                    buffer = new StringBuilder();
                    break;
                }
                default:
                    buffer.append(c);
            }
        }
        String value = buffer.toString();
        if (!value.isEmpty()) {
            segments.add(new PathSegment.PathSegmentString(value));
        }

        if (segments.size() == 1) {
            return segments.get(0);
        }
        return new PathSegment.CompositePathSegment(segments);
    }
}
