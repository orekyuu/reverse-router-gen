public final class ReverseRouter {
    private ReverseRouter() {
    }

    /**
     * SomeMethodController#post()
     * GET /some
     */
    public static SomeMethodControllerPostBuilder someMethodControllerPost() {
        return new SomeMethodControllerPostBuilder();
    }

    /**
     * SomeMethodController#get()
     * GET /some
     */
    public static SomeMethodControllerGetBuilder someMethodControllerGet() {
        return new SomeMethodControllerGetBuilder();
    }
}