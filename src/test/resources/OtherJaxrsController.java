import java.util.List;
import javax.ws.rs.*;

@Path("/posts")
class OtherJaxrsController {

  @Path("/")
  @GET
  public List<String> all() {
    return List.of();
  }
}