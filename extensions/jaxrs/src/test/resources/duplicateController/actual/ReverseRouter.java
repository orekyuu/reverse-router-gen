public final class ReverseRouter {
    private ReverseRouter() {
    }

    /**
     * bar.UsersController#show()
     * GET /bar/users/show
     */
    public static Bar_UsersControllerShowBuilder bar_UsersControllerShow() {
        return new Bar_UsersControllerShowBuilder();
    }

    /**
     * foo.UsersController#show()
     * GET /foo/users/show
     */
    public static Foo_UsersControllerShowBuilder foo_UsersControllerShow() {
        return new Foo_UsersControllerShowBuilder();
    }
}