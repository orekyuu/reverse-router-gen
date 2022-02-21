package net.orekyuu.rrg.jaxrs;

import net.orekyuu.rrg.api.router.PathSegment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


class SegmentParserTest {

    @Test
    public void simple() {
        assertThat(SegmentParser.parse("test"))
                .isInstanceOf(PathSegment.PathSegmentString.class)
                .satisfies(it -> assertThat(it.templateString()).isEqualTo("test"));
    }

    @Test
    public void variable() {
        assertThat(SegmentParser.parse("{id}"))
                .isInstanceOf(PathSegment.PathSegmentVariable.class)
                .satisfies(it -> assertThat(it.templateString()).isEqualTo("{id}"));
    }

    @Test
    public void composite() {
        assertThat(SegmentParser.parse("@{id}"))
                .isInstanceOf(PathSegment.CompositePathSegment.class)
                .satisfies(it -> assertThat(it.templateString()).isEqualTo("@{id}"));
    }

    @Test
    public void manyVariables() {
        assertThat(SegmentParser.parse("{foo}-{bar}"))
                .isInstanceOf(PathSegment.CompositePathSegment.class)
                .satisfies(it -> {
                    if (it instanceof PathSegment.CompositePathSegment s) {
                        assertThat(s.segments()).hasSize(3);
                    } else {
                        fail("invalid class: " + it.getClass());
                    }
                });
    }

    @Test
    void regex() {
        assertThat(SegmentParser.parse("{id:\\d+}"))
                .isInstanceOf(PathSegment.PathSegmentVariable.class)
                .satisfies(it -> assertThat(it.templateString()).isEqualTo("{id}"));
    }
}