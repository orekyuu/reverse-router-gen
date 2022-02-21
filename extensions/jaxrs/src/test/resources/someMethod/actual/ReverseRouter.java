public final class ReverseRouter {
    private ReverseRouter() {
    }

    /**
     * SomeMethodController#get()
     * GET /some
     */
    public static SomeMethodControllerGetBuilder someMethodControllerGet() {
        return new SomeMethodControllerGetBuilder();
    }

    /**
     * SomeMethodController#post()
     * GET /some
     */
    public static SomeMethodControllerPostBuilder someMethodControllerPost() {
        return new SomeMethodControllerPostBuilder();
    }
}