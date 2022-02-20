public final class ReverseRouter {
    private ReverseRouter() {
    }

    /**
     * SimpleTestController#all()
     * GET /users/all
     */
    public static SimpleTestControllerAllBuilder simpleTestControllerAll() {
        return new SimpleTestControllerAllBuilder();
    }

    /**
     * SimpleTestController#show()
     * GET /users/show/@{id}
     */
    public static SimpleTestControllerShowBuilder simpleTestControllerShow() {
        return new SimpleTestControllerShowBuilder();
    }
}