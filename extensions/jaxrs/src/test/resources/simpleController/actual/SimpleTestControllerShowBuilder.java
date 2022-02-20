import java.lang.String;
import java.lang.StringBuilder;

public final class SimpleTestControllerShowBuilder {
    private String id;

    public SimpleTestControllerShowBuilder id(String id) {
        this.id = id;
        return this;
    }

    public String toPathString() {
        StringBuilder builder = new StringBuilder();;
        builder.append("/");
        builder.append("users");
        builder.append("/");
        builder.append("show");
        builder.append("/");
        builder.append("@");
        builder.append(id);
        return builder.toString();
    }
}