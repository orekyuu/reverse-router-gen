import java.lang.String;
import java.lang.StringBuilder;

public final class SimpleTestControllerAllBuilder {
    public String toPathString() {
        StringBuilder builder = new StringBuilder();;
        builder.append("/");
        builder.append("users");
        builder.append("/");
        builder.append("all");
        return builder.toString();
    }
}