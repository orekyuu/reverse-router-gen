package bar;

import javax.ws.rs.Path;

@Path("/bar/users")
class UsersController {

    @Path("show")
    void show() { }
}