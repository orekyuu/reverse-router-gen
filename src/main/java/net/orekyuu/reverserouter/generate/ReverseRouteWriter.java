package net.orekyuu.reverserouter.generate;

import com.squareup.javapoet.TypeSpec;

public class ReverseRouteWriter {
    private TypeSpec.Builder typeBuilder;

    public ReverseRouteWriter(TypeSpec.Builder typeBuilder) {
        this.typeBuilder = typeBuilder;
    }

    public void write(ReverseRouteMethodBuilder builder) {
        typeBuilder.addMethod(builder.build().build());
    }
}
