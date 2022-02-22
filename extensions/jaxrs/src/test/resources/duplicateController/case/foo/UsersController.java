package foo;

import javax.ws.rs.Path;

@Path("/foo/users")
class UsersController {

    @Path("show")
    void show() { }
}