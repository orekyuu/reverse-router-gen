import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/some")
class SomeMethodController {

    @Path("")
    @GET
    public void get() {}

    @Path("")
    @GET
    public void get(String str) {}

    @Path("")
    @POST
    public void post(String id) {}
}