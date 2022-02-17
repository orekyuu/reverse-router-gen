package net.orekyuu.reverserouter.generate;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ReverseRouteWriter {
    private TypeSpec.Builder typeBuilder;

    public ReverseRouteWriter(TypeSpec.Builder typeBuilder) {
        this.typeBuilder = typeBuilder;
    }

    public void write(ReverseRouteMethodBuilder builder) {
        typeBuilder.addMethod(builder.build().build());
    }

    public void writeInnerRouteBuilderClass() {
        TypeSpec.Builder abstractRouteBuilder = TypeSpec.classBuilder("AbstractRouteBuilder");
        abstractRouteBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.ABSTRACT);
        abstractRouteBuilder.addMethod(MethodSpec.methodBuilder("toUri")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(URI.class))
                .addCode("return URI.create(this.toString());").build());
        abstractRouteBuilder.addMethod(MethodSpec.methodBuilder("toUrl")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(URL.class))
                .addException(TypeName.get(MalformedURLException.class))
                .addCode("return this.toUri().toURL();").build());
        abstractRouteBuilder.addMethod(MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(TypeName.get(String.class))
                .build());
        typeBuilder.addType(abstractRouteBuilder.build());
    }
}
