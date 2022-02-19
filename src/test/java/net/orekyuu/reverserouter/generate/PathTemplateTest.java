package net.orekyuu.reverserouter.generate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PathTemplateTest {

    @Test
    public void emptyParams() {
        PathTemplate path = new PathTemplate("/foo/bar");
        assertThat(path.build(Map.of())).isEqualTo("/foo/bar");
    }

    @Test
    public void singleParam() {
        PathTemplate path = new PathTemplate("/foo/bar/:id");
        Map<String, Integer> params = Map.of("id", 1);
        assertThat(path.build(params)).isEqualTo("/foo/bar/1");
    }

    @Test
    public void singleParamSlashEnded() {
        PathTemplate path = new PathTemplate("/foo/bar/:id/");
        Map<String, Object> params = Map.of("id", 1);
        assertThat(path.build(params)).isEqualTo("/foo/bar/1/");
    }

    @Test
    public void multiVariables() {
        PathTemplate path = new PathTemplate("/:foo/:bar");
        Map<String, Object> params = Map.of("foo", "aaa", "bar", 123);
        assertThat(path.build(params)).isEqualTo("/aaa/123");
    }

    @Test
    public void duplicateVariable() {
        PathTemplate path = new PathTemplate("/:foo/:foo");
        Map<String, Object> params = Map.of("foo", "aaa", "bar", 123);
        assertThat(path.build(params)).isEqualTo("/aaa/aaa");
    }

    @Test
    @Disabled("TODO")
    public void concat() {
        PathTemplate path = new PathTemplate("/users/@:id");
        Map<String, Object> params = Map.of("id", "orekyuu");
        assertThat(path.build(params)).isEqualTo("/users/@orekyuu");
    }
}