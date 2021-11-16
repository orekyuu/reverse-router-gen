import java.util.List;
import javax.ws.rs.*;

@Path("/users")
class JaxrsController {

  @Path("/all")
  @GET
  public List<String> all() {
    return List.of();
  }
}