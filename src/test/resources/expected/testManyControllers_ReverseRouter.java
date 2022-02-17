import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

class ReverseRouter {
    /**
     * GET /posts <br>
     * OtherJaxrsController <br>
     * all()
     */
    public static Object GET_posts() {
        return null;
    }

    /**
     * GET /users/all <br>
     * JaxrsController <br>
     * all()
     */
    public static Object GET_users_all() {
        return null;
    }

    public abstract static class AbstractRouteBuilder {
        public URI toUri() {
            return URI.create(this.toString());
        }

        public URL toUrl() throws MalformedURLException {
            return this.toUri().toURL();
        }

        @Override
        public abstract String toString();
    }
}