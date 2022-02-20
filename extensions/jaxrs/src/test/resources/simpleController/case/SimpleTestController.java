import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("users")
class SimpleTestController {

    @Path("all")
    @GET
    public void all() {

    }
}